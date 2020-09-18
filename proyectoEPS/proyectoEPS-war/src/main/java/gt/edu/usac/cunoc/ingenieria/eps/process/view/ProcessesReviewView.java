package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.*;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCommitteeEPSFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCoordinatorFacadeLocal;
import java.util.ArrayList;

@Named
@ViewScoped
public class ProcessesReviewView implements Serializable {

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private TailCoordinatorFacadeLocal tailFacade;

    @EJB
    private TailCommitteeEPSFacadeLocal tailCommitteeEPSFacade;

    private List<Process> processes;

    private User userLogged;

    private Process processSelected;
    private User supervisorEPS;

    private boolean committeeEPS;

    private boolean viewProcesses;
    
    @PostConstruct
    public void init() {
        try {
            processes = new ArrayList<>();
            userLogged = userFacade.getAuthenticatedUser().get(0);
            switch (userLogged.getROLid().getName()) {
                case COORDINADOR_EPS:
                case SUPERVISOR_EPS:
                    if (userLogged.getEpsCommittee()) {
                        this.processes = tailCommitteeEPSFacade.getTailCommitteeEPS();
                        committeeEPS = true;
                        viewProcesses = true;
                    } else {
                        viewProcesses = false;
                    }
                    break;
                case COORDINADOR_CARRERA:
                    this.processes = tailFacade.getProcessByCoordinator(userLogged);
                    committeeEPS = false;
                    viewProcesses = true;
                    break;
            }
        } catch (UserException e) {
            System.out.println("Error de Autenticación del Usuario: " + e);
        }
    }

    public String getTitle() {
        switch (userLogged.getROLid().getName()) {
            case COORDINADOR_EPS:
            case SUPERVISOR_EPS:
                return "Revisión de Anteproyecto - Comité EPS";
            case COORDINADOR_CARRERA:
                return "Revisión de Anteproyecto - Coordinador de Carrera";
            default:
                return "Revisión de Procesos -";
        }
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public User getSupervisorEPS() {
        return supervisorEPS;
    }

    public void setSupervisorEPS(User supervisorEPS) {
        this.supervisorEPS = supervisorEPS;
    }

    public Process getProcessSelected() {
        return processSelected;
    }

    public void setProcessSelected(Process processSelected) {
        this.processSelected = processSelected;
    }

    public boolean isCommitteeEPS() {
        return committeeEPS;
    }

    public void setCommitteeEPS(boolean committeeEPS) {
        this.committeeEPS = committeeEPS;
    }

    public boolean isViewProcesses() {
        return viewProcesses;
    }

    public void setViewProcesses(boolean viewProcesses) {
        this.viewProcesses = viewProcesses;
    }

    public void clean() {
        processSelected = null;
        supervisorEPS = null;
    }

}
