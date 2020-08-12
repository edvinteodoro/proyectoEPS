package gt.edu.usac.cunoc.ingenieria.eps.tail.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCoordinator;
import gt.edu.usac.cunoc.ingenieria.eps.tail.repository.TailCoordinatorRepository;
import gt.edu.usac.cunoc.ingenieria.eps.tail.service.TailCoordinatorService;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class TailCoordinatorFacade implements TailCoordinatorFacadeLocal {

    @EJB
    private TailCoordinatorService tailCoordinatorService;

    @EJB
    private TailCoordinatorRepository tailCoordinatorRepository;
    
    @Override
    public TailCoordinator createTailCoordinator(Process process) throws ValidationException, MandatoryException, LimitException{
        return tailCoordinatorService.createTailCoordinator(process);
    }
    
    @Override
    public TailCoordinator getTailCoordinator(Process process) {
        return tailCoordinatorRepository.getTailCoordinator(process);
    }
    
    @Override
    public List<Process> getProcessByCoordinator(User user) {
        return tailCoordinatorRepository.getProcessByCoordinator(user);
    }

    @Override
    public void deleteTailCoordinatod(Process process) {
        tailCoordinatorService.deleteTailCoordiantor(process); 
    }
    
}
