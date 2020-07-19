package gt.edu.usac.cunoc.ingenieria.eps.process.service;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.configuration.mail.MailService;
import gt.edu.usac.cunoc.ingenieria.eps.process.Appointment;
import static gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState.*;
import gt.edu.usac.cunoc.ingenieria.eps.process.repository.ProcessRepository;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.UserRepository;
import gt.edu.usac.cunoc.ingenieria.eps.user.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ProcessService {
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    
    @EJB
    private ProcessRepository processRepository;
    
    @EJB
    private UserRepository userRepository;
    
    @EJB
    private UserService userService;
    
    @EJB
    private AppointmentService appointmentService;
    
    @EJB
    private MailService mailService;
    
    public Process createProcess(Process process) {
        UserCareer userCareer = process.getUserCareer();
        userCareer.setProcess(process);
        if (userCareer.getId() != null) {
            entityManager.merge(userCareer);
        } else {
            entityManager.persist(userCareer);
        }
        return process;
    }
    
    public Process updateProcess(Process process) {
        entityManager.merge(process);
        return process;
    }

    /**
     * This method Assign a SUPERVISOR_EPS to a Process doing load balancing
     *
     * @param career Career to which the student owning the process belongs
     * @param process Process to Assign a SUPERVISOR_EPS
     * @throws ValidationException If not exist a SUPERVISOR_EPS for the Career
     */
    public void assignEPSSUpervisorToProcess(Career career, Process process) throws ValidationException {
        List<User> supervisorEPS = userRepository.getSupervisorEPSbyCareer(career);
        User supervisorEPSToAssign;
        if (supervisorEPS.size() > 1) {
            User currentUser;
            User nextUser;
            for (int i = 0; i < supervisorEPS.size() - 1; i++) {
                currentUser = supervisorEPS.get(i);
                nextUser = supervisorEPS.get(i + 1);
                long countProcessesCurrentUser = userRepository.getNumberProcessesBySupervisorEPS(currentUser);
                long countProcessesNextUser = userRepository.getNumberProcessesBySupervisorEPS(nextUser);
                if (countProcessesCurrentUser > countProcessesNextUser) {
                    supervisorEPSToAssign = nextUser;
                } else {
                    supervisorEPSToAssign = currentUser;
                }
                process.setSupervisor_EPS(supervisorEPSToAssign);
            }
        } else if (supervisorEPS.size() == 1) {
            supervisorEPSToAssign = supervisorEPS.get(0);
            process.setSupervisor_EPS(supervisorEPSToAssign);
        } else {
            throw new ValidationException("No existe Supervisor de EPS para su Carrera");
        }
        updateProcess(process);
    }
    
    public Process sendAppointmentToSupervisor(Process process) throws UserException, ValidationException {
        Optional<User> actualUser = Optional.ofNullable(userService.getAuthenticatedUser().get(0));
        Optional<Process> resultProcess;
        Appointment appointment;
        
        if (process != null && actualUser.isPresent() && process.getAppointmentId() != null
                && process.getUserCareer().getUSERuserId().getUserId().equals(actualUser.get().getUserId())) {
            
            resultProcess = processRepository.findProcessById(process.getId());
            
            if (resultProcess.isPresent()) {
                if (resultProcess.get().getAppointmentId() == null) {
                    appointment = new Appointment();
                } else {
                    appointment = resultProcess.get().getAppointmentId();
                }
                
                if (appointment != null && process.getAppointmentId().getUserAdviser() != null
                        && process.getAppointmentId().getUserReviewer() != null) {
                    
                    if (process.getAppointmentId().getAdviserState() != null && process.getAppointmentId().getAdviserState() == REVIEW) {
                        
                        if (!existsUser(process.getAppointmentId().getUserAdviser())) {
                            appointment.setUserAdviser(userService.createTempUser(process.getAppointmentId().getUserAdviser()));
                        } else {
                            appointment.setUserAdviser(process.getAppointmentId().getUserAdviser());
                        }
                        appointment.setAdviserState(REVIEW);
                        
                    }
                    
                    if (process.getAppointmentId().getReviewerState() != null && process.getAppointmentId().getReviewerState() == REVIEW) {
                        
                        if (!existsUser(process.getAppointmentId().getUserReviewer())) {
                            appointment.setUserReviewer(userService.createTempUser(process.getAppointmentId().getUserReviewer()));
                        } else {
                            appointment.setUserReviewer(process.getAppointmentId().getUserReviewer());
                        }
                        appointment.setReviewerState(REVIEW);
                    }
                    
                    if (appointment.getReviewerState() != null && appointment.getAdviserState() != null
                            && appointment.getAdviserState() == REVIEW || appointment.getAdviserState() == APPROVED
                            || appointment.getAdviserState() == ELECTION
                            && appointment.getReviewerState() == REVIEW || appointment.getReviewerState() == APPROVED
                            || appointment.getReviewerState() == ELECTION
                            && appointment.getUserAdviser() != null && appointment.getUserReviewer() != null) {
                        
                        appointment.setDateAction(LocalDateTime.now());
                        
                        if (resultProcess.get().getAppointmentId() == null) {
                            appointment = appointmentService.createAppointment(appointment);
                            resultProcess.get().setAppointmentId(appointment);
                            resultProcess = Optional.of(updateProcess(resultProcess.get()));
                        } else {
                            resultProcess = Optional.of(updateProcess(resultProcess.get()));
                        }
                        mailService.emailNotifySupervisor(
                                resultProcess.get().getSupervisor_EPS(),
                                resultProcess.get().getAppointmentId(),
                                resultProcess.get().getProject().getTitle(),
                                resultProcess.get().getUserCareer().getUSERuserId()
                        );
                        return resultProcess.get();
                    } else {
                        throw new ValidationException("Datos incorrectos");
                    }
                } else {
                    if (process.getAppointmentId().getUserAdviser() == null) {
                        throw new UserException("Debe indicar el Asesor");
                    } else {
                        throw new UserException("Debe indicar el Revisor");
                    }
                }
            } else {
                throw new ValidationException("El Proyecto no existe");
            }
        } else {
            throw new ValidationException("Debe elegir un proceso");
        }
    }
    
    public Process returnAppointmentToStudente(Process process) throws ValidationException, UserException {
        Optional<User> actualUser = Optional.ofNullable(userService.getAuthenticatedUser().get(0));
        Optional<Process> resultProcess;
        
        if (process != null && process.getAppointmentId() != null && actualUser.isPresent()
                && process.getSupervisor_EPS() != null && process.getSupervisor_EPS().getUserId().equals(actualUser.get().getUserId())) {
            
            resultProcess = processRepository.findProcessById(process.getId());
            
            if (resultProcess.isPresent() && resultProcess.get().getAppointmentId() != null) {

                //Advisor
                if (resultProcess.get().getAppointmentId().getAdviserState() == REVIEW && process.getAppointmentId().getAdviserState() != REVIEW) {
                    if (process.getAppointmentId().getAdviserState() == APPROVED
                            && resultProcess.get().getAppointmentId().getUserAdviser().getUserId().equals(process.getAppointmentId().getUserAdviser().getUserId())) {
                        
                        if (resultProcess.get().getAppointmentId().getUserAdviser().getRemovable()) {
                            resultProcess.get().getAppointmentId().setUserAdviser(
                                    userService.aproveUser(
                                            resultProcess.get().getAppointmentId().getUserAdviser(),
                                            resultProcess.get().getProject().getTitle(),
                                            resultProcess.get().getUserCareer().getUSERuserId().getFirstName().concat(" ").concat(process.getUserCareer().getUSERuserId().getLastName())
                                    )
                            );
                        }
                        resultProcess.get().getAppointmentId().setAdviserState(APPROVED);
                        
                    } else if (process.getAppointmentId().getAdviserState() == CHANGE
                            && resultProcess.get().getAppointmentId().getUserAdviser() != null
                            && process.getAppointmentId().getUserAdviser() == null) {
                        
                        if (resultProcess.get().getAppointmentId().getUserAdviser().getRemovable()) {
                            userService.deleteUser(resultProcess.get().getAppointmentId().getUserAdviser());
                        }
                        resultProcess.get().getAppointmentId().setUserAdviser(null);
                        resultProcess.get().getAppointmentId().setAdviserState(CHANGE);
                        
                    } else if (process.getAppointmentId().getAdviserState() == ELECTION
                            && !resultProcess.get().getAppointmentId().getUserAdviser().getUserId().equals(process.getAppointmentId().getUserAdviser().getUserId())) {
                        if (resultProcess.get().getAppointmentId().getUserAdviser().getRemovable()) {
                            userService.deleteUser(resultProcess.get().getAppointmentId().getUserAdviser());
                        }
                        resultProcess.get().getAppointmentId().setUserAdviser(process.getAppointmentId().getUserAdviser());
                        resultProcess.get().getAppointmentId().setAdviserState(ELECTION);
                        
                        mailService.emailNotifyAdvisorOrReviewer(
                                resultProcess.get().getAppointmentId().getUserAdviser(),
                                resultProcess.get().getProject().getTitle(),
                                resultProcess.get().getUserCareer().getUSERuserId().getFirstName().concat(" ").concat(process.getUserCareer().getUSERuserId().getLastName())
                        );
                    }
                    
                }

                //Reviewer
                if (resultProcess.get().getAppointmentId().getReviewerState() == REVIEW && process.getAppointmentId().getReviewerState() != REVIEW) {
                    if (process.getAppointmentId().getReviewerState() == APPROVED
                            && resultProcess.get().getAppointmentId().getUserReviewer().getUserId().equals(process.getAppointmentId().getUserReviewer().getUserId())) {
                        
                        if (resultProcess.get().getAppointmentId().getUserReviewer().getRemovable()) {
                            resultProcess.get().getAppointmentId().setUserReviewer(
                                    userService.aproveUser(
                                            resultProcess.get().getAppointmentId().getUserReviewer(),
                                            resultProcess.get().getProject().getTitle(),
                                            resultProcess.get().getUserCareer().getUSERuserId().getFirstName().concat(" ").concat(process.getUserCareer().getUSERuserId().getLastName())
                                    )
                            );
                        }
                        resultProcess.get().getAppointmentId().setReviewerState(APPROVED);
                        
                    } else if (process.getAppointmentId().getReviewerState() == CHANGE
                            && resultProcess.get().getAppointmentId().getUserReviewer() != null
                            && process.getAppointmentId().getUserReviewer() == null) {
                        
                        if (resultProcess.get().getAppointmentId().getUserReviewer().getRemovable()) {
                            userService.deleteUser(resultProcess.get().getAppointmentId().getUserReviewer());
                        }
                        resultProcess.get().getAppointmentId().setUserReviewer(null);
                        resultProcess.get().getAppointmentId().setReviewerState(CHANGE);
                        
                    } else if (process.getAppointmentId().getReviewerState() == ELECTION
                            && !resultProcess.get().getAppointmentId().getUserReviewer().getUserId().equals(process.getAppointmentId().getUserReviewer().getUserId())) {
                        if (resultProcess.get().getAppointmentId().getUserReviewer().getRemovable()) {
                            userService.deleteUser(resultProcess.get().getAppointmentId().getUserReviewer());
                        }
                        resultProcess.get().getAppointmentId().setUserReviewer(process.getAppointmentId().getUserReviewer());
                        resultProcess.get().getAppointmentId().setReviewerState(ELECTION);
                        
                        mailService.emailNotifyAdvisorOrReviewer(
                                resultProcess.get().getAppointmentId().getUserReviewer(),
                                resultProcess.get().getProject().getTitle(),
                                resultProcess.get().getUserCareer().getUSERuserId().getFirstName().concat(" ").concat(process.getUserCareer().getUSERuserId().getLastName())
                        );
                    }
                    
                }
                
                if ((resultProcess.get().getAppointmentId().getAdviserState() == APPROVED || resultProcess.get().getAppointmentId().getAdviserState() == CHANGE
                        || resultProcess.get().getAppointmentId().getAdviserState() == ELECTION) && (resultProcess.get().getAppointmentId().getReviewerState() == APPROVED
                        || resultProcess.get().getAppointmentId().getReviewerState() == CHANGE || resultProcess.get().getAppointmentId().getReviewerState() == ELECTION)) {
                    
                    if ((resultProcess.get().getAppointmentId().getAdviserState() == APPROVED || resultProcess.get().getAppointmentId().getAdviserState() == ELECTION)
                            && (resultProcess.get().getAppointmentId().getReviewerState() == APPROVED || resultProcess.get().getAppointmentId().getReviewerState() == ELECTION)) {
                    }
                    
                    resultProcess = Optional.ofNullable(updateProcess(resultProcess.get()));
                    mailService.emailNotifyStudent(
                            resultProcess.get().getAppointmentId().getUserAdviser(),
                            resultProcess.get().getAppointmentId().getAdviserState(),
                            resultProcess.get().getAppointmentId().getUserReviewer(),
                            resultProcess.get().getAppointmentId().getReviewerState(),
                            resultProcess.get().getSupervisor_EPS(),
                            resultProcess.get().getProject().getTitle(),
                            resultProcess.get().getUserCareer().getUSERuserId()
                    );
                    return resultProcess.get();
                } else {
                    throw new ValidationException("Debe dar una respuesta del Asesor y Revisor para enviarlo");
                }
            } else {
                throw new ValidationException("Proceso Inexistente");
            }
        } else {
            if (process == null || process.getAppointmentId() == null) {
                throw new ValidationException("Debe elegir un proceso");
            } else if (process.getSupervisor_EPS() == null || process.getSupervisor_EPS().getUserId() == null) {
                throw new ValidationException("Aun no cuenta con Supervisor");
            } else {
                throw new ValidationException("No cuenta con los permisos para esta acción");
            }
        }
    }
    
    private boolean existsUser(User user) throws UserException {
        User search = new User();
        search.setrOLid(user.getROLid());
        search.setDpi(user.getDpi());
        
        return (!userRepository.getUser(search).isEmpty());
    }
    
}
