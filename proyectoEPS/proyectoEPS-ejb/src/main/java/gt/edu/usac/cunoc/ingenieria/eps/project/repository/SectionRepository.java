
package gt.edu.usac.cunoc.ingenieria.eps.project.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class SectionRepository {
    
    private EntityManager entityManager;
       
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public SectionRepository() {
    }
    
    public Section getIntroduction(Project project) throws MandatoryException{
        if (project.getId()== null){
            throw new MandatoryException("No existe projecto a buscar Introducción");
        } 
        Query query = entityManager.createQuery("SELECT s FROM Section o WHERE b.project.id = :idProject AND type = :typeSection", Section.class);
        query.setParameter("idProject", project.getId());
        query.setParameter("typeSection", Section.INTRODUCTION);
        return (Section) query.getSingleResult();
    }
    
    public Section getJustification(Project project) throws MandatoryException{
        if (project.getId()== null){
            throw new MandatoryException("No existe projecto a buscar Justificación");
        } 
        Query query = entityManager.createQuery("SELECT s FROM Section o WHERE b.project.id = :idProject AND type = :typeSection", Section.class);
        query.setParameter("idProject", project.getId());
        query.setParameter("typeSection", Section.JUSTIFICATION);
        return (Section) query.getSingleResult();
    }
}
