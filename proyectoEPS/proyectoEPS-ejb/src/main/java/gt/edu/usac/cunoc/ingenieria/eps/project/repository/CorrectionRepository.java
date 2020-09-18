package gt.edu.usac.cunoc.ingenieria.eps.project.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public List<Correction> getCorrections(TypeCorrection typeCorrection, Integer projectID, Integer section, Boolean status) {
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
        if (status != null){
            predicates.add(criteriaBuilder.equal(correction.get("status"),status));
        }

        criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<Correction> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
    
    public void searchUnnotifiedCorrection(Project project) throws ValidationException{
    List<Correction> corrections;
        corrections = getCorrections(TypeCorrection.TITLE, project.getId(), null, null);
        validateCorrection(corrections);
        corrections = getCorrections(TypeCorrection.GENERAL_OBJETIVES, project.getId(), null, null);
        validateCorrection(corrections);
        corrections = getCorrections(TypeCorrection.SPECIFIC_OBJETIVES, project.getId(), null, null);
        validateCorrection(corrections);
        corrections = getCorrections(TypeCorrection.SECTION, project.getId(), null, null);
        validateCorrection(corrections);
        corrections = getCorrections(TypeCorrection.COORDINATE, project.getId(), null, null);
        validateCorrection(corrections);
        corrections = getCorrections(TypeCorrection.SCHEDULE, project.getId(), null, null);
        validateCorrection(corrections);
        corrections = getCorrections(TypeCorrection.INVESTMENT_PLAN, project.getId(), null, null);
        validateCorrection(corrections);
        corrections = getCorrections(TypeCorrection.BIBLIOGRAPHY, project.getId(), null, null);
        validateCorrection(corrections);
        corrections = getCorrections(TypeCorrection.ANNEXED, project.getId(), null, null);
        validateCorrection(corrections);
    }
    
    private void validateCorrection(List<Correction> corrections) throws ValidationException{
        for (Correction correction : corrections) {
            if (!correction.getStatus()){
                throw new ValidationException("Existen Correcciones Sin Notificar");
            }
        }
    }
}
