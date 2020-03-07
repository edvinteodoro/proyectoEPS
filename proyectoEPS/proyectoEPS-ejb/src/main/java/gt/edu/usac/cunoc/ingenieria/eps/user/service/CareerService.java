package gt.edu.usac.cunoc.ingenieria.eps.user.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class CareerService {
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Career createCareer(Career career) {
        entityManager.persist(career);
        return career;
    }
    
    public Career updateCareer(Career career, String name) {

        if ((name != null) && (!name.isEmpty())) {
            career.setName(name);
        }
        
        return entityManager.merge(career);
    }
    
}
