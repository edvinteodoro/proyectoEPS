package gt.edu.usac.cunoc.ingenieria.eps.bitacora.view;

import gt.edu.usac.cunoc.ingenieria.eps.journal.JournalLog;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import java.util.List;
import javax.ejb.EJB;

@Named
@ViewScoped
public class BitacoraReview implements Serializable {

    @EJB
    private ProcessFacadeLocal processFacade;

    private Integer processId;
    private Process process;

    public void loadCurrentProject() {
        this.process = processFacade.getProcess(new Process(processId)).get(0);
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

    public List<JournalLog> getJournalsLog() {
        return this.process.getJournalLog();
    }
    
    public void setJournalsLogs(List<JournalLog> journalsLog){
        this.process.setJournalLog(journalsLog); 
    }

}
