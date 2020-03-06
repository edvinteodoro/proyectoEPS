package gt.edu.usac.cunoc.ingenieria.eps.user.service;

import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class RolService {
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Rol createRolUser(Rol rol) throws UserException {
        if (rol == null) {
            throw new UserException("rolUser is null");
        }
        entityManager.persist(rol);
        return rol;
    }

    public Rol updateRolUser(Rol rol) throws UserException {
        if (rol == null) {
            throw new UserException("rolUser is null");
        }
        Rol updateRol = entityManager.find(Rol.class, rol.getId());
        updateRol.setName(rol.getName());
        return rol;
    }
    
}
