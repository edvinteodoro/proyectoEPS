
package gt.edu.usac.cunoc.ingenieria.eps.project.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
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
public class ProjectRepository {
    
    private EntityManager entityManager;

    public ProjectRepository() {
    }
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public List<Project> getProjects(Integer id, String titulo, Short state){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
        Root<Project> project = criteriaQuery.from(Project.class);
        ArrayList<Predicate> predicates = new ArrayList<>();
        if (id != null){
            predicates.add(criteriaBuilder.like(project.get("id"), "%" + id + "%"));
        }
        if (titulo != null){
            predicates.add(criteriaBuilder.like(project.get("title"), "%" + titulo + "%"));
        }
        if (state != null){
            predicates.add(criteriaBuilder.equal(project.get("state"), state));
        }
        criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<Project> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
