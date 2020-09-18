package gt.edu.usac.cunoc.ingenieria.eps.journal.service;

import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.journal.JournalLog;
import gt.edu.usac.cunoc.ingenieria.eps.journal.repository.JournalLogRepository;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.repository.ProcessRepository;
import gt.edu.usac.cunoc.ingenieria.eps.process.service.ProcessService;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class JournalLogService {

    @EJB
    private JournalLogRepository journalLogRepository;

    @EJB
    private ProcessService processService;

    @EJB
    private ProcessRepository processRepository;

    private EntityManager entityManager;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public JournalLogService() {
    }

    public void createJournalLog(JournalLog newJournalLog) throws LimitException, MandatoryException {
        verifyJournalLog(newJournalLog);
        entityManager.persist(newJournalLog);
    }

    public void verifyJournalLog(JournalLog newJournalLog) throws LimitException, MandatoryException {
        if (newJournalLog.getDateTime() == null) {
            throw new MandatoryException("Falta Fecha del Registro");
        } else if (newJournalLog.getActivity() == null || newJournalLog.getActivity().isEmpty()) {
            throw new MandatoryException("Falta Actividad del Registro");
        } else if (newJournalLog.getDescription() == null || newJournalLog.getDescription().isEmpty()) {
            throw new MandatoryException("Falta Descripción del Regisro");
        }
        if (!journalLogRepository.verifyDateJournal(newJournalLog)) {
            throw new LimitException("Ya existe un registro con esta fecha");
        }
    }

    public void enableJournal(Process process) throws ValidationException {
        try {
            Optional<Process> currentProcess = processRepository.findProcessById(process.getId());
            if (currentProcess.isPresent()) {
                if (processRepository.isAssignedAdvisorReviewer(currentProcess.get())) {
                    if (currentProcess.get().getAppointmentId().getCompanySupervisor() != null 
                            && !currentProcess.get().getAppointmentId().getCompanySupervisor().getRemovable()
                            && currentProcess.get().getAppointmentId().getCompanySupervisor().getStatus()) {
                        currentProcess.get().setApprovedEPSDevelopment(Boolean.TRUE);
                        currentProcess.get().setDateApproveddEpsDevelopment(LocalDate.now());
                        processService.updateProcess(currentProcess.get());
                    } else {
                        throw new ValidationException("Falta Verificación del Supervisor de la Empresa");
                    }
                } else {
                    throw new ValidationException("Falta Verificación del Asesor y Revisor");
                }
            } else {
                throw new ValidationException("Error al Buscar el Proceso");
            }
        } catch (UserException ex) {
            throw new ValidationException(ex.getMessage());
        }
    }
}
