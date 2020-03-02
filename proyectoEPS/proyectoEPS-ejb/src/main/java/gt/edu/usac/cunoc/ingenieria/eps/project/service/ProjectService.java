
package gt.edu.usac.cunoc.ingenieria.eps.project.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ProjectService {
    
    private EntityManager entityManager;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ProjectService() {
    }
    
//    public Project create(Project project) throws MandatoryException{
//        if (project.getTitle() == null){
//            throw new MandatoryException("Atributo Titulo Obligatorio");
//        }
//        if (project.getSchedule() == null){
//            throw new MandatoryException("Archivo Calendario Obligatorio");
//        }
//        if (project.getInvestmentPlan() == null){
//            throw new MandatoryException("Archivo Plan de Inversiones Obligatorio");
//        }
//        
//    } 
}
