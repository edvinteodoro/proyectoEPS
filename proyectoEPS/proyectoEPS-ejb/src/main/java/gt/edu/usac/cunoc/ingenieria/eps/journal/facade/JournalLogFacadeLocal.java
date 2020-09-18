package gt.edu.usac.cunoc.ingenieria.eps.journal.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.journal.JournalLog;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.util.List;
import javax.ejb.Local;

@Local
public interface JournalLogFacadeLocal {

    public void enableJournal(Process process) throws ValidationException;
    
    public List<JournalLog> getJournal(Integer processId);
    
    public void createJounalLog(JournalLog newJournalLog) throws LimitException, MandatoryException;
    
}
