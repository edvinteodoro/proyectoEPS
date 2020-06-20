package gt.edu.usac.cunoc.ingenieria.eps.process.facade;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.util.List;
import java.util.Optional;
import javax.ejb.Local;

@Local
public interface ProcessFacadeLocal {

    public List<Requeriment> getRequeriment(Requeriment requeriment);

    /**
     * Find the process base on ID
     *
     * @param id
     * @return
     * @throws UserException
     */
    public Optional<Process> findProcessById(Integer id) throws UserException;

    public List<Process> getProcess(Process process);

    public Requeriment createRequeriment(Requeriment requeriment);

    public Requeriment updaterequeriment(Requeriment requeriment);

    public Process createProcess(Process process);

    public List<Process> getProcessUser(User user);

    public Process updateProcess(Process process);
}
