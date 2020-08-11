
package gt.edu.usac.cunoc.ingenieria.eps.project.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


@Stateless
@LocalBean
public class ProjectRepository {
    
    private final String GET_PROJECT_BY_PROCESS = "SELECT p FROM Project p WHERE p.pROCESSid.id = :idProcess";
    
    private EntityManager entityManager;

    public ProjectRepository() {
    }
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public Project getProjectByProcess(Process process){
        TypedQuery<Project>  query = entityManager.createQuery(GET_PROJECT_BY_PROCESS, Project.class);
        query.setParameter("idProcess", process.getId());
        return (Project) query.getSingleResult();
    }

}
