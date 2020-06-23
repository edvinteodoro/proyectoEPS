package gt.edu.usac.cunoc.ingenieria.eps.process.facade;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.repository.ProcessRepository;
import gt.edu.usac.cunoc.ingenieria.eps.process.repository.RequerimentRepository;
import gt.edu.usac.cunoc.ingenieria.eps.process.service.ProcessService;
import gt.edu.usac.cunoc.ingenieria.eps.process.service.RequerimentService;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess;
import static gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess.REVISION;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class ProcessFacade implements ProcessFacadeLocal {
    
    @EJB
    RequerimentService requerimentService;
    
    @EJB
    RequerimentRepository requerimentRepository;
    
    @EJB
    ProcessService processService;
    
    @EJB
    ProcessRepository processRepository;
    
    @Override
    public List<Requeriment> getRequeriment(Requeriment requeriment) {
        return requerimentRepository.getRequeriment(requeriment);
    }
    
    @Override
    public Requeriment createRequeriment(Requeriment requeriment) {
        return requerimentService.createRequeriment(requeriment);
    }
    
    @Override
    public Requeriment updaterequeriment(Requeriment requeriment) {
        return requerimentService.updateRequeriment(requeriment);
    }
    
    @Override
    public Process createProcess(Process process) {
        return processService.createProcess(process);
    }
    
    @Override
    public Optional<Process> findProcessById(Integer id) throws UserException {
        return processRepository.findProcessById(id);
    }
    
    @Override
    public List<Process> getProcess(Process process) {
        return processRepository.getProcess(process);
    }
    
    @Override
    public List<Process> getProcessUser(User user) {
        return processRepository.getProcessUser(user);
    }
    
    @Override
    public Process updateProcess(Process process) {
        return processService.updateProcess(process);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Process> ProcessAvailableToEPSCommittee(Integer id) throws UserException {
        Optional<Process> result = findProcessById(id);
        
        if (result.isPresent()) {
            if (result.get().getState() == REVISION) {
                if (result.get().getApprovedCareerCoordinator() && !result.get().getApprovalEPSCommission()) {
                    return result;
                }
            } else {
                throw new UserException("No apto para revisión");
            }
        } else {
            throw new UserException("El proceso no existe");
        }
        
        return Optional.ofNullable(null);
    }
    
}
