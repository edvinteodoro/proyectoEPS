
package gt.edu.usac.cunoc.ingenieria.eps.journal.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.journal.Commentary;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class CommentaryService {
     
    private EntityManager entityManager;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CommentaryService() {
    }
    
    public void createCommentary(Commentary newCommentary) throws MandatoryException{
        if (newCommentary.getUser() == null){
            throw new MandatoryException("No existe Usuario que realice el comentario");
        } 
        if (newCommentary.getJournal_Log() == null){
            throw new MandatoryException("No existe Registro de Bitácora al que realizar el comentario");
        }
        if (newCommentary.getDate() == null){
            throw new MandatoryException("No existe Fecha del comentario");
        }
        if (newCommentary.getText().isEmpty()){
            throw new MandatoryException("No existe Texto en el comentario");
        }
        entityManager.persist(newCommentary);
    }
}
