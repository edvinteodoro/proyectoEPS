package gt.edu.usac.cunoc.ingenieria.eps.journal;

import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ASESOR;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.REVISOR;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SUPERVISOR_EMPRESA;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SUPERVISOR_EPS;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.journal.facade.CommentaryFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.journal.facade.JournalLogFacadeLocal;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;

@Named
@ViewScoped
public class JournalReviewView implements Serializable {

    @Inject
    private ExternalContext externalContext;

    @EJB
    private ProcessFacadeLocal processFacade;

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private JournalLogFacadeLocal journalFacade;

    @EJB
    private CommentaryFacadeLocal commentaryFacade;

    private Integer processId;
    private Process process;
    private JournalLog journalSelected;
    private String commentText;
    private LocalDate dateNow;

    private User userLogged;

    private List<JournalLog> journals;

    public void loadCurrentProject() throws IOException {
        try {
            dateNow = LocalDate.now();
            this.userLogged = userFacade.getAuthenticatedUser().get(0);
            this.process = processFacade.getProcess(new Process(processId)).get(0);
            if (validateUser(process, userLogged)) {
                this.journals = journalFacade.getJournal(processId);
            } else {
                externalContext.redirect(externalContext.getRequestContextPath() + "/error/error-403.xhtml");
            }
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        } catch (IndexOutOfBoundsException ex) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/error/error-404.xhtml");
        }
    }

    private boolean validateUser(Process currentProcess, User userlogged) throws IOException {
        List<Process> assignedProcesses;
        switch (userlogged.getROLid().getName()) {
            case SUPERVISOR_EPS:
                return currentProcess.getSupervisor_EPS().equals(userlogged);
            case ASESOR:
                assignedProcesses = processFacade.getProcessByAdviser(userlogged);
                return existProcessOnList(currentProcess, assignedProcesses);
            case REVISOR:
                assignedProcesses = processFacade.getProcessByReviewer(userlogged);
                return existProcessOnList(currentProcess, assignedProcesses);
            case SUPERVISOR_EMPRESA:
                assignedProcesses = processFacade.getProcessByCompanySupervisor(userlogged);
                return existProcessOnList(currentProcess, assignedProcesses);
            default:
                return false;
        }
    }

    private boolean existProcessOnList(Process process, List<Process> processes) {
        return processes.stream().anyMatch(assignedProcess -> (process.getId().compareTo(assignedProcess.getId()) == 0));
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer porcessId) {
        this.processId = porcessId;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public JournalLog getJournalSelected() {
        return journalSelected;
    }

    public void setJournalSelected(JournalLog journalSelected) {
        this.journalSelected = journalSelected;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public List<JournalLog> getJournals() {
        return journals;
    }

    public void setJournals(List<JournalLog> journals) {
        this.journals = journals;
    }

    public void comment() {
        try {
            Commentary newCommentary = new Commentary();
            newCommentary.setDate(dateNow);
            newCommentary.setJournal_Log(getJournalSelected());
            newCommentary.setUser(userLogged);
            newCommentary.setText(commentText);
            commentaryFacade.createCommentary(newCommentary);
            MessageUtils.addSuccessMessage("Comentario Realizado");
            setCommentText("");
        } catch (MandatoryException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    public void cleanJournalSelected() {
        setCommentText("");
        setJournalSelected(null);
    }

    public void enableJournal() throws IOException {
        try {
            journalFacade.enableJournal(process);
            externalContext.redirect(externalContext.getRequestContextPath() + "/process/assignedProcesses.xhtml");
            MessageUtils.addSuccessMessage("Bitácora Habilitada");
        } catch (ValidationException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    public List<Commentary> getCommentariesByJournal(Integer journalId) {
        return commentaryFacade.getCommentariesByJournalId(journalId);
    }

    public Boolean viewButtonEneable() {
        return !process.getApprovedEPSDevelopment() && canEnableJournalSupervisorEPS();
    }

    public Boolean emptyJournal() {
        return journals.isEmpty() && process.getApprovedEPSDevelopment();
    }

    public Boolean isDisableJournal() {
        return !process.getApprovedEPSDevelopment() && !canEnableJournalSupervisorEPS();
    }

    public Boolean canEnableJournalSupervisorEPS() {
        return process.getAppointmentId() != null
                && process.getAppointmentId().getCompanySupervisor() != null && process.getAppointmentId().getCompanySupervisor().getStatus()
                && process.getAppointmentId().getAdviserState() != appointmentState.CHANGE && process.getAppointmentId().getAdviserState() != appointmentState.REVIEW
                && process.getAppointmentId().getReviewerState() != appointmentState.CHANGE && process.getAppointmentId().getReviewerState() != appointmentState.REVIEW;
    }
    
    public Boolean isUserLoggedSupervisorEPS(){
        return userLogged.getROLid().getName().equals(SUPERVISOR_EPS);
    }
    
}
