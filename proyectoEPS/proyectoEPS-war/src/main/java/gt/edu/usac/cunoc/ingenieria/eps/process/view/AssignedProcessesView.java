package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
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

    public Boolean studentAppointmentApproved() {
        return (processSelected != null && processSelected.getAppointmentId() != null
                && processSelected.getAppointmentId().getAdviserState() != null
                && processSelected.getAppointmentId().getReviewerState() != null
                && (processSelected.getAppointmentId().getAdviserState() == appointmentState.APPROVED
                || processSelected.getAppointmentId().getAdviserState() == appointmentState.ELECTION)
                && (processSelected.getAppointmentId().getReviewerState() == appointmentState.APPROVED
                || processSelected.getAppointmentId().getReviewerState() == appointmentState.ELECTION));
    }

    public void reloadAdviserResume() {
        adviserResumeStream = new DefaultStreamedContent(new ByteArrayInputStream(processSelected.getAppointmentId().getUserAdviser().getPersonalResume()), "application/pdf", "Curriculum.pdf");
    }

    public void reloadReviewerResume() {
        reviewerResumeStream = new DefaultStreamedContent(new ByteArrayInputStream(processSelected.getAppointmentId().getUserReviewer().getPersonalResume()), "application/pdf", "Curriculum.pdf");
    }

    public Boolean companySupervisorExist(Process process) {
        return (process.getApprovalEPSCommission() != null && process.getApprovedCareerCoordinator() != null
                && process.getApprovalEPSCommission() && process.getApprovedCareerCoordinator()
                && process.getAppointmentId() != null && process.getAppointmentId().getCompanySupervisor() != null);
    }

    public Boolean isRequestAdvisorReviewerReviewState() {
        return (processSelected != null && processSelected.getAppointmentId() != null
                && processSelected.getAppointmentId().getAdviserState() != null
                && processSelected.getAppointmentId().getReviewerState() != null
                && processSelected.getAppointmentId().getAdviserState() == appointmentState.REVIEW
                && processSelected.getAppointmentId().getReviewerState() == appointmentState.REVIEW);
    }

    public Boolean canRequestAdvisorReviewer() {
        return (processSelected != null && processSelected.getApprovalEPSCommission() != null && processSelected.getApprovedCareerCoordinator() != null
                && processSelected.getApprovalEPSCommission() && processSelected.getApprovedCareerCoordinator());
    }

    public Boolean companySupervisorExist() {
        return (processSelected != null && processSelected.getApprovalEPSCommission() != null && processSelected.getApprovedCareerCoordinator() != null
                && processSelected.getApprovalEPSCommission() && processSelected.getApprovedCareerCoordinator()
                && processSelected.getAppointmentId() != null && processSelected.getAppointmentId().getCompanySupervisor() != null);
    }

    public Boolean canAddCompanySupervisor() {
        return (processSelected != null && processSelected.getApprovalEPSCommission() != null && processSelected.getApprovedCareerCoordinator() != null
                && processSelected.getApprovalEPSCommission() && processSelected.getApprovedCareerCoordinator());
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
        processSelected = null;
    }

}
