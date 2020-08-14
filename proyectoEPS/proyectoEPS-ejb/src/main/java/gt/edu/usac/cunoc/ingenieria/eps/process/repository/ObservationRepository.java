package gt.edu.usac.cunoc.ingenieria.eps.process.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.process.Observation;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class ObservationRepository {

    private final String GET_OBSERVATIONS_BY_REQUERIMENT_ID = "SELECT o FROM Observation o WHERE o.requeriment.id = :idRequeriment ORDER BY o.date ASC";

    private EntityManager entityManager;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ObservationRepository() {
    }

    public List<Observation> getRequerimentsObservations(Integer requerimentId) {
        Query query = entityManager.createQuery(GET_OBSERVATIONS_BY_REQUERIMENT_ID, Observation.class);
        query.setParameter("idRequeriment", requerimentId);
        return query.getResultList();
    }
}
