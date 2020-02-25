package gt.edu.usac.cunoc.ingenieria.eps.user.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
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
public class RolRepository {
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public List<Rol> getAllRolUser() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Rol> criteriaQuery = criteriaBuilder.createQuery(Rol.class);
        Root<Rol> rootEntry = criteriaQuery.from(Rol.class);
        CriteriaQuery<Rol> all = criteriaQuery.select(rootEntry);
        TypedQuery<Rol> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }
    public List<Rol> getRoll(Rol rol){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Rol> criteriaQuery = criteriaBuilder.createQuery(Rol.class);
        Root<Rol> position = criteriaQuery.from(Rol.class);
        List<Predicate> predicates = new ArrayList<>();
        if (rol.getId() != null) {
            predicates.add(criteriaBuilder.equal(position.get("id"), rol.getId()));
        }
        if (rol.getName() != null) {
            predicates.add(criteriaBuilder.like(position.get("name"), "%" + rol.getName()+ "%"));
        }
        criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<Rol> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
