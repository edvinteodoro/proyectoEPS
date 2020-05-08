package gt.edu.usac.cunoc.ingenieria.eps.tail.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCoordinator;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class TailCoordinatorService {
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public TailCoordinator createTailCoordinator(TailCoordinator tailCoordinator){
        entityManager.persist(tailCoordinator);
        return tailCoordinator;
    }
}
