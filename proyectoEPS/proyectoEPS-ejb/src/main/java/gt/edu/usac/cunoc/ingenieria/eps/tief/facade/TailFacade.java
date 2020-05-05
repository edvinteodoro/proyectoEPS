package gt.edu.usac.cunoc.ingenieria.eps.tief.facade;

import gt.edu.usac.cunoc.ingenieria.eps.tief.TailCoordinator;
import gt.edu.usac.cunoc.ingenieria.eps.tief.repository.TailCoordinatorRepository;
import gt.edu.usac.cunoc.ingenieria.eps.tief.service.TailCoordinatorService;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class TailFacade implements TailFacadeLocal{

    @EJB
    TailCoordinatorService tailCoordiantorService;
    
    @EJB
    TailCoordinatorRepository tailCoordiantorRepository;
    
    @Override
    public TailCoordinator createTailCoordinator(TailCoordinator tailCoordinator) {
        return tailCoordiantorService.createTailCoordinator(tailCoordinator);
    }

    @Override
    public List<Process> getProcessByCoordinator(User user) {
        return tailCoordiantorRepository.getProcessByCoordinator(user);
    }
}
