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

    private final String GET_JOURNAL_LOG_BY_MONTHLY_REPORT = "SELECT j FROM JournalLog j WHERE j.monthlyReport.id = :idMonthlyReport ORDER BY j.dateTime";
    private final String GET_JOURNAL_LOG_BY_DATE = "SELECT j FROM JournalLog j WHERE j.dateTime = :dateTime AND j.monthlyReport.id = :idMonthlyReport";
    private EntityManager entityManager;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * This method get a List o JournalLog that belong to a Process
     * @param monthlyReportId
     * @return List of JounalLogs
     */
    public List<JournalLog> getJournal(Integer monthlyReportId) {
        Query query = entityManager.createQuery(GET_JOURNAL_LOG_BY_MONTHLY_REPORT, JournalLog.class);
        query.setParameter("idMonthlyReport", monthlyReportId);
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
        query.setParameter("idMonthlyReport", newJournalLog.getMonthlyReport().getId());
        List<JournalLog> journals = query.getResultList();
        return journals.isEmpty();
    }
    
}
