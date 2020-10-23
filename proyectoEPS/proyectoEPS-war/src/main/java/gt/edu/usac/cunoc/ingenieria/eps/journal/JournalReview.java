package gt.edu.usac.cunoc.ingenieria.eps.journal;

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
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;

@Named
@ViewScoped
public class JournalReview implements Serializable {

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

    private User autenticatedUser;

    private List<JournalLog> journals;

    public void loadCurrentProject() {
        try {
            dateNow = LocalDate.now();
            autenticatedUser = userFacade.getAuthenticatedUser().get(0);
            this.process = processFacade.getProcess(new Process(processId)).get(0);
            this.journals = journalFacade.getJournal(processId);
        } catch (Exception e) {
            System.out.println("--------" + e + "--------");
        }
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
            newCommentary.setUser(autenticatedUser);
            newCommentary.setText(commentText);
            commentaryFacade.createCommentary(newCommentary);
            MessageUtils.addSuccessMessage("Comentario Realizado");
        } catch (MandatoryException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    public void cleanJournalSelected() {
        setCommentText("");
        setJournalSelected(null);
    }

    public void enableJournal() {
        try {
            journalFacade.enableJournal(process);
            MessageUtils.addSuccessMessage("Bitácora Habilitada");
        } catch (ValidationException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    public List<Commentary> getCommentariesByJournal(Integer journalId) {
        return commentaryFacade.getCommentariesByJournalId(journalId);
    }

    public Boolean canEnableJournalSupervisorEPS(){
        return process.getAppointmentId() != null 
                && process.getAppointmentId().getCompanySupervisor() != null && process.getAppointmentId().getCompanySupervisor().getStatus()
                && process.getAppointmentId().getAdviserState() != appointmentState.CHANGE && process.getAppointmentId().getAdviserState() != appointmentState.REVIEW
                && process.getAppointmentId().getReviewerState()!= appointmentState.CHANGE && process.getAppointmentId().getReviewerState()!= appointmentState.REVIEW;
    }
}
