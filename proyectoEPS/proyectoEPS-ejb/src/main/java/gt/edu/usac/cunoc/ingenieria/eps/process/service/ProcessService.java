package gt.edu.usac.cunoc.ingenieria.eps.process.service;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.UserRepository;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ProcessService {

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    
    @EJB
    private UserRepository userRepository;
    
    public Process createProcess(Process process){
        UserCareer userCareer=process.getUserCareer();
        userCareer.setProcess(process);
        if (userCareer.getId() != null) {
            entityManager.merge(userCareer);
        }else{
            entityManager.persist(userCareer);
        }
        return process;
    }

    public Process updateProcess(Process process) {
        entityManager.merge(process);
        return process;
    }
    
    /**
     * This method Assign a SUPERVISOR_EPS to a Process doing load balancing
     * @param career  Career to which the student owning the process belongs
     * @param process Process to Assign a SUPERVISOR_EPS
     * @throws ValidationException If not exist a SUPERVISOR_EPS for the Career
     */
    public void assignEPSSUpervisorToProcess(Career career, Process process) throws ValidationException{
        List<User> supervisorEPS = userRepository.getSupervisorEPSbyCareer(career);
        User supervisorEPSToAssign;
        if (supervisorEPS.size() > 1){    
            User currentUser;
            User nextUser;
            for (int i = 0; i < supervisorEPS.size()-1; i++) {
                currentUser = supervisorEPS.get(i);
                nextUser = supervisorEPS.get(i + 1);
                int countProcessesCurrentUser = userRepository.getNumberProcessesBySupervisorEPS(currentUser);
                int countProcessesNextUser = userRepository.getNumberProcessesBySupervisorEPS(nextUser);
                if (countProcessesCurrentUser > countProcessesNextUser){
                    supervisorEPSToAssign = nextUser;
                } else {
                    supervisorEPSToAssign = currentUser;
                }
                process.setSupervisorEPS(supervisorEPSToAssign);
            }   
        } else if (supervisorEPS.size() == 1){
            supervisorEPSToAssign = supervisorEPS.get(0);
            process.setSupervisorEPS(supervisorEPSToAssign);
        } else {
            throw new ValidationException("No existe Supervisor de EPS para su Carrera");
        }
        updateProcess(process);
    }
    
}
