package gt.edu.usac.cunoc.ingenieria.eps.tail.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCoordinator;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.util.List;
import javax.ejb.Local;

@Local
public interface TailCoordinatorFacadeLocal {
    
    public TailCoordinator createTailCoordinator(Process process)throws ValidationException, MandatoryException, LimitException;
    
    public TailCoordinator getTailCoordinator(Process process);
    
    public List<Process> getProcessByCoordinator(User user);
    
    public void deleteTailCoordinatod(Process process);
    
}
