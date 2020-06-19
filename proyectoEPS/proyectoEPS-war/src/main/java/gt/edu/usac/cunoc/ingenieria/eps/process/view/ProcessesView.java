package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_CARRERA;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ESTUDIANTE;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
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

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private TailFacadeLocal tailFacade;

    private List<Process> processes;
    private Boolean careerCoordinator;

    private User user;

    @PostConstruct
    public void init() {
        try {
            careerCoordinator = false;
            user = userFacade.getAuthenticatedUser().get(0);
            switch (user.getROLid().getName()){    
                case ESTUDIANTE:
                    processes = processFacade.getProcessUser(user);
                    break;
                case COORDINADOR_CARRERA:
                    processes = tailFacade.getProcessByCoordinator(user);
                    careerCoordinator = true;
                    break;
            }
            if (processes != null) {
                if (processes.get(0) == null) {
                    processes = null;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro+-------------------------------------------\n"+e);
        }
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public void goToProcess(Process process) {

    }

    public Boolean isCareerCoordinator() {
        return careerCoordinator;
    }

    public Boolean getIsFirst(Integer id) {
        if (id == getProcesses().get(0).getId()) {
            return true;
        }
        return false;
    }
}
