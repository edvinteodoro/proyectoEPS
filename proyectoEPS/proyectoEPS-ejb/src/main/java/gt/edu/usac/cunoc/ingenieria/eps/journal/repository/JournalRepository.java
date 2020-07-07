package gt.edu.usac.cunoc.ingenieria.eps.journal.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.journal.JournalLog;
import java.util.Optional;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class JournalRepository {

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    
    public Optional<JournalLog> getJournals(){
     return null;
    }
}
