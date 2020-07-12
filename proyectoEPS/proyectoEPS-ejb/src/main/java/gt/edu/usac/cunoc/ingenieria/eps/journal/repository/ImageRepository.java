
package gt.edu.usac.cunoc.ingenieria.eps.journal.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.journal.Image;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class ImageRepository {

    private final String GET_IMAGE_BY_ID = "SELECT i FROM Image i WHERE i.id = :idImage";
    
    private EntityManager entityManager;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public Image getImage(Integer imageId) {
        Query query = entityManager.createQuery(GET_IMAGE_BY_ID, Image.class);
        query.setParameter("idImage", imageId);
        return (Image) query.getSingleResult();
    }
}
