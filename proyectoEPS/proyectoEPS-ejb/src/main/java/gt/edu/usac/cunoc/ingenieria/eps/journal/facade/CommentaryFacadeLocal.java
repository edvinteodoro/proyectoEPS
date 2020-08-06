
package gt.edu.usac.cunoc.ingenieria.eps.journal.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.journal.Commentary;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CommentaryFacadeLocal {
    
    public void createCommentary(Commentary newCommentary) throws MandatoryException;
    
    public List<Commentary> getCommentariesByJournalId(Integer journalId);
    
}
