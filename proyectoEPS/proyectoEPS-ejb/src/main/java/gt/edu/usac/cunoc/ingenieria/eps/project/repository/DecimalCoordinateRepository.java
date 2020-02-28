
package gt.edu.usac.cunoc.ingenieria.eps.project.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.DecimalCoordinate;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
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
public class DecimalCoordinateRepository {
    
    private EntityManager entityManager;

    public DecimalCoordinateRepository() {
    }
       
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public List<DecimalCoordinate> getDecimalCoordinateByIdProject(Project project) throws MandatoryException{
        if (project.getId()== null){
            throw new MandatoryException("No existe projecto a buscar Coordenadas");
        } else {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<DecimalCoordinate> criteriaQuery = criteriaBuilder.createQuery(DecimalCoordinate.class);
            Root<DecimalCoordinate> decimalCoordinate = criteriaQuery.from(DecimalCoordinate.class);
            ArrayList<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(decimalCoordinate.get("id"), "%" + project.getId() + "%"));
            criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
            TypedQuery<DecimalCoordinate> query = entityManager.createQuery(criteriaQuery);
            return query.getResultList();
        }
        
    }
    
}
