package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.*;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Appointment;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCoordinatorFacadeLocal;

@Named
@ViewScoped
public class ProcessesView implements Serializable {

    @Inject
    private ExternalContext externalContext;

    @EJB
    private ProcessFacadeLocal processFacade;

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private TailCoordinatorFacadeLocal tailFacade;

    private List<Process> processes;
    private Boolean careerCoordinator;

    private User user;
    private Appointment appointment;
    private StreamedContent adviserResumeStream;
    private StreamedContent reviewerResumeStream;

    private Process processSelected;
    private User companySupervisor;
    private User supervisorEPS;

    @PostConstruct
    public void init() {
        try {
            careerCoordinator = false;
            user = userFacade.getAuthenticatedUser().get(0);
            switch (user.getROLid().getName()) {
                case ESTUDIANTE:
                    processes = processFacade.getProcessUser(user);
                    break;
                case COORDINADOR_CARRERA:
                    processes = tailFacade.getProcessByCoordinator(user);
                    careerCoordinator = true;
                    break;
            }
            if (processes != null) {
                if (processes.get(0) == null) {
                    processes = null;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro+-------------------------------------------\n" + e);
        }
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public void goToProcess(Process process) {

    }

    public Boolean isCareerCoordinator() {
        return careerCoordinator;
    }

    public String redirectProcessTo() {
        String value = "";
        switch (user.getROLid().getName()) {
            case ESTUDIANTE:
                value = "project";
                break;
            case COORDINADOR_CARRERA:
                value = "projectReview";
                break;
        }
        return value;
    }

    public String redirectJournalTo() {
        String value = "";
        switch (user.getROLid().getName()) {
            case ESTUDIANTE:
                value = "journal";
                break;
            case SUPERVISOR_EPS:
            case REVISOR:
            case ASESOR:
                value = "journalReview";
                break;
        }
        return value;
    }

    public Boolean getIsFirst(Integer id) {
        return (id == getProcesses().get(0).getId());
    }

    public Boolean studentAppointmentApproved(Process process) {
        return (process != null && process.getAppointmentId() != null
                && process.getAppointmentId().getAdviserState() != null
                && process.getAppointmentId().getReviewerState() != null
                && (process.getAppointmentId().getAdviserState() == appointmentState.APPROVED
                || process.getAppointmentId().getAdviserState() == appointmentState.ELECTION)
                && (process.getAppointmentId().getReviewerState() == appointmentState.APPROVED
                || process.getAppointmentId().getReviewerState() == appointmentState.ELECTION));
    }

    /**
     * create a empty user with the role set, this is for the User
     *
     * @param process
     */
    public void createNewUser(Process process) {
        processSelected = process;
        if (processSelected.getAppointmentId() == null) {
            processSelected.setAppointmentId(new Appointment());
        }

        try {
            if (processSelected.getAppointmentId().getCompanySupervisor() == null) {

                companySupervisor = new User();
                companySupervisor.setROLid(userFacade.getRolUser(new Rol(null, SUPERVISOR_EMPRESA)).get(0));
            } else {
                clean();
                MessageUtils.addErrorMessage("El usuario ya existe");
            }
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    /**
     * Save in memory the user added by the Student
     *
     * @param modalIdToClose
     */
    public void saveNewUser(final String modalIdToClose) {
        try {
            if (existsUser(companySupervisor)) {
                MessageUtils.addErrorMessage("El usuario ya existe con ese cargo");
            } else {
                processSelected.getAppointmentId().setCompanySupervisor(companySupervisor);
                processSelected = processFacade.sendCompanySupervisorToSupervisor(processSelected);
                PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
                MessageUtils.addSuccessMessage("Usuario agregado");
                clean();
            }
        } catch (UserException | ValidationException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    public Boolean canAddCompanySupervisor(Process process) {
        return (process.getApprovalEPSCommission() != null && process.getApprovedCareerCoordinator() != null
                && process.getApprovalEPSCommission() && process.getApprovedCareerCoordinator());
    }

    public Boolean companySupervisorExist(Process process) {
        return (process.getApprovalEPSCommission() != null && process.getApprovedCareerCoordinator() != null
                && process.getApprovalEPSCommission() && process.getApprovedCareerCoordinator()
                && process.getAppointmentId() != null && process.getAppointmentId().getCompanySupervisor() != null);
    }

    public void reloadAdviserResume() {
        adviserResumeStream = new DefaultStreamedContent(new ByteArrayInputStream(appointment.getUserAdviser().getPersonalResume()), "application/pdf", "Curriculum.pdf");
    }

    public void reloadReviewerResume() {
        reviewerResumeStream = new DefaultStreamedContent(new ByteArrayInputStream(appointment.getUserReviewer().getPersonalResume()), "application/pdf", "Curriculum.pdf");
    }

    private boolean existsUser(User user) throws UserException {
        User search = new User();
        search.setROLid(user.getROLid());
        search.setDpi(user.getDpi());

        return (!userFacade.getUser(search).isEmpty());
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public StreamedContent getAdviserResumeStream() {
        return adviserResumeStream;
    }

    public void setAdviserResumeStream(StreamedContent adviserResumeStream) {
        this.adviserResumeStream = adviserResumeStream;
    }

    public StreamedContent getReviewerResumeStream() {
        return reviewerResumeStream;
    }

    public void setReviewerResumeStream(StreamedContent reviewerResumeStream) {
        this.reviewerResumeStream = reviewerResumeStream;
    }

    public User getCompanySupervisor() {
        return companySupervisor;
    }

    public void setCompanySupervisor(User companySupervisor) {
        this.companySupervisor = companySupervisor;
    }

    public User getSupervisorEPS() {
        return supervisorEPS;
    }

    public void setSupervisorEPS(User supervisorEPS) {
        this.supervisorEPS = supervisorEPS;
    }

    public String getTitle() {
        switch (user.getROLid().getName()) {
            case ESTUDIANTE:
                return "Mis Procesos";
            case COORDINADOR_CARRERA:
                return "Procesos para Revisión";
            default:
                return "Procesos";
        }
    }

    public void clean() {
        adviserResumeStream = null;
        reviewerResumeStream = null;
        appointment = null;
        processSelected = null;
        companySupervisor = null;
        supervisorEPS = null;
    }
    
}
