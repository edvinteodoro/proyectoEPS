package gt.edu.usac.cunoc.ingenieria.eps.user.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import java.util.Optional;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;

@Stateless
@LocalBean
public class UserCareerService {
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserCareer createGroupUser(UserCareer userCareer) {
        entityManager.persist(userCareer);
        return userCareer;
    }
    
    
    
    
    
    public UserCareer updateUserCareer(UserCareer userCareer) {
        UserCareer updateUserCareer = entityManager.find(UserCareer.class, userCareer.getId());
        
        if (userCareer.getCAREERcodigo() != null) {
            updateUserCareer.setCAREERcodigo(userCareer.getCAREERcodigo());
        }
        
        if (userCareer.getUSERuserId() != null) {
            updateUserCareer.setUSERuserId(userCareer.getUSERuserId());
        }

        return updateUserCareer;
    }
    
    
    public UserCareer updateUserCareer(UserCareer userCareer, Career career, User idUser) {
        userCareer.setCAREERcodigo(career);
        userCareer.setUSERuserId(idUser);
        entityManager.merge(userCareer);
        return userCareer;
    }
    
    
    public Optional<UserCareer> removeUserFromGroup(UserCareer userCareer) {
        try {
            entityManager.remove(userCareer);
            return Optional.of(userCareer);
        } catch (TransactionRequiredException e) {
            return Optional.empty();
        }
    }
}
