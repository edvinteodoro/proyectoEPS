
package gt.edu.usac.cunoc.ingenieria.eps.project.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import java.time.LocalDate;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ObjetiveService {
    
    private EntityManager entityManager;
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ObjetiveService() {
    }
    
    public Objectives create(Objectives objective) throws MandatoryException{
        if (objective.getState() == null){
            throw new MandatoryException("Atributo Tipo Obligatorio");
        }
        if (objective.getText() == null){
            throw new MandatoryException("Atributo Texto Obligatorio");
        }
        objective.setLastModificationDate(LocalDate.now());
        entityManager.persist(objective);
        return objective;
    }
    
    public Objectives update(Objectives objective,Byte[] newText) throws MandatoryException{
        if (newText == null){
            throw new MandatoryException("Atributo Texto Obligatorio");
        }
        objective.setText(newText);
        objective.setLastModificationDate(LocalDate.now());
        return objective;
    }
    
    public boolean remove(Objectives objective){
        try {
            entityManager.remove(objective);
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }
}
