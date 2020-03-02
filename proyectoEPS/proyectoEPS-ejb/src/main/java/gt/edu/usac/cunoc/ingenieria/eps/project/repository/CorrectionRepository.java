
package gt.edu.usac.cunoc.ingenieria.eps.project.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Bibliography;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class CorrectionRepository {
    
    private EntityManager entityManager;
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CorrectionRepository() {
    }

    public List<Correction> getCorrectionBibliography(Bibliography bibliography) throws MandatoryException{
        if (bibliography.getId() == null){
            throw new MandatoryException("No existe bibliografia a buscar Correcciones");
        }
        Query query = entityManager.createQuery("SELECT c FROM Correction c WHERE c.bibliography.id = :idBibliography", Correction.class);
        query.setParameter("idBibliography", bibliography.getId());
        return query.getResultList();
    }
    
    public List<Correction> getCorrectionObjective(Objectives objective) throws MandatoryException{
        if (objective.getId() == null){
            throw new MandatoryException("No existe Objectivo a buscar Correcciones");
        }
        Query query = entityManager.createQuery("SELECT c FROM Correction c WHERE c.objective.id = :idObjective", Correction.class);
        query.setParameter("idObjective", objective.getId());
        return query.getResultList();
    }
    
    public List<Correction> getCorrectionSection(Section section) throws MandatoryException{
        if (section.getId() == null){
            throw new MandatoryException("No existe Sección a buscar Correcciones");
        }
        Query query = entityManager.createQuery("SELECT c FROM Correction c WHERE c.section.id = :idSection", Correction.class);
        query.setParameter("idSection", section.getId());
        return query.getResultList();
    }
 }
