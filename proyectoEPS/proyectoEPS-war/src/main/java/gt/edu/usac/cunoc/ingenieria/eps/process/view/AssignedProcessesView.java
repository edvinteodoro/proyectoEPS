package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Appointment;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@ViewScoped
public class AssignedProcessesView implements Serializable {

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private ProcessFacadeLocal processFacade;

    private List<Process> processes;

    private User user;
    private Appointment appointment;
    private StreamedContent adviserResumeStream;
    private StreamedContent reviewerResumeStream;
    private Process processSelected;

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    @PostConstruct
    public void init() {
        try {
            user = userFacade.getAuthenticatedUser().get(0);
            setProcesses(processFacade.getProcessBySupervisorEPS(user));
        } catch (UserException ex) {
            System.out.println("Error Autenticación");
        }
    }

    /**
     * return the appointment to the Student
     *
     * @param modalIdToClose
     */
    public void aproveCompanySupervisor(final String modalIdToClose) {
        try {
            processSelected = processFacade.enableCompanySupervisor(processSelected);
            init();
            PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
            MessageUtils.addSuccessMessage("Se ha notificado a los interesados de la desición");
        } catch (UserException | ValidationException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
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

    public void reloadAdviserResume() {
        adviserResumeStream = new DefaultStreamedContent(new ByteArrayInputStream(appointment.getUserAdviser().getPersonalResume()), "application/pdf", "Curriculum.pdf");
    }

    public void reloadReviewerResume() {
        reviewerResumeStream = new DefaultStreamedContent(new ByteArrayInputStream(appointment.getUserReviewer().getPersonalResume()), "application/pdf", "Curriculum.pdf");
    }

    public Boolean companySupervisorExist(Process process) {
        return (process.getApprovalEPSCommission() != null && process.getApprovedCareerCoordinator() != null
                && process.getApprovalEPSCommission() && process.getApprovedCareerCoordinator()
                && process.getAppointmentId() != null && process.getAppointmentId().getCompanySupervisor() != null);
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

    public Process getProcessSelected() {
        return processSelected;
    }

    public void setProcessSelected(Process processSelected) {
        this.processSelected = processSelected;
    }

    public void clean() {
        adviserResumeStream = null;
        reviewerResumeStream = null;
        appointment = null;
        processSelected = null;
    }
    
    public boolean isAssignedAdviserReviewer(Process process){
        return processFacade.isAssignedAdvisorReviewer(process);
    }
}
