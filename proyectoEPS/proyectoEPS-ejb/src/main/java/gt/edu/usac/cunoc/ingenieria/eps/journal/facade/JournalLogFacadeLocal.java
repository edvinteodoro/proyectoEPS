package gt.edu.usac.cunoc.ingenieria.eps.journal.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.journal.Commentary;
import gt.edu.usac.cunoc.ingenieria.eps.journal.JournalLog;
import java.util.List;
import javax.ejb.Local;

@Local
public interface JournalLogFacadeLocal {

    public List<JournalLog> getJournal(Integer processId);
    
    public void createJounalLog(JournalLog newJournalLog) throws LimitException, MandatoryException;
    
    public List<Commentary> getCommentariesJournal(Integer journalId);
    
    /*other functions for rest services*/
    
    public JournalLog getJournalById(Integer id);
    
    public JournalLog updateJournal(JournalLog jouranl);
}
