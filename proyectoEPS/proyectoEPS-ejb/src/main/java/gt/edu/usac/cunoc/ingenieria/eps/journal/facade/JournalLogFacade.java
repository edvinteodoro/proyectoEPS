package gt.edu.usac.cunoc.ingenieria.eps.journal.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.journal.Commentary;
import gt.edu.usac.cunoc.ingenieria.eps.journal.JournalLog;
import gt.edu.usac.cunoc.ingenieria.eps.journal.repository.JournalLogRepository;
import gt.edu.usac.cunoc.ingenieria.eps.journal.service.JournalLogService;
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
    public void createJounalLog(JournalLog newJournalLog) throws LimitException, MandatoryException {
        journalLogService.createJournalLog(newJournalLog);
    }

    @Override
    public List<Commentary> getCommentariesJournal(Integer journalId) {
        return journalLogRepository.getCommentariesJournal(journalId);
    }
    
    /*other function for rest services*/

    @Override
    public JournalLog getJournalById(Integer id) {
        return journalLogRepository.getJournalById(id);
    }

    @Override
    public JournalLog updateJournal(JournalLog jouranl) {
        return journalLogService.updateJournal(jouranl);
    }

}
