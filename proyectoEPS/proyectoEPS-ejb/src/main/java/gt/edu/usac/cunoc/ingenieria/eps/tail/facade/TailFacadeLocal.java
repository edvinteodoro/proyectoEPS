package gt.edu.usac.cunoc.ingenieria.eps.tail.facade;

import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCoordinator;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.util.List;
import javax.ejb.Local;

@Local
public interface TailFacadeLocal {
    
    public TailCoordinator createTailCoordinator(TailCoordinator tailCoordinator);
    public TailCoordinator createTailCoordinator(User user,Process process);
    public List<Process> getProcessByCoordinator(User user);
    
}
