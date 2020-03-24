package gt.edu.usac.cunoc.ingenieria.eps.user.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
@LocalBean
public class CareerRepository {
    public static final String FIND_BY_ID = "SELECT g FROM Career g WHERE g.codigo = :codigo";
    public static final String GET_ALL = "SELECT g FROM Career g";
    
    
    private EntityManager entityManager;
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public Optional<Career> findById(Integer codigo){
        TypedQuery<Career> typedQuery = entityManager.createQuery(FIND_BY_ID,Career.class).setParameter("codigo", codigo);
        try {
            return Optional.of(typedQuery.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (NullPointerException e){
            return Optional.empty();
        }
 
    }
    
    public List<Career> getAll(){
        TypedQuery<Career> typedQuery = entityManager.createQuery(GET_ALL,Career.class);
        return typedQuery.getResultList();
    }
    
    public List<Career> getCareer(Career career){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Career> criteriaQuery = criteriaBuilder.createQuery(Career.class);
        Root<Career> Career = criteriaQuery.from(Career.class);
        List<Predicate> predicates = new ArrayList<>();
        if (career.getCodigo() != null) {
            predicates.add(criteriaBuilder.equal(Career.get("codigo"), career.getCodigo()));
        }
        criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<Career> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
