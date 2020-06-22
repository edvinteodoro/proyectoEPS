package gt.edu.usac.cunoc.ingenieria.eps.process.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.JAVA_MAIL_SESSION;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCoordinator;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import static gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer_.process;
import gt.edu.usac.cunoc.ingenieria.eps.user.service.UserService;
import static gt.edu.usac.cunoc.ingenieria.eps.user.service.UserService.CONTENT_TYPE;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
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

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Resource(name = JAVA_MAIL_SESSION)
    private Session emailSession;

    public static final String GET_PROCESS_USER = "SELECT c.process FROM UserCareer c WHERE c.uSERuserId.userId=:userId AND c.process IS NOT NULL";
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
        Query query = entityManager.createQuery(GET_PROCESS_USER);
        query.setParameter(ID_PARAMETER_NAME, user.getUserId());
        return query.getResultList();
    }

    public boolean SendEmailStudent(TailCoordinator tailCoordinator,String title, String msg) {
        try {
            sendEmail(tailCoordinator.getProcess().getUserCareer().getUSERuserId().getEmail(), title, emailBody(tailCoordinator,title, msg));
            return true;
        } catch (Exception e) {
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

    private String emailBody(TailCoordinator tailCoordinator,String title,String mensaje) {
        return ("<h2><strong>"+title+"</strong></h2>"
                + "<p>" + tailCoordinator.getUserCareer().getUSERuserId().getROLid().getName() + ": " +tailCoordinator.getUserCareer().getCAREERcodigo().getName()+ "</p>"
                +"<p>"+ tailCoordinator.getUserCareer().getUSERuserId().getFirstName() + " " + tailCoordinator.getUserCareer().getUSERuserId().getLastName() + "</p>"
                + "<p><span style=\"color: #000000;\">" + mensaje + "</span></p>"
                + "<p>Divisi&oacute;n de Ciencias de la Ingenieria - Centro Universitario de Occidente</p>");
    }
}
