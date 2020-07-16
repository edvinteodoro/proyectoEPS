package gt.edu.usac.cunoc.ingenieria.eps.bitacora.view;

import gt.edu.usac.cunoc.ingenieria.eps.journal.Commentary;
import gt.edu.usac.cunoc.ingenieria.eps.journal.JournalLog;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;

@Named
@ViewScoped
public class BitacoraReview implements Serializable {

    @EJB
    private ProcessFacadeLocal processFacade;

    @EJB
    private UserFacadeLocal userFacade;

    private Integer processId;
    private Process process;
    private JournalLog journalSelected;
    private String commentText;
    private LocalDate dateNow;

    private User autenticatedUser;

    public void loadCurrentProject() {
        try {
            dateNow = LocalDate.now();
            autenticatedUser = userFacade.getAuthenticatedUser().get(0);
            this.process = processFacade.getProcess(new Process(processId)).get(0);
        } catch (Exception e) {
            System.out.println("--------"+e+"--------");
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

    public List<JournalLog> getJournalsLog() {
        return getProcess().getJournalLog();
    }

    public void setJournalsLogs(List<JournalLog> journalsLog) {
        getProcess().setJournalLog(journalsLog);
    }

    public void comment() {
        getJournalSelected().getCommentaries().add(new Commentary(getCommentText(), dateNow, getJournalSelected(),autenticatedUser)); 
        processFacade.updateProcess(process);
    }
    
    public void cleanJournalSelected(){
        setCommentText("");
        setJournalSelected(null);
    }

}
