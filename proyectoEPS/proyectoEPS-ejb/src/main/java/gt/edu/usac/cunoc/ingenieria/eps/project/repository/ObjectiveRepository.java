
package gt.edu.usac.cunoc.ingenieria.eps.project.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.configuration.repository.PropertyRepository;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class ObjectiveRepository {
    
    private EntityManager entityManager;
       
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ObjectiveRepository() {
    }
    
    public List<Objectives> getSpecificObjectives(Project project) throws MandatoryException{
        if (project.getId()== null){
            throw new MandatoryException("No existe projecto a buscar Bibliografias");
        } else {
            Query query = entityManager.createQuery("SELECT o FROM Objectives o WHERE b.project.id = :idProject AND state = :stateObjective", Objectives.class);
            query.setParameter("idProject", project.getId());
            query.setParameter("stateObjective", Objectives.SPECIFIC_OBJECTIVE);
            return query.getResultList();
        }
    }
    
    public List<Objectives> getGeneralObjectives(Project project) throws MandatoryException{
        if (project.getId()== null){
            throw new MandatoryException("No existe projecto a buscar Bibliografias");
        } else {
            Query query = entityManager.createQuery("SELECT o FROM Objectives o WHERE b.project.id = :idProject AND state = :stateObjective", Objectives.class);
            query.setParameter("idProject", project.getId());
            query.setParameter("stateObjective", Objectives.GENERAL_OBJETICVE);
            return query.getResultList();
        }
    }
    
    public boolean verifySpecificObjectives(Project project) throws MandatoryException{
        if (project.getId()== null){
            throw new MandatoryException("No existe projecto a buscar Coordenadas");
        }
        Short quantitySpecificObjectives = (Short) entityManager.createQuery("SELECT COUNT(*) FROM Objectives o WHERE o.state = :stateObjective")
                    .setParameter("stateObjective", Objectives.SPECIFIC_OBJECTIVE).getSingleResult();
        if (quantitySpecificObjectives < PropertyRepository.LIMIT_SPECIFIC_OBJECTIVE.getValueInt()){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean verifyGeneralObjectives(Project project) throws MandatoryException{
        if (project.getId()== null){
            throw new MandatoryException("No existe projecto a buscar Coordenadas");
        }
        Short quantityGeneralObjectives = (Short) entityManager.createQuery("SELECT COUNT(*) FROM Objectives o WHERE o.state = :stateObjective")
                    .setParameter("stateObjective", Objectives.GENERAL_OBJETICVE).getSingleResult();
        if (quantityGeneralObjectives < PropertyRepository.LIMIT_GENERAL_OBJECTIVE.getValueInt()){
            return true;
        } else {
            return false;
        }
    }
}
