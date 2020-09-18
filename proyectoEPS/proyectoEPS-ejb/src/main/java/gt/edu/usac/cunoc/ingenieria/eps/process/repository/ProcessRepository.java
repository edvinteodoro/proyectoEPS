package gt.edu.usac.cunoc.ingenieria.eps.process.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.JAVA_MAIL_SESSION;
import gt.edu.usac.cunoc.ingenieria.eps.process.Appointment;
import gt.edu.usac.cunoc.ingenieria.eps.configuration.mail.MailService;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess;
import gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCoordinator;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.service.UserService;
import static gt.edu.usac.cunoc.ingenieria.eps.user.service.UserService.CONTENT_TYPE;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
@LocalBean
public class ProcessRepository {

    private final int ANTICIPATED_TIME = 2;
    private final int LIMIT_MONTH = 6;
    private final int MONTH_DAYS = 30;

    public static final String GET_PROCESSES_SUPERVISOR_EPS = "SELECT c FROM Process c WHERE c.supervisor_EPS.userId=:userIdSupervisorEPS AND (c.state != :RECHAZADO OR c.state != :INACTIVO)";

    public static final String GET_APPOINTMENT_BY_PROCESS = "SELECT a FROM Appointment a WHERE a.id=:appointmentId";

    @EJB
    MailService mailService;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Resource(name = JAVA_MAIL_SESSION)
    private Session emailSession;

    public static final String GET_PROCESS_USER = "SELECT c.process FROM UserCareer c WHERE c.uSERuserId.userId=:userId AND c.process IS NOT NULL ORDER BY c.process.id DESC";
    public static final String ID_PARAMETER_NAME = "userId";

    public Optional<Process> findProcessById(Integer id) throws UserException {
        if (id == null) {
            throw new UserException("Debe indicar el ID");
        }
        return Optional.ofNullable(entityManager.find(Process.class, id));
    }

    public List<Process> getProcess(Process process) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Process> criteriaQuery = criteriaBuilder.createQuery(Process.class);
        Root<Process> processR = criteriaQuery.from(Process.class);
        List<Predicate> predicates = new ArrayList<>();
        if (process.getId() != null) {
            predicates.add(criteriaBuilder.equal(processR.get("id"), process.getId()));
        }
        if (process.getUserCareer() != null) {
            predicates.add(criteriaBuilder.equal(processR.get("userCareer"), process.getUserCareer()));
        }
        if (process.getApprovedCareerCoordinator() != null) {
            predicates.add(criteriaBuilder.equal(processR.get("approvedCareerCoordinator"), process.getApprovedCareerCoordinator()));
        }
        criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<Process> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Process> getProcessUser(User user) {
        TypedQuery<Process> query = entityManager.createQuery(GET_PROCESS_USER, Process.class);
        query.setParameter(ID_PARAMETER_NAME, user.getUserId());
        return query.getResultList();
    }

    private List<Process> revisionRemainer(boolean lastRevision) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Process> criteriaQuery = criteriaBuilder.createQuery(Process.class);
        Root<Process> processR = criteriaQuery.from(Process.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.isNotNull(processR.get("dateApproveddEpsDevelopment")));

        predicates.add(
                criteriaBuilder.equal(
                        criteriaBuilder.mod(
                                criteriaBuilder.function(
                                        "DATEDIFF",
                                        Integer.class,
                                        processR.get("dateApproveddEpsDevelopment"),
                                        criteriaBuilder.literal(LocalDate.now().minusDays(ANTICIPATED_TIME))
                                ),
                                MONTH_DAYS), 0)
        );

