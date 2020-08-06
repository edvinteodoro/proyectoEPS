
package gt.edu.usac.cunoc.ingenieria.eps.journal.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.journal.Commentary;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class CommentaryRepository {
    
    private final String GET_COMMENTARIES_BY_JOURNAL_ID = "SELECT c FROM Commentary c WHERE c.journal_Log.id = :idJournal ORDER BY c.date ASC";
    
    private EntityManager entityManager;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CommentaryRepository() {
    }
    
    public List<Commentary> getCommentariesJournal(Integer journalId){
        Query query = entityManager.createQuery(GET_COMMENTARIES_BY_JOURNAL_ID, Commentary.class);
        query.setParameter("idJournal", journalId);
        return query.getResultList();
    }
}
