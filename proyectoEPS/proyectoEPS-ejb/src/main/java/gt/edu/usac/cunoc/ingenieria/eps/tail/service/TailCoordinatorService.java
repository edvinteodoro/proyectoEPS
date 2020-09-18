package gt.edu.usac.cunoc.ingenieria.eps.tail.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCoordinator;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess;
import gt.edu.usac.cunoc.ingenieria.eps.process.service.ProcessService;
import gt.edu.usac.cunoc.ingenieria.eps.project.repository.ProjectRepository;
import gt.edu.usac.cunoc.ingenieria.eps.project.service.ProjectService;
import gt.edu.usac.cunoc.ingenieria.eps.tail.repository.TailCoordinatorRepository;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.UserCareerRepository;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class TailCoordinatorService {
    
    @EJB
    private ProjectRepository projectRepository;
    
    @EJB
    private ProjectService projectService;
    
    @EJB
    private UserRepository userRepository;
    
    @EJB
    private UserCareerRepository userCareerRepository;
    
    @EJB
    private TailCoordinatorRepository tailCoordinatorRepository;
    
    @EJB
    private ProcessService processService;
    
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    
    public static final String GET_TAIL_COORDIANTOR = "DELETE FROM TailCoordinator t WHERE t.process.id=:process";
    public static final String ID_PARAMETER_NAME = "process";
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public TailCoordinator createTailCoordinator(Process process) throws ValidationException, MandatoryException, LimitException{
        projectRepository.validateProjectoToReview(process.getProject());
        List<User> coordinators = userRepository.getCareerCoordinator(process);
        if (coordinators.size() > 1) {
            User coordinator = coordinators.get(0);
            int cantidad = tailCoordinatorRepository.getProcessByCoordinator(coordinator).size();
            for (int i = 0; i < coordinators.size(); i++) {
                int cant = tailCoordinatorRepository.getProcessByCoordinator(coordinators.get(i)).size();
                if (cant < cantidad) {
                    cantidad = cant;
                    coordinator = coordinators.get(i);
                }
            }
            process.setState(StateProcess.REVISION);
            processService.updateProcess(process);
            TailCoordinator newElementTailCoordination = new TailCoordinator(LocalDate.now(), userCareerRepository.getUserCareer(coordinator, process.getUserCareer().getCAREERcodigo().getName()).get(0), process);
            entityManager.persist(newElementTailCoordination);
            projectService.update(process.getProject());
            return newElementTailCoordination;
        } else if (coordinators.size() == 1) {
            process.setState(StateProcess.REVISION);
            processService.updateProcess(process);
            TailCoordinator newElementTailCoordination = new TailCoordinator(LocalDate.now(), userCareerRepository.getUserCareer(coordinators.get(0), process.getUserCareer().getCAREERcodigo().getName()).get(0), process);
            entityManager.persist(newElementTailCoordination);
            projectService.update(process.getProject());
            return newElementTailCoordination;
        } else {
            throw new ValidationException("No existe Coordinador para Su Carrera");
        }
    }
    
    public void deleteTailCoordiantor(Process process){
        Query query = entityManager.createQuery(GET_TAIL_COORDIANTOR);
        query.setParameter(ID_PARAMETER_NAME, process.getId());
        query.executeUpdate();
    }
}
