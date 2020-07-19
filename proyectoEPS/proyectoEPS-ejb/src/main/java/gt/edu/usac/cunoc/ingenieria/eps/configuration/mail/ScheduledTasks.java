package gt.edu.usac.cunoc.ingenieria.eps.configuration.mail;

import gt.edu.usac.cunoc.ingenieria.eps.process.repository.ProcessRepository;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

/**
 *
 * @author angelrg
 */
@Singleton
public class ScheduledTasks {

    @EJB
    ProcessRepository processRepository;

    @Schedule(hour = "12")
    public void revisionRemainer() {
        processRepository.revisionRemainer();
    }
}
