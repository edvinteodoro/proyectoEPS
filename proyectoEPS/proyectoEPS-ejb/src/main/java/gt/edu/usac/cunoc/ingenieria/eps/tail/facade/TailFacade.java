package gt.edu.usac.cunoc.ingenieria.eps.tail.facade;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_CARRERA;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCoordinator;
import gt.edu.usac.cunoc.ingenieria.eps.tail.repository.TailCoordinatorRepository;
import gt.edu.usac.cunoc.ingenieria.eps.tail.service.TailCoordinatorService;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.service.ProcessService;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class TailFacade implements TailFacadeLocal {

    @EJB
    TailCoordinatorService tailCoordiantorService;

    @EJB
    TailCoordinatorRepository tailCoordiantorRepository;
    
    @EJB
    ProcessService processService;

    @EJB
    private UserFacadeLocal userFacade;

    private LocalDate date = LocalDate.now();

    @Override
    public TailCoordinator createTailCoordinator(TailCoordinator tailCoordinator) {
        return tailCoordiantorService.createTailCoordinator(tailCoordinator);
    }

    @Override
    public List<Process> getProcessByCoordinator(User user) {
        return tailCoordiantorRepository.getProcessByCoordinator(user);
    }

    @Override
    public TailCoordinator createTailCoordinator(User user, Process process) {
        List<User> coordinators = userFacade.getCareerCoordinator(process);
        if (coordinators.size() > 1) {
            User coordinator = coordinators.get(0);
            int cantidad = tailCoordiantorRepository.getProcessByCoordinator(coordinator).size();
            for (int i = 0; i < coordinators.size(); i++) {
                int cant = tailCoordiantorRepository.getProcessByCoordinator(coordinators.get(i)).size();
                if (cant < cantidad) {
                    cantidad = cant;
                    coordinator = coordinators.get(i);
                }
            }
            processService.updateProcess(process);
            return tailCoordiantorService.createTailCoordinator(new TailCoordinator(date, userFacade.getUserCareer(coordinator, process.getUserCareer().getCAREERcodigo().getName()), process));
        } else if (coordinators.size() == 1) {
            processService.updateProcess(process);
            return tailCoordiantorService.createTailCoordinator(new TailCoordinator(date, userFacade.getUserCareer(coordinators.get(0), process.getUserCareer().getCAREERcodigo().getName()), process));
        } else {
            return null;
        }
    }

    @Override
    public void deleteTailCoordinatod(Process process) {
        tailCoordiantorService.deleteTailCoordiantor(process); 
    }

    @Override
    public TailCoordinator getTailCoordianteor(Process process) {
        return tailCoordiantorRepository.getTailCoordinator(process);
    }
}
