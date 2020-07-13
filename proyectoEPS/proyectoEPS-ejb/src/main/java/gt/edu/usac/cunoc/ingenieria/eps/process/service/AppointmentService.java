package gt.edu.usac.cunoc.ingenieria.eps.process.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.process.Appointment;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author angelrg
 */
@Stateless
@LocalBean
public class AppointmentService {

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public Appointment createAppointment(Appointment appointment) {
        entityManager.persist(appointment);
        return appointment;
    }

    public Appointment updateAppointment(Appointment appointment) {
        Appointment update = entityManager.find(Appointment.class, appointment.getId());
        if (appointment.getAdviserState() != null) {
            update.setAdviserState(appointment.getAdviserState());
        }
        if (appointment.getReviewerState() != null) {
            update.setReviewerState(appointment.getReviewerState());
        }
        if (appointment.getUserAdviser() != null) {
            update.setUserAdviser(appointment.getUserAdviser());
        }
        if (appointment.getUserReviewer() != null) {
            update.setUserReviewer(appointment.getUserReviewer());
        }
        if (appointment.getDateAction() != null) {
            update.setDateAction(appointment.getDateAction());
        }

        return entityManager.merge(update);
    }
}
