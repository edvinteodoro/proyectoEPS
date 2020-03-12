package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ProcessesView implements Serializable {
    @Inject
    private ExternalContext externalContext;
    
    @EJB
    private ProcessFacadeLocal processFacade;

    private List<Process> processes;

    @PostConstruct
    public void init() {
        processes = processFacade.getProcess(new Process());
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public void goToProcess(Process process) {
        
    }

}
