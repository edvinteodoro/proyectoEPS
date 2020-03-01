
package gt.edu.usac.cunoc.ingenieria.eps.project.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Bibliography;
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
public class BibliographyRepository {
    
    private EntityManager entityManager;
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public BibliographyRepository() {
    }

    public List<Bibliography> getBibliographiesByProject(Project project) throws MandatoryException {
        if (project.getId()== null){
            throw new MandatoryException("No existe projecto a buscar Bibliografias");
        } else {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Bibliography> criteriaQuery = criteriaBuilder.createQuery(Bibliography.class);
            Root<Bibliography> bibliographies = criteriaQuery.from(Bibliography.class);
            ArrayList<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(bibliographies.get("project"), project));
            criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
            TypedQuery<Bibliography> query = entityManager.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }  
}
