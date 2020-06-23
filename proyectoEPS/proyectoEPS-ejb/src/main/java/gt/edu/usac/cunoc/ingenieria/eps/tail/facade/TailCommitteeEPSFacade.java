
package gt.edu.usac.cunoc.ingenieria.eps.tail.facade;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCommitteeEPS;
import gt.edu.usac.cunoc.ingenieria.eps.tail.repository.TailCommitteeEPSRepository;
import gt.edu.usac.cunoc.ingenieria.eps.tail.service.TailCommitteeEPSService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class TailCommitteeEPSFacade implements TailCommitteeEPSFacadeLocal{

    @EJB
    private TailCommitteeEPSService tailCommitteeEPSService;
    
    @EJB
    private TailCommitteeEPSRepository tailCommitteeEPSRepository;
        
    @Override    
    public void createTailCommiteeEPS(Process process) {
        tailCommitteeEPSService.createTailCommitteeEPS(process);
    }

    @Override
    public List<Process> getTailCommitteeEPS() {
        return tailCommitteeEPSRepository.getTailCommitteeEPS();
    }

    @Override
    public void deleteTailCommitteeEPS(Process process) {
        tailCommitteeEPSService.deleteTailCommitteeEPS(process);
    }
  
}
