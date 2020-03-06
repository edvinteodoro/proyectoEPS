
package gt.edu.usac.cunoc.ingenieria.eps.project.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Bibliography;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class BibliographyRepository {
    
    private EntityManager entityManager;
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public BibliographyRepository() {
    }

    public List<Bibliography> getBibliographiesByProject(Project project) throws MandatoryException {
        if (project.getId()== null){
            throw new MandatoryException("No existe projecto a buscar Bibliografias");
        } else {
            return entityManager.createQuery("SELECT b FROM Bibliography b WHERE b.project.id = :idProject", Bibliography.class)
                    .setParameter("idProject", project.getId()).getResultList();
        }
    }  
}
