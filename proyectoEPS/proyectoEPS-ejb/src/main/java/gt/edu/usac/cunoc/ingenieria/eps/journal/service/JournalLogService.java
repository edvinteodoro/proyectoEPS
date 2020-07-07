package gt.edu.usac.cunoc.ingenieria.eps.journal.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.journal.JournalLog;
import gt.edu.usac.cunoc.ingenieria.eps.journal.repository.JournalLogRepository;
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
        if (newJournalLog.getDateTime() == null){
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
}
