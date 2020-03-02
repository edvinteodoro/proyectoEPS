package gt.edu.usac.cunoc.ingenieria.eps.user.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.UserCareerRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@Stateless
@LocalBean
public class UserCareerService {
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    
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
}
