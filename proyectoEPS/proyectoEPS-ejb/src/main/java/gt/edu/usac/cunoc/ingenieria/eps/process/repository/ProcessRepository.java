package gt.edu.usac.cunoc.ingenieria.eps.process.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import static gt.edu.usac.cunoc.ingenieria.eps.user.repository.UserCareerRepository.FIND_CAREER_OF_USERS;
import static gt.edu.usac.cunoc.ingenieria.eps.user.repository.UserCareerRepository.ID_PARAMETER_NAME;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
@LocalBean
public class ProcessRepository {

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    
    public static final String GET_PROCESS_USER = "SELECT c.process FROM UserCareer c WHERE c.uSERuserId.userId=:userId";
    public static final String ID_PARAMETER_NAME = "userId";
    
    public List<Process> getProcess(Process process){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Process> criteriaQuery = criteriaBuilder.createQuery(Process.class);
        Root<Process> processR = criteriaQuery.from(Process.class);
        List<Predicate> predicates = new ArrayList<>();
        if (process.getId() != null) {
            predicates.add(criteriaBuilder.equal(processR.get("id"), process.getId()));
        }
        if(process.getUserCareer()!=null){
            predicates.add(criteriaBuilder.equal(processR.get("userCareer"), process.getUserCareer()));
        }
        criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<Process> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
    
    public List<Process> getProcessUser(User user){
        Query query = entityManager.createQuery(GET_PROCESS_USER);
        query.setParameter(ID_PARAMETER_NAME, user.getUserId());
        return query.getResultList();
    }
}
