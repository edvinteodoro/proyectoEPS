package gt.edu.usac.cunoc.ingenieria.eps.project.service;

import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection;
import gt.edu.usac.cunoc.ingenieria.eps.project.repository.CorrectionRepository;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class CorrectionService {

    @EJB
    CorrectionRepository correctionReposiroty;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public Correction createCorrection(Correction correction) throws UserException, MandatoryException {
        validateCorrection(correction);
        entityManager.persist(correction);
        return correction;
    }

    public void returnCorrections(Project project) throws MandatoryException {
        List<Correction> corrections;
        corrections = correctionReposiroty.getCorrections(TypeCorrection.TITLE, project.getId(), null, null);
        returnCorrection(corrections);
        corrections = correctionReposiroty.getCorrections(TypeCorrection.GENERAL_OBJETIVES, project.getId(), null, null);
        returnCorrection(corrections);
        corrections = correctionReposiroty.getCorrections(TypeCorrection.SPECIFIC_OBJETIVES, project.getId(), null, null);
        returnCorrection(corrections);
        corrections = correctionReposiroty.getCorrections(TypeCorrection.SECTION, project.getId(), null, null);
        returnCorrection(corrections);
        corrections = correctionReposiroty.getCorrections(TypeCorrection.COORDINATE, project.getId(), null, null);
        returnCorrection(corrections);
        corrections = correctionReposiroty.getCorrections(TypeCorrection.SCHEDULE, project.getId(), null, null);
        returnCorrection(corrections);
        corrections = correctionReposiroty.getCorrections(TypeCorrection.INVESTMENT_PLAN, project.getId(), null, null);
        returnCorrection(corrections);
        corrections = correctionReposiroty.getCorrections(TypeCorrection.BIBLIOGRAPHY, project.getId(), null, null);
        returnCorrection(corrections);
        corrections = correctionReposiroty.getCorrections(TypeCorrection.ANNEXED, project.getId(), null, null);
        returnCorrection(corrections);
    }

    private void returnCorrection(List<Correction> corrections) throws MandatoryException {
        for (Correction correction : corrections) {
            validateCorrection(correction);
            correction.setStatus(Boolean.TRUE);
            entityManager.merge(correction);
        }
    }

    private void validateCorrection(Correction correction) throws MandatoryException {
        if (correction == null) {
            throw new MandatoryException("Debe agregar una corrección");
        }
        if (correction.getDate() == null) {
            throw new MandatoryException("Debe agregar una fecha de Correción");
        }
        if (correction.getProject() == null) {
            throw new MandatoryException("Debe agregar un Proyecto para la Correción");
        }
        if (correction.getType() == null) {
            throw new MandatoryException("Debe agregar Un Tipo para la correción");
        }
        if (correction.getUser() == null) {
            throw new MandatoryException("Debe agregar un Usuario para la corrección");
        }
        if (correction.getText() == null || correction.getText().isBlank()) {
            throw new MandatoryException("Debe agregar texto en la correccion");
        }
    }
}
