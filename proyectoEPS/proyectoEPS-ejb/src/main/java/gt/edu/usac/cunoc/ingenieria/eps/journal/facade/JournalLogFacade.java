
package gt.edu.usac.cunoc.ingenieria.eps.journal.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.journal.JournalLog;
import gt.edu.usac.cunoc.ingenieria.eps.journal.repository.JournalLogRepository;
import gt.edu.usac.cunoc.ingenieria.eps.journal.service.JournalLogService;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class JournalLogFacade implements JournalLogFacadeLocal {

    @EJB
    private JournalLogRepository journalLogRepository;
    
    @EJB
    private JournalLogService journalLogService;
    
    @Override
    public List<JournalLog> getJournal(Integer processId) {
        return journalLogRepository.getJournal(processId);
    }

    @Override
    public void createJounalLog(JournalLog newJournalLog) throws LimitException, MandatoryException{
        journalLogService.createJournalLog(newJournalLog);
    }

    @Override
    public void enableJournal(Process process) throws ValidationException{
        journalLogService.enableJournal(process);
    }
    
}
