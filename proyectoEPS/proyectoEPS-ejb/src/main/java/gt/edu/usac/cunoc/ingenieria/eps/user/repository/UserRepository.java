package gt.edu.usac.cunoc.ingenieria.eps.user.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public class UserRepository {
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public List<User> getUser(User user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userR = criteriaQuery.from(User.class);
        List<Predicate> predicates = new ArrayList<>();
        if (user.getDpi() != null) {
            predicates.add(criteriaBuilder.equal(userR.get("dpi"), user.getDpi()));
        }
        if (user.getCodePersonal() != null) {
            predicates.add(criteriaBuilder.like(userR.get("codePersonal"), "%" + user.getCodePersonal() + "%"));
        }
        if (user.getCarnet() != null) {
            predicates.add(criteriaBuilder.equal(userR.get("carnet"), user.getCarnet()));
        }
        if (user.getAcademicRegister() != null) {
            predicates.add(criteriaBuilder.equal(userR.get("academicRegister"), user.getAcademicRegister()));
        }
        if (user.getFirstName() != null) {
            predicates.add(criteriaBuilder.like(userR.get("firstName"), "%" + user.getFirstName() + "%"));
        }
        if (user.getLastName() != null) {
            predicates.add(criteriaBuilder.like(userR.get("lastName"), "%" + user.getLastName() + "%"));
        }
        if (user.getEmail() != null) {
            predicates.add(criteriaBuilder.equal(userR.get("email"), user.getEmail()));
        }
        if (user.getPhone() != null) {
            predicates.add(criteriaBuilder.equal(userR.get("phone"), user.getPhone()));
        }
        if (user.getPassword() != null) {
            predicates.add(criteriaBuilder.equal(userR.get("password"), user.getPassword()));
        }
        if (user.getDirection() != null) {
            predicates.add(criteriaBuilder.equal(userR.get("direction"), user.getDirection()));
        }
        if (user.getState() != 0) {
            predicates.add(criteriaBuilder.equal(userR.get("state"), user.getState()));
        }
        if (user.getROLid() != null) {
            predicates.add(criteriaBuilder.equal(userR.get("ROL_id"), user.getROLid().getId()));
        }
        criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
