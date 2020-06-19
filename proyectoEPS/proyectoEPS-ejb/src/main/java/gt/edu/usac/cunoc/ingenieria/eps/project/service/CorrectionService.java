package gt.edu.usac.cunoc.ingenieria.eps.project.service;

import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author angelrg
 */
@Stateless
@LocalBean
public class CorrectionService {

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public Correction createCorrection(Correction correction) throws UserException {
        if (correction == null) {
            throw new UserException("Debe agregar una correción");
        }

        if (correction.getText() != null && !(new String(correction.getText()).isEmpty())) {
            entityManager.persist(correction);
        } else {
            throw new UserException("Debe agregar texto en la correccion");
        }
        return correction;
    }
}
