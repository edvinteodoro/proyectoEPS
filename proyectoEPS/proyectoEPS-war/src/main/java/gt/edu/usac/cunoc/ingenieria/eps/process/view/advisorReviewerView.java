package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ESTUDIANTE;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SUPERVISOR_EPS;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ASESOR;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.REVISOR;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Appointment;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import static gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState.*;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

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
    Optional<User> loggedUser;

    private Process processSelected;
    private User actualUser;
    private boolean isAdvisor;

    private StreamedContent personalResumeStream;
    private UploadedFile personalResume;
    private String namePersonalResume;

    @PostConstruct
    public void init() {
        try {
            loggedUser = Optional.ofNullable(userFacade.getAuthenticatedUser().get(0));
            if (loggedUser.isPresent()) {
                switch (loggedUser.get().getROLid().getName()) {
                    case ESTUDIANTE:
                        setProcessAvailable(findProcessAvailableStudent(processFacade.getProcessUser(loggedUser.get())));
                        break;
                    case SUPERVISOR_EPS:
                        setProcessAvailable(findProcessAvailableSupervisor(processFacade.getProcessBySupervisorEPS(loggedUser.get())));
                        break;
                    default:
                        MessageUtils.addErrorMessage("No cuenta con los permisos");
                        break;
                }
            } else {
                MessageUtils.addErrorMessage("Debe iniciar sesión");
            }
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    private List<Process> findProcessAvailableStudent(List<Process> process) {
        List<Process> result = new LinkedList<>();

        for (Process proces : process) {
            if ((proces.getApprovedCareerCoordinator() != null && proces.getApprovedCareerCoordinator())
                    && (proces.getApprovalEPSCommission() != null && proces.getApprovalEPSCommission())
                    && (proces.getAppointmentId() == null || (proces.getAppointmentId().getAdviserState() == CHANGE)
                    || (proces.getAppointmentId().getReviewerState() == CHANGE))) {
                result.add(proces);
            }
        }

        return result;
    }

    private List<Process> findProcessAvailableSupervisor(List<Process> process) {
        List<Process> result = new LinkedList<>();

        for (Process proces : process) {
            if ((proces.getApprovedCareerCoordinator() != null && proces.getApprovedCareerCoordinator())
                    && (proces.getApprovalEPSCommission() != null && proces.getApprovalEPSCommission())
                    && (proces.getAppointmentId() != null)
                    && (proces.getAppointmentId().getAdviserState() == REVIEW || proces.getAppointmentId().getReviewerState() == REVIEW)) {
                result.add(proces);
            }
        }

        return result.stream().sorted(
                (p1, p2) -> p1.getAppointmentId().getDateAction().compareTo(p2.getAppointmentId().getDateAction())
        ).collect(Collectors.toList());
    }

    /**
     * Change the state of the Reviewer or the Advisor to APPROVED, don't
     * persist the information until the user respond the student
     *
     * @param modalIdToClose
     */
    public void acceptUser(final String modalIdToClose) {
        if (actualUser != null) {
            if (isAdvisor) {
                processSelected.getAppointmentId().setAdviserState(APPROVED);
                PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
                MessageUtils.addSuccessMessage("Asesor aceptado");
            } else {
                processSelected.getAppointmentId().setReviewerState(APPROVED);
                PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
                MessageUtils.addSuccessMessage("Revisor aceptado");
            }
        }
    }

    public void denyUser(final String modalIdToClose) {
        if (actualUser != null) {
            if (isAdvisor) {
                processSelected.getAppointmentId().setAdviserState(CHANGE);
                processSelected.getAppointmentId().setUserAdviser(null);
                PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
                MessageUtils.addWarningMessage("Asesor eliminado");
            } else {
                processSelected.getAppointmentId().setReviewerState(CHANGE);
                processSelected.getAppointmentId().setUserReviewer(null);
                PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
                MessageUtils.addWarningMessage("Revisor eliminado");
            }
        }
    }

    /**
     * create a empty user with the role set, this is for the User
     *
     * @param process
     * @param advisor
     */
    public void createNewUser(Process process, boolean advisor) {
        processSelected = process;
        isAdvisor = advisor;
        if (processSelected.getAppointmentId() == null) {
            processSelected.setAppointmentId(new Appointment());
        }

        try {
            if (advisor && processSelected.getAppointmentId().getUserAdviser() == null) {

                actualUser = new User();
                actualUser.setrOLid(userFacade.getRolUser(new Rol(null, ASESOR)).get(0));
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

    /**
     * Save in memory the user added by the Student
     *
     * @param modalIdToClose
     */
    public void saveNewUser(final String modalIdToClose) {
        try {
            if (existsUser(actualUser)) {
                MessageUtils.addErrorMessage("El usuario ya existe con ese cargo");
            } else {
                if (isAdvisor) {
                    processSelected.getAppointmentId().setUserAdviser(actualUser);
                    processSelected.getAppointmentId().setAdviserState(REVIEW);
                } else {
                    processSelected.getAppointmentId().setUserReviewer(actualUser);
                    processSelected.getAppointmentId().setReviewerState(REVIEW);
                }
                PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
                MessageUtils.addSuccessMessage("Usuario agregado");
                clean();
            }
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    /**
     * If the user already exist, just is added to the Appointment. It's
     * student's method.
     *
     * @param modalIdToClose
     * @param user is the Reviewer or advisor
     */
    public void saveSelectedUser(final String modalIdToClose, User user) {

        if (user != null) {
            if (isAdvisor) {
                processSelected.getAppointmentId().setUserAdviser(user);
                processSelected.getAppointmentId().setAdviserState(REVIEW);
            } else {
                processSelected.getAppointmentId().setUserReviewer(user);
                processSelected.getAppointmentId().setReviewerState(REVIEW);
            }
            PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
            MessageUtils.addSuccessMessage("Usuario agregado");
        } else {
            MessageUtils.addErrorMessage("Debe elegir un usuario");
        }
    }

    /**
     * If the user already exist, just is added to the Appointment
     *
     * @param modalIdToClose
     * @param user
     */
    public void saveSelectedUserBySupervisor(final String modalIdToClose, User user) {

        if (user != null) {
            if (isAdvisor) {
                processSelected.getAppointmentId().setUserAdviser(user);
                processSelected.getAppointmentId().setAdviserState(ELECTION);
            } else {
                processSelected.getAppointmentId().setUserReviewer(user);
                processSelected.getAppointmentId().setReviewerState(ELECTION);
            }
            PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
            MessageUtils.addSuccessMessage("Usuario agregado");
        } else {
            MessageUtils.addErrorMessage("Debe elegir un usuario");
        }
    }

    /**
     * return the appointment to the Student
     *
     * @param modalIdToClose
     * @param process
     */
    public void returnAppointmentToStudent(final String modalIdToClose, Process process) {
        processSelected = process;
        try {
            processFacade.returnAppointmentToStudent(processSelected);
            init();
            PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
            MessageUtils.addSuccessMessage("Se ha notificado a los interesados de la desición");
        } catch (UserException | ValidationException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    /**
     * Send User's Review and advisor appointment to the Supervisor
     *
     * @param modalIdToClose
     * @param process
     */
    public void sendAppointmentToSupervisor(final String modalIdToClose, Process process) {

        try {
            processFacade.sendAppointmentToSupervisor(process);
            clean();
            init();
            PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
            MessageUtils.addSuccessMessage("Se ha enviado a su supervisor");
        } catch (UserException | ValidationException e) {
            MessageUtils.addErrorMessage(e.getMessage());
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
            personalResumeStream = new DefaultStreamedContent(new ByteArrayInputStream(actualUser.getPersonalResume()), "application/pdf", "Corriculum.pdf");
        } else if (!advisor && processSelected.getAppointmentId().getUserReviewer() != null) {
            actualUser = processSelected.getAppointmentId().getUserReviewer();
            personalResumeStream = new DefaultStreamedContent(new ByteArrayInputStream(actualUser.getPersonalResume()), "application/pdf", "Corriculum.pdf");
        } else {
            clean();
            MessageUtils.addErrorMessage("El usuario inexistente");
        }
    }

    /**
     * This method render the estate for view at list
     *
     * @param process
     * @param advisor
     * @return
     */
    public String showUserStatus(Process process, boolean advisor) {
        if (advisor && process != null) {
            return process.getAppointmentId().getAdviserState().stateToText();
        } else if (!advisor && process != null) {
            return process.getAppointmentId().getReviewerState().stateToText();
        }
        return "";
    }

    /**
     * This method render the state of the user on memory
     *
     * @return
     */
    public String showUserStatus() {
        if (isAdvisor && processSelected != null) {
            return processSelected.getAppointmentId().getAdviserState().stateToText();
        } else if (!isAdvisor && processSelected != null) {
            return processSelected.getAppointmentId().getReviewerState().stateToText();
        }
        return "";
    }

    private void loadAdvisors() {
        try {
            User searchU = new User(new Rol(null, ASESOR), null);
            searchU.setStatus(true);
            setElegible(userFacade.getUser(searchU));
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    private void loadAReviewer() {
        try {
            User searchU = new User(new Rol(null, REVISOR), null);
            searchU.setStatus(true);
            setElegible(userFacade.getUser(searchU));
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    /**
     * Indicate if the advisor was accepted or had been set
     *
     * @param process
     * @return
     */
    public Boolean advisorAlreadyExist(Process process) {
        return ((process.getAppointmentId() != null && process.getAppointmentId().getAdviserState() != null && process.getAppointmentId().getAdviserState() == APPROVED)
                || (process.getAppointmentId() != null && process.getAppointmentId().getUserAdviser() != null));
    }

    /**
     * Indicate if the reviewer was accepted or had been set
     *
     * @param process
     * @return
     */
    public Boolean reviewerAlreadyExist(Process process) {
        return ((process.getAppointmentId() != null && process.getAppointmentId().getReviewerState() != null && process.getAppointmentId().getReviewerState() == APPROVED)
                || (process.getAppointmentId() != null && process.getAppointmentId().getUserReviewer() != null));
    }

    /**
     * Verify if advisor's status is CHANGE
     *
     * @param process
     * @return
     */
    public Boolean advisorAvailableToChange(Process process) {
        return ((process.getAppointmentId() != null && process.getAppointmentId().getAdviserState() != null && process.getAppointmentId().getAdviserState() == CHANGE)
                || (process.getAppointmentId() != null && process.getAppointmentId().getUserAdviser() == null));
    }

    /**
     * Verify if reviewer's status is CHANGE
     *
     * @param process
     * @return
     */
    public Boolean reviewerAvailableToChange(Process process) {
        return ((process.getAppointmentId() != null && process.getAppointmentId().getReviewerState() != null && process.getAppointmentId().getReviewerState() == CHANGE)
                || (process.getAppointmentId() != null && process.getAppointmentId().getReviewerState() == null));
    }

    public Boolean pendingReview() {
        if (loggedUser.isPresent() && loggedUser.get().getROLid() != null && loggedUser.get().getROLid().getName().equals(SUPERVISOR_EPS)) {
            if (processSelected != null && processSelected.getAppointmentId() != null) {
                if (isAdvisor) {
                    return (processSelected.getAppointmentId().getAdviserState() == REVIEW);
                } else {
                    return (processSelected.getAppointmentId().getReviewerState() == REVIEW);
                }
            }
        }
        return false;

    }

    /**
     * Evaluate if the selected process
     *
     * @param process
     * @return
     */
    public Boolean appointmentAvailableToReturn(Process process) {
        try {
            Optional<Process> result = processFacade.findProcessById(process.getId());
            return (result.isPresent() && processAvailable.get(0).getId().equals(result.get().getId()));
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
        return false;
    }

    public String actualSelect() {
        if (isAdvisor) {
            return ASESOR;
        }
        return REVISOR;
    }

    public void handlePersonalResume(FileUploadEvent event) {
        personalResume = event.getFile();
        namePersonalResume = event.getFile().getFileName();
        actualUser.setPersonalResume(personalResume.getContents());
        personalResumeStream = new DefaultStreamedContent(new ByteArrayInputStream(actualUser.getPersonalResume()), "application/pdf", "Curriculum.pdf");
    }

    public void reloadPersonalResume() {
        personalResumeStream = new DefaultStreamedContent(new ByteArrayInputStream(actualUser.getPersonalResume()), "application/pdf", "Curriculum.pdf");
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

    public StreamedContent getPersonalResumeStream() {
        return personalResumeStream;
    }

    public void setPersonalResumeStream(StreamedContent personalResumeStream) {
        this.personalResumeStream = personalResumeStream;
    }

    public String getNamePersonalResume() {
        return namePersonalResume;
    }

    public void setNamePersonalResume(String namePersonalResume) {
        this.namePersonalResume = namePersonalResume;
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
        personalResume = null;
        personalResumeStream = null;
        namePersonalResume = null;
    }
}
