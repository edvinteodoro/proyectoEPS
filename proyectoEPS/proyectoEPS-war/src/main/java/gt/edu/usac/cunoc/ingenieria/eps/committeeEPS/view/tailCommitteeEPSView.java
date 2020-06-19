
package gt.edu.usac.cunoc.ingenieria.eps.committeeEPS.view;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCommitteeEPSFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class tailCommitteeEPSView implements Serializable{
    
    @EJB
    private TailCommitteeEPSFacadeLocal tailCommitteeEPSFacade;
    
    private List<Process> processes;

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    @PostConstruct
    public void init(){
        this.processes = tailCommitteeEPSFacade.getTailCommitteeEPS();
    }
    
    
}
