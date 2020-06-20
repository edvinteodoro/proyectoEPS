
package gt.edu.usac.cunoc.ingenieria.eps.tail.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCommitteeEPS;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
@LocalBean
public class TailCommitteeEPSRepository {
    
    private EntityManager entityManager;

    public TailCommitteeEPSRepository() {
    }
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public List<Process> getTailCommitteeEPS(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TailCommitteeEPS> criteriaQuery = criteriaBuilder.createQuery(TailCommitteeEPS.class);
        Root<TailCommitteeEPS> root = criteriaQuery.from(TailCommitteeEPS.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
        TypedQuery<TailCommitteeEPS> query = entityManager.createQuery(criteriaQuery);
        List<TailCommitteeEPS> tailCommitteeEPS = query.getResultList();
        List<Process> processes =  new ArrayList<>();
        for (TailCommitteeEPS tailCommitteeEPS1 : tailCommitteeEPS) {
            processes.add(tailCommitteeEPS1.getProcess());
        }
        return processes;
    }
}
