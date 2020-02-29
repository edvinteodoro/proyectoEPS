package gt.edu.usac.cunoc.ingenieria.eps.user.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
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
public class CareerRepository {
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public List<Career> getAllCareers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Career> criteriaQuery = criteriaBuilder.createQuery(Career.class);
        Root<Career> rootEntry = criteriaQuery.from(Career.class);
        CriteriaQuery<Career> all = criteriaQuery.select(rootEntry);
        TypedQuery<Career> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }
    public List<Career> getCareer(Career career){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Career> criteriaQuery = criteriaBuilder.createQuery(Career.class);
        Root<Career> position = criteriaQuery.from(Career.class);
        List<Predicate> predicates = new ArrayList<>();
        if (career.getCodigo() != null) {
            predicates.add(criteriaBuilder.equal(position.get("codigo"), career.getCodigo()));
        }
        if (career.getName() != null) {
            predicates.add(criteriaBuilder.like(position.get("name"), "%" + career.getName()+ "%"));
        }
        criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<Career> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
