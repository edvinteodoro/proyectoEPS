package gt.edu.usac.cunoc.ingenieria.eps.project.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Bibliography;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
@LocalBean
public class CorrectionRepository {

    private EntityManager entityManager;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Correction> getCorrections(TypeCorrection typeCorrection, Integer projectID, Integer section) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Correction> criteriaQuery = criteriaBuilder.createQuery(Correction.class);
        Root<Correction> correction = criteriaQuery.from(Correction.class);
        List<Predicate> predicates = new ArrayList<>();

        if (typeCorrection != null) {
            predicates.add(criteriaBuilder.equal(correction.get("type"), typeCorrection));
        }
        if (projectID != null) {
            predicates.add(criteriaBuilder.equal(correction.get("project").get("id"), projectID));
        }
        if (section != null) {
            predicates.add(criteriaBuilder.equal(correction.get("section").get("id"), section));
        }

        criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<Correction> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Correction> getCorrectionBibliography(Bibliography bibliography) throws MandatoryException {
        if (bibliography.getId() == null) {
            throw new MandatoryException("No existe bibliografia a buscar Correcciones");
        }
        Query query = entityManager.createQuery("SELECT c FROM Correction c WHERE c.bibliography.id = :idBibliography", Correction.class);
        query.setParameter("idBibliography", bibliography.getId());
        return query.getResultList();
    }

    public List<Correction> getCorrectionObjective(Objectives objective) throws MandatoryException {
        if (objective.getId() == null) {
            throw new MandatoryException("No existe Objectivo a buscar Correcciones");
        }
        Query query = entityManager.createQuery("SELECT c FROM Correction c WHERE c.objective.id = :idObjective", Correction.class);
        query.setParameter("idObjective", objective.getId());
        return query.getResultList();
    }

    public List<Correction> getCorrectionSection(Section section) throws MandatoryException {
        if (section.getId() == null) {
            throw new MandatoryException("No existe Sección a buscar Correcciones");
        }
        Query query = entityManager.createQuery("SELECT c FROM Correction c WHERE c.section.id = :idSection", Correction.class);
        query.setParameter("idSection", section.getId());
        return query.getResultList();
    }
}
