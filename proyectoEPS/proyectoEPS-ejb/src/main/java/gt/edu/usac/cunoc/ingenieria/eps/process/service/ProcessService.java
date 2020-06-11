package gt.edu.usac.cunoc.ingenieria.eps.process.service;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ProcessService {

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    
    public Process createProcess(Process process){
        UserCareer userCareer=process.getUserCareer();
        userCareer.setProcess(process);
        entityManager.merge(userCareer);
        return process;
    }
    
    public Process updateProcess(Process process){
        entityManager.merge(process);
        return process;
    }
    
    
    
}
