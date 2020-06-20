package gt.edu.usac.cunoc.ingenieria.eps.tail.facade;

import javax.ejb.Local;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.util.List;

@Local
public interface TailCommitteeEPSFacadeLocal {
    
    public void createTailCommiteeEPS(Process process);
    
    public List<Process> getTailCommitteeEPS();

}
