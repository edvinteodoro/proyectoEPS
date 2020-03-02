
package gt.edu.usac.cunoc.ingenieria.eps.project.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Texto;
import gt.edu.usac.cunoc.ingenieria.eps.project.Title;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class TextRepository {
    
    private EntityManager entityManager;
       
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public TextRepository() {
    }

    public List<Texto> getText(Title title) throws MandatoryException{
        if (title.getId()== null){
            throw new MandatoryException("No existe titulo a buscar Texto");
        } 
        Query query = entityManager.createQuery("SELECT t FROM Texto t WHERE t.title.id = :idTitle", Texto.class);
        query.setParameter("idTitle", title.getId());
        return query.getResultList();
    }
}
