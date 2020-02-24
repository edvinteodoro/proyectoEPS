package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.RequerimentFacadeLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.FlowEvent;

@Named
@ViewScoped
public class CreateProcessView implements Serializable{

    @EJB
    RequerimentFacadeLocal requerimentFacade;

    private Process process;

    Requeriment requeriment;
    private boolean skip;

    public Requeriment getRequeriment() {
        if (requeriment == null) {
            requeriment = new Requeriment();
        }
        return requeriment;
    }

    public Process getProcess() {
        if (process == null) {
            process = new Process();
        }
        return process;
    }
    
    public void guardar(){
    
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }
}
