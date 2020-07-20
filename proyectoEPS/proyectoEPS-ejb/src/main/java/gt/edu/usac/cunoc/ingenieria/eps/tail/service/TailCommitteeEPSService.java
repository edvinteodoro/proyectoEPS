
package gt.edu.usac.cunoc.ingenieria.eps.tail.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.service.ProcessService;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCommitteeEPS;
import java.time.LocalDate;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class TailCommitteeEPSService {
    
    private EntityManager entityManager;
    
    @EJB
    private ProcessService processService;
    
    public static final String DELETE_TAIL_COMMITTEE_EPS = "DELETE FROM TailCommitteeEPS t WHERE t.process.id=:process";
    public static final String ID_PARAMETER_NAME = "process";
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public TailCommitteeEPSService() {
    }
    
    public TailCommitteeEPS createTailCommitteeEPS(Process process){
        TailCommitteeEPS newElementoTail = new TailCommitteeEPS(LocalDate.now(), process);
        entityManager.persist(newElementoTail);
        processService.updateProcess(process);
        return newElementoTail;
    }
    
    public void deleteTailCommitteeEPS(Process process){
        Query query = entityManager.createQuery(DELETE_TAIL_COMMITTEE_EPS);
        query.setParameter(ID_PARAMETER_NAME, process.getId());
        query.executeUpdate();
    }
}
