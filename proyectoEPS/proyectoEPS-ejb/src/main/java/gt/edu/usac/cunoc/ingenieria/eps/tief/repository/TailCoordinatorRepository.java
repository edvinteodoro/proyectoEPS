package gt.edu.usac.cunoc.ingenieria.eps.tief.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class TailCoordinatorRepository {

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public static final String GET_PROCESS_COORDIANTOR = "SELECT t.process FROM TailCoordinator t WHERE t.userCareer.uSERuserId.userId=:userId ORDER BY t.id DESC";
    public static final String ID_PARAMETER_NAME = "userId";
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Process> getProcessByCoordinator(User user) {
        Query query = entityManager.createQuery(GET_PROCESS_COORDIANTOR);
        query.setParameter(ID_PARAMETER_NAME, user.getUserId());
        return query.getResultList();    
    }

}
