package gt.edu.usac.cunoc.ingenieria.eps.process.facade;

import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.repository.ProcessRepository;
import gt.edu.usac.cunoc.ingenieria.eps.process.repository.RequerimentRepository;
import gt.edu.usac.cunoc.ingenieria.eps.process.service.ProcessService;
import gt.edu.usac.cunoc.ingenieria.eps.process.service.RequerimentService;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.util.List;
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
        return  processService.createProcess(process);
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
}
