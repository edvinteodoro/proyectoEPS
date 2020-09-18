package gt.edu.usac.cunoc.ingenieria.eps.property.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.property.Property;
import gt.edu.usac.cunoc.ingenieria.eps.property.repository.PropertyRepository;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class PropertyService {

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PropertyService() {
    }

    public void updateProperties(List<Property> properties) throws ValidationException {
        validateMinimumMaximumExecutionMonths(properties);
        for (Property property : properties) {
            if (property.getId() == PropertyRepository.DEADLINE_TO_SUBMIT_DOCUMENT.getId().intValue()) {
                if (property.getValueDate().isBefore(LocalDate.now())) {
                    throw new ValidationException("Fecha Limite para Recepción de Requisitos es anterior a la fecha actual");
                }
                PropertyRepository.DEADLINE_TO_SUBMIT_DOCUMENT.setValueDate(property.getValueDate());
                entityManager.merge(PropertyRepository.DEADLINE_TO_SUBMIT_DOCUMENT);
            }
            if (property.getId() == PropertyRepository.VALIDATION_PERCENTAGE_EXTENSION.getId().intValue()) {
                if (property.getValueInt() < 0 || property.getValueInt() > 100) {
                    throw new ValidationException("Porcentaje Mínimo para Extensión debe ser entre 0 y 100");
                }
                PropertyRepository.VALIDATION_PERCENTAGE_EXTENSION.setValueInt(property.getValueInt());
                entityManager.merge(PropertyRepository.VALIDATION_PERCENTAGE_EXTENSION);
            }
            if (property.getId() == PropertyRepository.MINIMUN_EXECUTION_MONTHS.getId().intValue()) {
                if (property.getValueInt() < 1) {
                    throw new ValidationException("Tiempo mínimo de Ejecución debe ser mayor a 0");
                }
                PropertyRepository.MINIMUN_EXECUTION_MONTHS.setValueInt(property.getValueInt());
                entityManager.merge(PropertyRepository.MINIMUN_EXECUTION_MONTHS);
            }
            if (property.getId() == PropertyRepository.MAXIMUN_EXECUTION_MONTHS.getId().intValue()) {
                if (property.getValueInt() < 1) {
                    throw new ValidationException("Tiempo máximo de Ejecución debe ser mayor a 0");
                }
                PropertyRepository.MAXIMUN_EXECUTION_MONTHS.setValueInt(property.getValueInt());
                entityManager.merge(PropertyRepository.MAXIMUN_EXECUTION_MONTHS);
            }
            if (property.getId() == PropertyRepository.TIME_OF_PROCESS_WITHOUT_MOVEMENT.getId().intValue()) {
                if (property.getValueInt() < 0) {
                    throw new ValidationException("Tiempo para finalizar Proceso sin movimiento no debe ser mayor a 0");
                }
                PropertyRepository.TIME_OF_PROCESS_WITHOUT_MOVEMENT.setValueInt(property.getValueInt());
                entityManager.merge(PropertyRepository.TIME_OF_PROCESS_WITHOUT_MOVEMENT);
            }            
            if (property.getId() == PropertyRepository.REVIEW_TIME_DAYS.getId().intValue()) {
                if (property.getValueInt() < 0) {
                    throw new ValidationException("Tiempo para Pecordatorio de Revisión debe ser mayor a 0");
                }
                PropertyRepository.REVIEW_TIME_DAYS.setValueInt(property.getValueInt());
                entityManager.merge(PropertyRepository.REVIEW_TIME_DAYS);
            }
            if (property.getId() == PropertyRepository.CHARACTER_LIMIT_TITLE.getId().intValue()) {
                if (property.getValueInt() < 1 || property.getValueInt() > 400) {
                    throw new ValidationException("Cantidad Máxima de Caracteres para Titulo principal debe ser entre 1 y 400");
                }
                PropertyRepository.CHARACTER_LIMIT_TITLE.setValueInt(property.getValueInt());
                entityManager.merge(PropertyRepository.CHARACTER_LIMIT_TITLE);
            }
            if (property.getId() == PropertyRepository.CHARACTER_LIMIT_JUSTIFICATION.getId().intValue()) {
                if (property.getValueInt() < 1 || property.getValueInt() > 65500) {
                    throw new ValidationException("Cantidad Máxima de Caracteres para Justificación debe ser entre 1 y 65,500");
                }
                PropertyRepository.CHARACTER_LIMIT_JUSTIFICATION.setValueInt(property.getValueInt());
                entityManager.merge(PropertyRepository.CHARACTER_LIMIT_JUSTIFICATION);
            }
            if (property.getId() == PropertyRepository.LIMIT_GENERAL_OBJECTIVE.getId().intValue()) {
                if (property.getValueInt() < 0) {
                    throw new ValidationException("Limite de Objetivos Generales debe ser mayor a 0");
                }
                PropertyRepository.LIMIT_GENERAL_OBJECTIVE.setValueInt(property.getValueInt());
                entityManager.merge(PropertyRepository.LIMIT_GENERAL_OBJECTIVE);
            }
            if (property.getId() == PropertyRepository.LIMIT_SPECIFIC_OBJECTIVE.getId().intValue()) {
                if (property.getValueInt() < 0) {
                    throw new ValidationException("Limite de Objetivos Específicos debe ser mayor a 0");
                }
                PropertyRepository.LIMIT_SPECIFIC_OBJECTIVE.setValueInt(property.getValueInt());
                entityManager.merge(PropertyRepository.LIMIT_SPECIFIC_OBJECTIVE);
            }
        }
    }

    private void validateMinimumMaximumExecutionMonths(List<Property> properties) throws ValidationException {
        Property maximum = null;
        Property minimum = null;
        for (Property property : properties) {
            if (property.getId() == PropertyRepository.MINIMUN_EXECUTION_MONTHS.getId().intValue()) {
                minimum = property;
            }
            if (property.getId() == PropertyRepository.MAXIMUN_EXECUTION_MONTHS.getId().intValue()) {
                maximum = property;
            }
        }
        if (maximum != null && minimum != null) {
            if (minimum.getValueInt() > maximum.getValueInt()) {
                throw new ValidationException("Tiempo Mínimo de Ejecución no puede ser mayor al Tiempo Máximo de Ejecución");
            }
        }
    }
}
