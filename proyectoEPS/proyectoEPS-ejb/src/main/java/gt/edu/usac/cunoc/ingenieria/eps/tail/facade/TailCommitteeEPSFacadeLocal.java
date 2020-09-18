package gt.edu.usac.cunoc.ingenieria.eps.tail.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import javax.ejb.Local;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.util.List;

@Local
public interface TailCommitteeEPSFacadeLocal {
    
    public void createTailCommiteeEPS(Process process) throws ValidationException, MandatoryException, LimitException ;
    
    public List<Process> getTailCommitteeEPS();
    
    public void deleteTailCommitteeEPS(Process process);

}
