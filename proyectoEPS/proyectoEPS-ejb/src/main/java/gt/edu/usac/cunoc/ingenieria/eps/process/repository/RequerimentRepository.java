package gt.edu.usac.cunoc.ingenieria.eps.process.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.config.Constans.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
@LocalBean
public class RequerimentRepository {
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public List<Requeriment> getRequeriment(Requeriment requeriment) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Requeriment> criteriaQuery = criteriaBuilder.createQuery(Requeriment.class);
        Root<Requeriment> requerimentR = criteriaQuery.from(Requeriment.class);
        List<Predicate> predicates = new ArrayList<>();
        if (requeriment.getId() != null) {
            predicates.add(criteriaBuilder.equal(requerimentR.get("id"), requeriment.getId()));
        }
        criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<Requeriment> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
