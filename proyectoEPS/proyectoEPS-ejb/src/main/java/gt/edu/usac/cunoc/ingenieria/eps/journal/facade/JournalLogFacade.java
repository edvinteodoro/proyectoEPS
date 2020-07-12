
package gt.edu.usac.cunoc.ingenieria.eps.journal.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.journal.Image;
import gt.edu.usac.cunoc.ingenieria.eps.journal.JournalLog;
import gt.edu.usac.cunoc.ingenieria.eps.journal.repository.ImageRepository;
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
    
    @EJB
    private ImageRepository imageRepository;
    
    @Override
    public List<JournalLog> getJournal(Integer processId) {
        return journalLogRepository.getJournal(processId);
    }

    @Override
    public void createJounalLog(JournalLog newJournalLog) throws LimitException, MandatoryException{
        journalLogService.createJournalLog(newJournalLog);
    }

    @Override
    public Image getImageById(Integer imageId) {
        return imageRepository.getImage(imageId);
    }
    
}
