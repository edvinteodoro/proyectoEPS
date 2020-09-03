package gt.edu.usac.cunoc.ingenieria.eps.journal.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.journal.Commentary;
import gt.edu.usac.cunoc.ingenieria.eps.journal.JournalLog;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class JournalLogRepository {

    private final String GET_JOURNAL_LOG_BY_PROCESS = "SELECT j FROM JournalLog j WHERE j.process.id = :idProcess ORDER BY j.dateTime";
    private final String GET_JOURNAL_LOG_BY_ID = "SELECT j FROM JournalLog j WHERE j.id = :idJournal";
    private final String GET_JOURNAL_LOG_BY_DATE = "SELECT j FROM JournalLog j WHERE j.dateTime = :dateTime AND j.process.id = :idProcess";
    private final String GET_COMMENTARIES_BY_JOURNAL_ID = "SELECT c FROM Commentary c WHERE c.journal_Log.id = :idJournal";
    private EntityManager entityManager;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * This method get a List o JournalLog that belong to a Process
     * @param processId Id from the Process owner
     * @return List of JounalLogs
     */
    public List<JournalLog> getJournal(Integer processId) {
        Query query = entityManager.createQuery(GET_JOURNAL_LOG_BY_PROCESS, JournalLog.class);
        query.setParameter("idProcess", processId);
        return query.getResultList();
    }
    
    /**
     * This method get a boolan result that depend if exist a JournalLog with the same Date that the provided
     * @param newJournalLog
     * @return depend if exist a jornalLog with the date provided
     */
    public boolean verifyDateJournal(JournalLog newJournalLog){
        Query query = entityManager.createQuery(GET_JOURNAL_LOG_BY_DATE, JournalLog.class);
        query.setParameter("dateTime", newJournalLog.getDateTime());
        query.setParameter("idProcess", newJournalLog.getProcess().getId());
        List<JournalLog> journals = query.getResultList();
        return journals.isEmpty();
    }
    
    public List<Commentary> getCommentariesJournal(Integer journalId){
        Query query = entityManager.createQuery(GET_COMMENTARIES_BY_JOURNAL_ID, Commentary.class);
        query.setParameter("idJournal", journalId);
        return query.getResultList();
    }
    
    /*Other function for rest services*/
    
    public JournalLog getJournalById(Integer id) {
        Query query = entityManager.createQuery(GET_JOURNAL_LOG_BY_ID, JournalLog.class);
        query.setParameter("idJournal", id);
        return (JournalLog) query.getSingleResult();
    }
}
