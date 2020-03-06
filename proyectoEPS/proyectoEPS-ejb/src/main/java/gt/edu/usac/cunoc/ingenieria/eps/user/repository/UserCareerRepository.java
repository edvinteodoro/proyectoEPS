package gt.edu.usac.cunoc.ingenieria.eps.user.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import java.util.List;
import java.util.Optional;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class UserCareerRepository {
    
    public static final String FIND_USERS_BY_CAREER = "SELECT u FROM UserCareer g, User u WHERE g.career.codigo = :cAREERcodigo AND g.user.userId = u.uSERuserId";
    public static final String FIND_CAREER_OF_USERS = "SELECT u FROM UserCareer gu, Group g WHERE gu.user.userId = :uSERuserId AND gu.group.codigo = g.cAREERcodigo";
    public static final String FIND_USERCAREER_BY_CAREER_AND_USER = "SELECT g FROM UserCareer g WHERE g.group.codigo = :cAREERcodigo AND g.user.userId = :uSERuserId";
    public static final String GET_ALL_CAREER_USERS = "SELECT gu FROM UserCareer gu";
    public static final String CAREER_PARAMETER_NAME = "cAREERcodigo";
    public static final String USER_PARAMETER_NAME = "uSERuserId";
    public static final String ID_PARAMETER_NAME = "userId";

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<UserCareer> findGroupUserById(Integer id) {
        return Optional.of(entityManager.find(UserCareer.class, id));
    }

    public List<User> findUsersByGroup(Integer careerId) {
        Query query = entityManager.createQuery(FIND_USERS_BY_CAREER);
        query.setParameter(CAREER_PARAMETER_NAME, careerId);
        return query.getResultList();
    }

    public List<Career> findGroupsOfUser(String id) {
        Query query = entityManager.createQuery(FIND_CAREER_OF_USERS);
        query.setParameter(ID_PARAMETER_NAME, id);
        return query.getResultList();
    }

    public List<UserCareer> getGroupUserByUserAndGroup(Integer groupId, String id) {
        Query query = entityManager.createQuery(FIND_USERCAREER_BY_CAREER_AND_USER);
        query.setParameter(CAREER_PARAMETER_NAME, groupId);
        query.setParameter(ID_PARAMETER_NAME, id);
        return query.getResultList();
    }

    public List<UserCareer> getAllGroupUser() {
        Query query = entityManager.createQuery(GET_ALL_CAREER_USERS);
        return query.getResultList();
    }
    
}
