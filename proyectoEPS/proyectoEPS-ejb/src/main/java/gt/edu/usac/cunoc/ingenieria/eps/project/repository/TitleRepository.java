
package gt.edu.usac.cunoc.ingenieria.eps.project.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
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
    
    public List<Title> getTitles(Section section) throws MandatoryException{
        if (section.getId()== null){
            throw new MandatoryException("No existe Sección a buscar Titulo");
        } 
        Query query = entityManager.createQuery("SELECT t FROM Title t WHERE t.section = :idSection", Title.class);
        query.setParameter("idSection", section.getId());
        return query.getResultList();
    }
}
