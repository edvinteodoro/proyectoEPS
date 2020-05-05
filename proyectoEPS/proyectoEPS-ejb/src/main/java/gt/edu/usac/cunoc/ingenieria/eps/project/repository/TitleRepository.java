
package gt.edu.usac.cunoc.ingenieria.eps.project.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.project.Title;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class TitleRepository {
    
    private EntityManager entityManager;
       
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public TitleRepository() {
    }
    
    public List<Title> searchTitleByIdParent(Title titleParent) {
        Query query = entityManager.createQuery("SELECT t FROM Title t WHERE t.titleParent.id = :idParent", Title.class);
        query.setParameter("idParent", titleParent);
        return query.getResultList();
    }
}
