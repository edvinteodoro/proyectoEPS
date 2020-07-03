package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AssignedProcessesView implements Serializable {

    @EJB
    private UserFacadeLocal userFacade;
    
    @EJB
    private ProcessFacadeLocal processFacade;
    
    private List<Process> processes;

    private User user;

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
}
