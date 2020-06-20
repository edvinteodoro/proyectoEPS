
package gt.edu.usac.cunoc.ingenieria.eps.committeeEPS.view;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCommitteeEPSFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
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
    
    @EJB
    private UserFacadeLocal userFacade;
        
    private List<Process> processes;

    private User user;
    
    private Boolean epsCommittee;
    
    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public Boolean getEpsCommittee() {
        return epsCommittee;
    }

    public void setEpsCommittee(Boolean epsCommittee) {
        this.epsCommittee = epsCommittee;
    }

    @PostConstruct
    public void init(){
        try {
            user = userFacade.getAuthenticatedUser().get(0);
            this.epsCommittee = user.getEpsCommittee();
            if (epsCommittee){
                this.processes = tailCommitteeEPSFacade.getTailCommitteeEPS();
            } else {
                this.processes = new ArrayList<>();
            }
        } catch (UserException ex) {
            System.out.println("Error Autenticación");
        }
    }
    
}
