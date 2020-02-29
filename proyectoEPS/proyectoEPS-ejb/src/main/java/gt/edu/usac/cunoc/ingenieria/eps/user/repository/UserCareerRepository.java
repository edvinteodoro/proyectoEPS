package gt.edu.usac.cunoc.ingenieria.eps.user.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import java.time.LocalDate;
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
public class UserCareerRepository {
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public List<UserCareer> getUser(UserCareer userCareer) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserCareer> criteriaQuery = criteriaBuilder.createQuery(UserCareer.class);
        Root<UserCareer> userR = criteriaQuery.from(UserCareer.class);
        List<Predicate> predicates = new ArrayList<>();
        
        if (userCareer.getCAREERcodigo() != null) {
            predicates.add(criteriaBuilder.equal(userR.get("cAREERcodigo"), userCareer.getCAREERcodigo().getCodigo()));
        }
        
        if (userCareer.getUSERuserId() != null) {
            predicates.add(criteriaBuilder.equal(userR.get("uSERuserId"), userCareer.getUSERuserId().getDpi()));
        }
        
        criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<UserCareer> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
