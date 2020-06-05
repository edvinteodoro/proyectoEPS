package gt.edu.usac.cunoc.ingenieria.eps.tail.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCoordinator;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import static gt.edu.usac.cunoc.ingenieria.eps.tail.repository.TailCoordinatorRepository.GET_PROCESS_COORDIANTOR;
import static gt.edu.usac.cunoc.ingenieria.eps.tail.repository.TailCoordinatorRepository.ID_PARAMETER_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class TailCoordinatorService {
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    
    public static final String GET_TAIL_COORDIANTOR = "DELETE FROM TailCoordinator t WHERE t.process.id=:process";
    public static final String ID_PARAMETER_NAME = "process";
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public TailCoordinator createTailCoordinator(TailCoordinator tailCoordinator){
        entityManager.persist(tailCoordinator);
        return tailCoordinator;
    }
    
    public void deleteTailCoordiantor(Process process){
        Query query = entityManager.createQuery(GET_TAIL_COORDIANTOR);
        query.setParameter(ID_PARAMETER_NAME, process.getId());
        query.executeUpdate();
    }
}
