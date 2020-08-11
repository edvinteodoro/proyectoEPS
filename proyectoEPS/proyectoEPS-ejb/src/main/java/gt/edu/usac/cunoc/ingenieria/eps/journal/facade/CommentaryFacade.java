
package gt.edu.usac.cunoc.ingenieria.eps.journal.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.journal.Commentary;
import gt.edu.usac.cunoc.ingenieria.eps.journal.repository.CommentaryRepository;
import gt.edu.usac.cunoc.ingenieria.eps.journal.service.CommentaryService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class CommentaryFacade implements CommentaryFacadeLocal{

    @EJB
    CommentaryService commentaryService;
    
    @EJB
    CommentaryRepository commentaryRepository;
    
    @Override
    public void createCommentary(Commentary newCommentary) throws MandatoryException{
        commentaryService.createCommentary(newCommentary);
    }

    @Override
    public List<Commentary> getCommentariesByJournalId(Integer journalId) {
        return commentaryRepository.getCommentariesJournal(journalId);
    }
    
}
