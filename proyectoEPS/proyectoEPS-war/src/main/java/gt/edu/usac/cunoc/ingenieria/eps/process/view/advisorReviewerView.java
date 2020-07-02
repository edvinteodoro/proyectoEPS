package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ESTUDIANTE;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SUPERVISOR_EPS;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ASESOR;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.REVISOR;
import gt.edu.usac.cunoc.ingenieria.eps.process.Appointment;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import static gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess.REVISION;
import gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState;
import static gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState.APPROVED;
import static gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState.CHANGE;
import static gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState.NEW;
import static gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState.REVIEW;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author angelrg
 */
@Named
@ViewScoped
public class advisorReviewerView implements Serializable {
    
    @EJB
    private UserFacadeLocal userFacade;
    
    @EJB
    private ProcessFacadeLocal processFacade;
    
    private List<Process> processAvailable;
    private List<User> elegible;
    
    private Process processSelected;
    private User actualUser;
    private boolean isAdvisor;
    
    @PostConstruct
    public void init() {
        try {
            Optional<User> currentUser = Optional.ofNullable(userFacade.getAuthenticatedUser().get(0));
            if (currentUser.isPresent()) {
                if (currentUser.get().getROLid().getName().equals(ESTUDIANTE)) {
                    setProcessAvailable(findProcessAvailable(processFacade.getProcessUser(currentUser.get())));
                } else if (currentUser.get().getROLid().getName().equals(SUPERVISOR_EPS)) {
                    //TO-DO logic to manage supervisor
                } else {
                    MessageUtils.addErrorMessage("No cuenta con los permisos");
                }
            }
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }
    
    private List<Process> findProcessAvailable(List<Process> process) {
        List<Process> result = new LinkedList<>();
        
        for (Process proces : process) {
            if ((proces.getApprovedCareerCoordinator() != null && proces.getApprovedCareerCoordinator())
                    && (proces.getApprovalEPSCommission() != null && proces.getApprovalEPSCommission())
                    && (proces.getAppointmentId() == null || (proces.getAppointmentId().getAdviserState() == CHANGE))) {
                result.add(proces);
            }
        }
        
        return result;
    }
    
    public void createNewUser(Process process, boolean advisor) {
        processSelected = process;
        isAdvisor = advisor;
        if (processSelected.getAppointmentId() == null) {
            processSelected.setAppointmentId(new Appointment());
        }
        
        try {
            if (advisor && processSelected.getAppointmentId().getUserAdviser() == null) {
                actualUser = new User();
                Rol actualRol = userFacade.getRolUser(new Rol(null, ASESOR)).get(0);
                actualUser.setrOLid(actualRol);
            } else if (!advisor && processSelected.getAppointmentId().getUserReviewer() == null) {
                actualUser = new User();
                actualUser.setrOLid(userFacade.getRolUser(new Rol(null, REVISOR)).get(0));
            } else {
                clean();
                MessageUtils.addErrorMessage("El usuario ya existe");
            }
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }
    
    public void saveNewUser(final String modalIdToClose) {
        try {
            if (existsUser(actualUser)) {
                MessageUtils.addErrorMessage("El usuario ya existe con ese cargo");
            } else {
                if (isAdvisor) {
                    processSelected.getAppointmentId().setUserAdviser(actualUser);
                } else {
                    processSelected.getAppointmentId().setUserReviewer(actualUser);
                }
                PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
                MessageUtils.addSuccessMessage("Usuario agregado");
                clean();
            }
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }
    
    public void saveSelectedUser(final String modalIdToClose, User user) {
        
        if (user != null) {
            if (isAdvisor) {
                processSelected.getAppointmentId().setUserAdviser(user);
                PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
                MessageUtils.addSuccessMessage("Asesor agregado");
            } else {
                processSelected.getAppointmentId().setUserReviewer(user);
                PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
                MessageUtils.addSuccessMessage("Revisor agregado");
            }
        } else {
            MessageUtils.addErrorMessage("Debe elegir un usuario");
        }
    }

    /**
     * Send User's Review and advisor appointment to the Supervisor
     *
     * @param process
     */
    public void sendAppointmentToSupervisor(Process process) {
        processSelected = process;
        
        if (processSelected != null && processSelected.getAppointmentId() != null) {
            
            try {
                if (processSelected.getAppointmentId().getUserAdviser() != null && processSelected.getAppointmentId().getUserReviewer() != null) {
                    
                    if (processSelected.getAppointmentId().getAdviserState() == null || processSelected.getAppointmentId().getAdviserState() != APPROVED) {
                        
                        if (existsUser(processSelected.getAppointmentId().getUserAdviser())) {
                            processSelected.getAppointmentId().setAdviserState(REVIEW);
                        } else {
                            processSelected.getAppointmentId().setUserAdviser(userFacade.createTempUser(processSelected.getAppointmentId().getUserAdviser()));
                            processSelected.getAppointmentId().setAdviserState(NEW);
                        }
                    }
                    if (processSelected.getAppointmentId().getReviewerState() == null
                            || processSelected.getAppointmentId().getReviewerState() != APPROVED) {
                        
                        if (existsUser(processSelected.getAppointmentId().getUserReviewer())) {
                            processSelected.getAppointmentId().setReviewerState(REVIEW);
                        } else {
                            processSelected.getAppointmentId().setUserReviewer(userFacade.createTempUser(processSelected.getAppointmentId().getUserReviewer()));
                            processSelected.getAppointmentId().setReviewerState(NEW);
                        }
                    }
                    
                    processFacade.updateProcess(processSelected);
                    MessageUtils.addSuccessMessage("Se ha enviado a su supervisor");
                    clean();
                    init();
                } else {
                    if (processSelected.getAppointmentId().getUserAdviser() == null) {
                        MessageUtils.addErrorMessage("Debe indicar el Asesor");
                    } else {
                        MessageUtils.addErrorMessage("Debe indicar el Revisor");
                    }
                }
            } catch (UserException e) {
                MessageUtils.addErrorMessage(e.getMessage());
            }
        } else {
            MessageUtils.addErrorMessage("Debe elegir un proceso");
        }
    }
    
    private boolean existsUser(User user) throws UserException {
        User search = new User();
        search.setrOLid(user.getROLid());
        search.setDpi(user.getDpi());
        
        return (!userFacade.getUser(search).isEmpty());
    }
    
    public void loadElegibleUsers(Process process, boolean advisor) {
        processSelected = process;
        isAdvisor = advisor;
        if (processSelected.getAppointmentId() == null) {
            processSelected.setAppointmentId(new Appointment());
        }
        
        if (advisor && processSelected.getAppointmentId().getUserAdviser() == null) {
            loadAdvisors();
        } else if (!advisor && processSelected.getAppointmentId().getUserReviewer() == null) {
            loadAReviewer();
        } else {
            clean();
            MessageUtils.addErrorMessage("El usuario ya existe");
        }
    }
    
    public void showUserInformation(Process process, boolean advisor) {
        processSelected = process;
        isAdvisor = advisor;
        if (processSelected.getAppointmentId() == null) {
            MessageUtils.addErrorMessage("Debe ingresa los usuarios primero");
        }
        
        if (advisor && processSelected.getAppointmentId().getUserAdviser() != null) {
            actualUser = processSelected.getAppointmentId().getUserAdviser();
        } else if (!advisor && processSelected.getAppointmentId().getUserReviewer() != null) {
            actualUser = processSelected.getAppointmentId().getUserReviewer();
        } else {
            clean();
            MessageUtils.addErrorMessage("El usuario inexistente");
        }
    }

//    public String showUserStatus(Process process, boolean advisor){
//        if (advisor) {
//            return
//        }
//    }
    private void loadAdvisors() {
        try {
            User searchU = new User(new Rol(null, ASESOR), null);
            searchU.setState(true);
            setElegible(userFacade.getUser(searchU));
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }
    
    private void loadAReviewer() {
        try {
            User searchU = new User(new Rol(null, REVISOR), null);
            searchU.setState(true);
            setElegible(userFacade.getUser(searchU));
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    /**
     * Indicate if the advisor was acepted or had been set
     *
     * @param process
     * @return
     */
    public Boolean advisorAlreadyExist(Process process) {
        return ((process.getAppointmentId() != null && process.getAppointmentId().getAdviserState() != null && process.getAppointmentId().getAdviserState() == APPROVED)
                || (process.getAppointmentId() != null && process.getAppointmentId().getUserAdviser() != null));
    }

    /**
     * Indicate if the reviewer was acepted or had been set
     *
     * @param process
     * @return
     */
    public Boolean reviewerAlreadyExist(Process process) {
        return ((process.getAppointmentId() != null && process.getAppointmentId().getReviewerState() != null && process.getAppointmentId().getReviewerState() == APPROVED)
                || (process.getAppointmentId() != null && process.getAppointmentId().getUserReviewer() != null));
    }
    
    public String actualSelect() {
        if (isAdvisor) {
            return ASESOR;
        }
        return REVISOR;
    }
    
    public List<Process> getProcessAvailable() {
        return processAvailable;
    }
    
    public void setProcessAvailable(List<Process> processAvailable) {
        this.processAvailable = processAvailable;
    }
    
    public List<User> getElegible() {
        return elegible;
    }
    
    public void setElegible(List<User> elegible) {
        this.elegible = elegible;
    }
    
    public Process getProcessSelected() {
        if (processSelected == null) {
            processSelected = new Process();
        }
        return processSelected;
    }
    
    public void setProcessSelected(Process processSelected) {
        this.processSelected = processSelected;
    }
    
    public User getActualUser() {
        return actualUser;
    }
    
    public void setActualUser(User actualUser) {
        if (actualUser == null) {
            actualUser = new User();
        }
        this.actualUser = actualUser;
    }
    
    public void newUserIsAdvisor(boolean advisor) {
        isAdvisor = advisor;
    }
    
    public void selectUserIsAdvisor(boolean advisor) {
        isAdvisor = advisor;
        if (advisor) {
            loadAdvisors();
        } else {
            loadAReviewer();
        }
    }
    
    public boolean isIsAdvisor() {
        return isAdvisor;
    }

    /**
     * Clean the advisors and reviewers list, setting them at null
     */
    public void clean() {
        elegible = null;
        actualUser = null;
    }
}
