package gt.edu.usac.cunoc.ingenieria.eps.project.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.project.Bibliography;
import gt.edu.usac.cunoc.ingenieria.eps.project.DecimalCoordinate;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import gt.edu.usac.cunoc.ingenieria.eps.property.repository.PropertyRepository;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class ProjectRepository {

    private final String GET_PROJECT_BY_PROCESS = "SELECT p FROM Project p WHERE p.pROCESSid.id = :idProcess";

    private EntityManager entityManager;

    public ProjectRepository() {
    }

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Project getProjectByProcess(Process process) {
        TypedQuery<Project> query = entityManager.createQuery(GET_PROJECT_BY_PROCESS, Project.class);
        query.setParameter("idProcess", process.getId());
        return (Project) query.getSingleResult();
    }

    public void verifyProject(Project project) throws MandatoryException, LimitException {
        validateTitle(project);
        validateObjectives(project);
        validateTextSections(project);
        validateDecimalCoordinates(project);
        validateBibliographies(project);
    }

    private void validateTitle(Project project) throws MandatoryException, LimitException {
        if (project.getTitle() != null) {
            if (project.getTitle().length() > PropertyRepository.CHARACTER_LIMIT_TITLE.getValueInt()) {
                throw new LimitException("Título fuera de limites, Número de Caracteres Máximo " + PropertyRepository.CHARACTER_LIMIT_TITLE.getValueInt());
            }
        } else {
            throw new MandatoryException("Atributo Título Obligatorio");
        }
    }

    private void validateObjectives(Project project) throws LimitException, MandatoryException {
        if (!project.getObjectives().isEmpty()) {
            int quantityGeneralObjectives = 0;
            int quantitySpecificObjectives = 0;
            for (Objectives objective : project.getObjectives()) {
                if (objective.getText() != null && !objective.getText().isBlank()) {
                    if (objective.getType().shortValue() == Objectives.GENERAL_OBJETICVE) {
                        quantityGeneralObjectives++;
                    } else {
                        quantitySpecificObjectives++;
                    }
                } else {
                    throw new MandatoryException("Texto de Objetivo no puede estar vacio");
                }
            }
            if (quantityGeneralObjectives > PropertyRepository.LIMIT_GENERAL_OBJECTIVE.getValueInt()) {
                throw new LimitException("Número Máximo de Objetivos Generales: " + PropertyRepository.LIMIT_GENERAL_OBJECTIVE.getValueInt());
            }
            if (quantitySpecificObjectives > PropertyRepository.LIMIT_SPECIFIC_OBJECTIVE.getValueInt()) {
                throw new LimitException("Número Máximo de Objetivos Específicos: " + PropertyRepository.LIMIT_GENERAL_OBJECTIVE.getValueInt());
            }
        }
    }

    private void validateTextSections(Project project) throws LimitException, MandatoryException {
        for (Section section : project.getSections()) {
            if (section.getTitle().getName() != null && !section.getTitle().getName().isBlank()) {
                if (section.getTitle().getName().equals(Section.JUSTIFICATION_TEXT)) {
                    if (section.getTitle().getTexto().getText().length() > PropertyRepository.CHARACTER_LIMIT_JUSTIFICATION.getValueInt()) {
                        throw new LimitException("Caracteres Máximos para la Justificación: " + PropertyRepository.CHARACTER_LIMIT_JUSTIFICATION.getValueInt());
                    }
                } else {
                    if (section.getTitle().getTexto().getText().length() > PropertyRepository.CHARACTER_LIMIT_FOR_TEXT_OF_SECTION) {
                        throw new LimitException("Caracteres Máximos en la sección "
                                + section.getTitle().getName() + ": "
                                + PropertyRepository.CHARACTER_LIMIT_FOR_TEXT_OF_SECTION);
                    }
                }
            } else {
                throw new MandatoryException("Titulo de Sección Vacio");
            }
        }
    }

    private void validateDecimalCoordinates(Project project) throws LimitException, MandatoryException {
        for (DecimalCoordinate decimalCoordinate : project.getDecimalCoordinates()) {
            if (decimalCoordinate.getLatitude() != null) {
                if ((Double.compare(decimalCoordinate.getLatitude(), -90.9999d) == 0 || Double.compare(decimalCoordinate.getLatitude(), -90.9999d) < 0) && 
                    (Double.compare(decimalCoordinate.getLatitude(), 90.9999d) == 0 || Double.compare(decimalCoordinate.getLatitude(), 90.9999d) < 0) ) {
                    throw new LimitException("Coordenada de Latitud debe estar entre -90.9999 y 90.9999");
                }
            } else {
                throw new MandatoryException("Dato de Latitud no puede estar vacio");
            }
            if (decimalCoordinate.getLongitude() != null) {
                if ((Double.compare(decimalCoordinate.getLongitude(), -180.9999d) == 0 || Double.compare(decimalCoordinate.getLongitude(), -180.9999d) < 0) && 
                    (Double.compare(decimalCoordinate.getLongitude(), 180.9999d) == 0 || Double.compare(decimalCoordinate.getLongitude(), 180.9999d) < 0) ) {
                    throw new LimitException("Coordenada de Longitud debe estar entre -180.9999 y 180.9999");
                }
            } else {
                throw new MandatoryException("Dato de Longitud no puede estar vacio");
            }
        }
    }

    public void validateBibliographies(Project project) throws MandatoryException {
        for (Bibliography bibliography : project.getBibliographies()) {
            if (bibliography.getPublicactionYear() == null) {
                throw new MandatoryException("Fecha de Publicación no puede estar vacia");
            }
            if (bibliography.getAuthor() == null) {
                throw new MandatoryException("Autor no puede estar vacia");
            }
            if (bibliography.getCity() == null) {
                throw new MandatoryException("Ciudad no puede estar vacia");
            }
            if (bibliography.getCountry() == null) {
                throw new MandatoryException("Pais no puede estar vacia");
            }
            if (bibliography.getEditorial() == null) {
                throw new MandatoryException("Editorial no puede estar vacia");
            }
            if (bibliography.getTitle() == null) {
                throw new MandatoryException("Titulo de Bibliografia no puede estar vacia");
            }
        }
    }

    public void validateProjectoToReview(Project project) throws ValidationException, MandatoryException, LimitException {
        verifyProject(project);
        if (project.getObjectives().size() < 2) {
            throw new ValidationException("Debe existir por lo menos un Objetivo General y un Objetivo Específico");
        }
        if (project.getSections().size() <= 2) {
            throw new ValidationException("Debe existir por lo menos una Sección Adicional, además de la Introducción y Justificación");
        }
        for (Section section : project.getSections()) {
            if (section.getTitle().getTexto().getText().isBlank()) {
                throw new ValidationException("Texto en sección " + section.getTitle().getName() + " no puede estar vacio");
            }
        }
        if (project.getDecimalCoordinates().isEmpty()) {
            throw new ValidationException("Debe existir por lo menos una Coordenada Decimal");
        }
        if (project.getSchedule() == null) {
            throw new ValidationException("Archivo Calendario obligatorio");
        }
        if (project.getInvestmentPlan() == null) {
            throw new ValidationException("Archivo Plan de Inversión Obligatorio");
        }
        if (project.getBibliographies().isEmpty()) {
            throw new ValidationException("Debe existir por lo menos una Bibliografia");
        }
    }

}
