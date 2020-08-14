
package gt.edu.usac.cunoc.ingenieria.eps.process.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Observation;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ObservationService {
    
    private EntityManager entityManager;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ObservationService() {
    }
    
     public void createObservation(Observation newObservation) throws MandatoryException{
        if (newObservation.getUser() == null){
            throw new MandatoryException("No existe Usuario que realice la Observación");
        } 
        if (newObservation.getRequeriment() == null){
            throw new MandatoryException("No existe Requerimientos al que realizar la Observación");
        }
        if (newObservation.getDate() == null){
            throw new MandatoryException("No existe Fecha de la Observación");
        }
        if (newObservation.getText().isEmpty()){
            throw new MandatoryException("No existe Texto en la Observación");
        }
        entityManager.persist(newObservation);
    }
}
