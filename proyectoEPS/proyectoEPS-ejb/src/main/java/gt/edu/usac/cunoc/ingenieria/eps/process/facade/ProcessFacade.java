package gt.edu.usac.cunoc.ingenieria.eps.process.facade;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.configuration.mail.MailService;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.repository.ProcessRepository;
import gt.edu.usac.cunoc.ingenieria.eps.process.repository.RequerimentRepository;
import gt.edu.usac.cunoc.ingenieria.eps.process.service.ProcessService;
import gt.edu.usac.cunoc.ingenieria.eps.process.service.RequerimentService;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import static gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess.ACTIVO;
import static gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess.RECHAZADO;
import static gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess.REVISION;
import static gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState.*;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCoordinator;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCommitteeEPSFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacade;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class ProcessFacade implements ProcessFacadeLocal {
    
    String DEBE_BORRARSE = "Mensaje q se debe borrar";

    @EJB
    RequerimentService requerimentService;

    @EJB
    RequerimentRepository requerimentRepository;

    @EJB
    ProcessService processService;

    @EJB
    ProcessRepository processRepository;

    @EJB
    private ProjectFacadeLocal projectFacade;

    @EJB
    TailCommitteeEPSFacadeLocal tailCommitteeEPSFacadeLocal;

    @EJB
    UserFacade userFacade;

    @EJB
    MailService mailService;

    @Override
    public List<Requeriment> getRequeriment(Requeriment requeriment) {
        return requerimentRepository.getRequeriment(requeriment);
    }

    @Override
    public Requeriment createRequeriment(Requeriment requeriment) {
        return requerimentService.createRequeriment(requeriment);
    }

    @Override
    public Requeriment updaterequeriment(Requeriment requeriment) {
        return requerimentService.updateRequeriment(requeriment);
    }

    @Override
    public Process createProcess(Process process) {
        return processService.createProcess(process);
    }

    @Override
    public Optional<Process> findProcessById(Integer id) throws UserException {
        return processRepository.findProcessById(id);
    }

    @Override
    public List<Process> getProcess(Process process) {
        return processRepository.getProcess(process);
    }

    @Override
    public List<Process> getProcessUser(User user) {
        return processRepository.getProcessUser(user);
    }

    @Override
    public Process updateProcess(Process process) {
        return processService.updateProcess(process);
    }

    @Override
    public boolean rejectProcess(TailCoordinator tailCoordinator, String title, String msg) {
        return processRepository.SendEmailStudent(tailCoordinator, title, msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Process> ProcessAvailableToEPSCommittee(Integer id) throws UserException {
        Optional<Process> result = findProcessById(id);

        if (result.isPresent()) {
            if (result.get().getState() == REVISION) {
                if (result.get().getApprovedCareerCoordinator() && (result.get().getApprovalEPSCommission() == null || !result.get().getApprovalEPSCommission())) {
                    return result;
                }
            } else {
                throw new UserException("No apto para revisión");
            }
        } else {
            throw new UserException("El proceso no existe");
        }

        return Optional.ofNullable(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Process> returnEPSCommitteeRevisionToStudent(Integer id) throws UserException {
        List<Process> processes = tailCommitteeEPSFacadeLocal.getTailCommitteeEPS();
        Optional<Process> result = ProcessAvailableToEPSCommittee(id);

        if (!processes.isEmpty()) {
            if (result.isPresent()) {
                if (processes.get(0).getId() == result.get().getId()) {
                    result.get().setState(ACTIVO);
                    updateProcess(result.get());
                    tailCommitteeEPSFacadeLocal.deleteTailCommitteeEPS(result.get());

                    //Falta enviar notificacion por correo
                    return result;
                }
            } else {
                throw new UserException("Proceso Desconocido");
            }
        } else {
            throw new UserException("No existen procesos para el comité");
        }

        return Optional.ofNullable(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Process> aproveedByEPSCommittee(Integer id) throws UserException {
        List<Process> processes = tailCommitteeEPSFacadeLocal.getTailCommitteeEPS();
        Optional<Process> result = ProcessAvailableToEPSCommittee(id);
        if (!processes.isEmpty()) {
            if (result.isPresent()) {
                if (processes.get(0).getId() == result.get().getId()) {
                    result.get().setState(REVISION);
                    result.get().setApprovalEPSCommission(true);
                    updateProcess(result.get());
                    tailCommitteeEPSFacadeLocal.deleteTailCommitteeEPS(result.get());
                    //Metodo para enviar agregar a siguiente etapa

                    //Falta enviar notificacion por correo
                    return result;
                }
            } else {
                throw new UserException("Proceso Desconocido");
            }
        } else {
            throw new UserException("No existen procesos para el comité");
        }

        return Optional.ofNullable(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Process> EPSCommitteeRejectProyect(Integer id, User user, String message) throws UserException {
        List<Process> processes = tailCommitteeEPSFacadeLocal.getTailCommitteeEPS();
        Optional<Process> result = ProcessAvailableToEPSCommittee(id);

        if (!processes.isEmpty()) {
            if (result.isPresent()) {
                if (processes.get(0).getId() == result.get().getId()) {
                    result.get().setState(RECHAZADO);
                    result.get().setApprovalEPSCommission(false);
                    updateProcess(result.get());
                    tailCommitteeEPSFacadeLocal.deleteTailCommitteeEPS(result.get());
                    projectFacade.createCorrection(new Correction(LocalDate.now(), user, TypeCorrection.REJECTED, result.get().getProject(), null, message.getBytes()));

                    //Falta enviar notificacion por correo
                    return result;
                }
            } else {
                throw new UserException("Proceso Desconocido");
            }
        } else {
            throw new UserException("No existen procesos para el comité");
        }

        return Optional.ofNullable(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Process sendAppointmentToSupervisor(Process process) throws UserException {
        Optional<User> actualUser = Optional.ofNullable(userFacade.getAuthenticatedUser().get(0));

        if (process != null && process.getAppointmentId() != null) {

            if (actualUser.isPresent() && process.getUserCareer().getUSERuserId().getUserId().equals(actualUser.get().getUserId())) {

                if (process.getAppointmentId().getUserAdviser() != null && process.getAppointmentId().getUserReviewer() != null) {

                    if (process.getAppointmentId().getAdviserState() == null
                            || process.getAppointmentId().getAdviserState() != APPROVED
                            && !existsUser(process.getAppointmentId().getUserAdviser())) {
                        process.getAppointmentId().setUserAdviser(userFacade.createTempUser(process.getAppointmentId().getUserAdviser()));
                    }
                    if (process.getAppointmentId().getReviewerState() == null
                            || process.getAppointmentId().getReviewerState() != APPROVED
                            && !existsUser(process.getAppointmentId().getUserReviewer())) {
                        process.getAppointmentId().setUserReviewer(userFacade.createTempUser(process.getAppointmentId().getUserReviewer()));
                    }

                    process.getAppointmentId().setDateAction(LocalDateTime.now());
                    updateProcess(process);
                    return process;
                } else {
                    if (process.getAppointmentId().getUserAdviser() == null) {
                        throw new UserException("Debe indicar el Asesor");
                    } else {
                        throw new UserException("Debe indicar el Revisor");
                    }
                }
            } else {
                throw new UserException("El usuario no es dueño del Proyecto");
            }
        } else {
            throw new UserException("Debe elegir un proceso");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Process returnAppointmentToStudent(Process process) throws UserException, ValidationException {
        return processService.returnAppointmentToStudente(process);
    }

    private boolean existsUser(User user) throws UserException {
        User search = new User();
        search.setrOLid(user.getROLid());
        search.setDpi(user.getDpi());

        return (!userFacade.getUser(search).isEmpty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignEPSSUpervisorToProcess(Career career, Process process) throws ValidationException {
        processService.assignEPSSUpervisorToProcess(career, process);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Process> getProcessBySupervisorEPS(User supervisorEPS) {
        return processRepository.getProcessBySupervisorEPS(supervisorEPS);
    }

}