        if (lastRevision) {
            predicates.add(
                    criteriaBuilder.equal(
                            criteriaBuilder.toInteger(
                                    criteriaBuilder.quot(
                                            criteriaBuilder.function(
                                                    "DATEDIFF",
                                                    Integer.class,
                                                    processR.get("dateApproveddEpsDevelopment"),
                                                    criteriaBuilder.literal(LocalDate.now().minusDays(ANTICIPATED_TIME))
                                            ),
                                            MONTH_DAYS)
                            ), LIMIT_MONTH)
            );
        } else {
            predicates.add(
                    criteriaBuilder.lessThan(
                            criteriaBuilder.toInteger(
                                    criteriaBuilder.quot(
                                            criteriaBuilder.function(
                                                    "DATEDIFF",
                                                    Integer.class,
                                                    processR.get("dateApproveddEpsDevelopment"),
                                                    criteriaBuilder.literal(LocalDate.now().minusDays(ANTICIPATED_TIME))
                                            ), MONTH_DAYS)
                            ), LIMIT_MONTH)
            );
        }

        criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<Process> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public boolean SendEmailStudent(TailCoordinator tailCoordinator, String title, String msg) {
        try {
            sendEmail(tailCoordinator.getProcess().getUserCareer().getUSERuserId().getEmail(), title, emailBody(tailCoordinator, title, msg));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sendEmailStudentCommitteeEPS(User student, String Title, String msg) {
        try {
            sendEmail(student.getEmail(), Title, emailBodyCommitteeEPS(Title, msg));
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Asynchronous
    private Future<Void> sendEmail(final String to, final String subject, final String body) {
        try {
            Message message = new MimeMessage(emailSession);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(body, CONTENT_TYPE);
            Transport.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new AsyncResult<>(null);
    }

    private String emailBody(TailCoordinator tailCoordinator, String title, String mensaje) {
        return ("<h2><strong>" + title + "</strong></h2>"
                + "<p>" + tailCoordinator.getUserCareer().getUSERuserId().getROLid().getName() + ": " + tailCoordinator.getUserCareer().getCAREERcodigo().getName() + "</p>"
                + "<p>" + tailCoordinator.getUserCareer().getUSERuserId().getFirstName() + " " + tailCoordinator.getUserCareer().getUSERuserId().getLastName() + "</p>"
                + "<p><span style=\"color: #000000;\">" + mensaje + "</span></p>"
                + "<p>Divisi&oacute;n de Ciencias de la Ingenieria - Centro Universitario de Occidente</p>");
    }
    
    private String emailBodyCommitteeEPS(String title, String mensaje){
        return ("<h2><strong>" + title + "</strong></h2>"
                + "<p><span style=\"color: #000000;\">" + mensaje + "</span></p>"
                + "<p>Divisi&oacute;n de Ciencias de la Ingenieria - Centro Universitario de Occidente</p>");
    }

    public List<Process> getProcessBySupervisorEPS(User supervisorEPS) {
        Query query = entityManager.createQuery(GET_PROCESSES_SUPERVISOR_EPS);
        query.setParameter("userIdSupervisorEPS", supervisorEPS.getUserId());
        query.setParameter("RECHAZADO", StateProcess.RECHAZADO);
        query.setParameter("INACTIVO", StateProcess.INACTIVO);
        return query.getResultList();
    }

    public boolean isAssignedAdvisorReviewer(Process process) {
        if (process.getAppointmentId() != null) {
            Query query = entityManager.createQuery(GET_APPOINTMENT_BY_PROCESS);
            query.setParameter("appointmentId", process.getAppointmentId().getId());
            Appointment temp = (Appointment) query.getSingleResult();
            return (temp.getAdviserState() == appointmentState.APPROVED || temp.getAdviserState() == appointmentState.ELECTION
                    && temp.getReviewerState() == appointmentState.APPROVED || temp.getReviewerState() == appointmentState.ELECTION);
        }
        return false;
    }

    public void revisionRemainer() {
        for (Process normalTime : revisionRemainer(false)) {
            mailService.emailRevisionRemainerStudent(normalTime, ANTICIPATED_TIME, false);
            mailService.emailRevisionRemainerSupervisor(normalTime, ANTICIPATED_TIME, false);
        }

        for (Process normalTime : revisionRemainer(true)) {
            mailService.emailRevisionRemainerStudent(normalTime, ANTICIPATED_TIME, true);
            mailService.emailRevisionRemainerSupervisor(normalTime, ANTICIPATED_TIME, true);
        }
    }
}
