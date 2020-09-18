package gt.edu.usac.cunoc.ingenieria.eps.configuration.view;

import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.property.Property;
import gt.edu.usac.cunoc.ingenieria.eps.property.facade.PropertyFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.property.repository.PropertyRepository;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ConfigurationView implements Serializable {

    @EJB
    private PropertyFacadeLocal propertyFacade;

    private Property deadLineToSubmitDocument;
    private Property validationPercentageExtension;
    private Property minimumExecutionMonth;
    private Property maximumExecutionMonth;
    private Property timeProcessWithoutMovement;

    private Property reviewTimeDays;

    private Property characterLimitTitle;
    private Property characterLimitJustification;
    private Property limitGeneralObjective;
    private Property limitSpecificObjective;

    @PostConstruct
    public void init() {
        loadProperties();
    }

    public Property getDeadLineToSubmitDocument() {
        return deadLineToSubmitDocument;
    }

    public void setDeadLineToSubmitDocument(Property deadLineToSubmitDocument) {
        this.deadLineToSubmitDocument = deadLineToSubmitDocument;
    }

    public Property getValidationPercentageExtension() {
        return validationPercentageExtension;
    }

    public void setValidationPercentageExtension(Property validationPercentageExtension) {
        this.validationPercentageExtension = validationPercentageExtension;
    }

    public Property getMinimumExecutionMonth() {
        return minimumExecutionMonth;
    }

    public void setMinimumExecutionMonth(Property minimumExecutionMonth) {
        this.minimumExecutionMonth = minimumExecutionMonth;
    }

    public Property getMaximumExecutionMonth() {
        return maximumExecutionMonth;
    }

    public void setMaximumExecutionMonth(Property maximumExecutionMonth) {
        this.maximumExecutionMonth = maximumExecutionMonth;
    }

    public Property getTimeProcessWithoutMovement() {
        return timeProcessWithoutMovement;
    }

    public void setTimeProcessWithoutMovement(Property timeProcessWithoutMovement) {
        this.timeProcessWithoutMovement = timeProcessWithoutMovement;
    }

    public Property getReviewTimeDays() {
        return reviewTimeDays;
    }

    public void setReviewTimeDays(Property reviewTimeDays) {
        this.reviewTimeDays = reviewTimeDays;
    }

    public Property getCharacterLimitTitle() {
        return characterLimitTitle;
    }

    public void setCharacterLimitTitle(Property characterLimitTitle) {
        this.characterLimitTitle = characterLimitTitle;
    }

    public Property getCharacterLimitJustification() {
        return characterLimitJustification;
    }

    public void setCharacterLimitJustification(Property characterLimitJustification) {
        this.characterLimitJustification = characterLimitJustification;
    }

    public Property getLimitGeneralObjective() {
        return limitGeneralObjective;
    }

    public void setLimitGeneralObjective(Property limitGeneralObjective) {
        this.limitGeneralObjective = limitGeneralObjective;
    }

    public Property getLimitSpecificObjective() {
        return limitSpecificObjective;
    }

    public void setLimitSpecificObjective(Property limitSpecificObjective) {
        this.limitSpecificObjective = limitSpecificObjective;
    }

    public void loadProperties() {
        propertyFacade.loadProperties();
        this.deadLineToSubmitDocument = (Property) PropertyRepository.DEADLINE_TO_SUBMIT_DOCUMENT.clone();
        this.validationPercentageExtension = (Property)PropertyRepository.VALIDATION_PERCENTAGE_EXTENSION.clone();
        this.minimumExecutionMonth = (Property) PropertyRepository.MINIMUN_EXECUTION_MONTHS.clone();
        this.maximumExecutionMonth = (Property) PropertyRepository.MAXIMUN_EXECUTION_MONTHS.clone();
        this.timeProcessWithoutMovement = (Property) PropertyRepository.TIME_OF_PROCESS_WITHOUT_MOVEMENT.clone();
        this.reviewTimeDays = (Property) PropertyRepository.REVIEW_TIME_DAYS.clone();
        this.characterLimitTitle = (Property) PropertyRepository.CHARACTER_LIMIT_TITLE.clone();
        this.characterLimitJustification = (Property) PropertyRepository.CHARACTER_LIMIT_JUSTIFICATION.clone();
        this.limitGeneralObjective = (Property) PropertyRepository.LIMIT_GENERAL_OBJECTIVE.clone();
        this.limitSpecificObjective = (Property) PropertyRepository.LIMIT_SPECIFIC_OBJECTIVE.clone();
    }
    
    public void reloadProperties(){
        loadProperties();
        MessageUtils.addSuccessMessage("Configuraciones Cargadas");
    }
    
    public void updateProperties(){
        try {
            List<Property> properties = new ArrayList<>();
            properties.add(deadLineToSubmitDocument);
            properties.add(validationPercentageExtension);
            properties.add(minimumExecutionMonth);
            properties.add(maximumExecutionMonth);
            properties.add(timeProcessWithoutMovement);
            properties.add(reviewTimeDays);
            properties.add(characterLimitTitle);
            properties.add(characterLimitJustification);
            properties.add(limitGeneralObjective);
            properties.add(limitSpecificObjective);
            propertyFacade.updateProperties(properties);
            MessageUtils.addSuccessMessage("Configuración Actualizada");
        } catch (ValidationException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }
    
    
}
