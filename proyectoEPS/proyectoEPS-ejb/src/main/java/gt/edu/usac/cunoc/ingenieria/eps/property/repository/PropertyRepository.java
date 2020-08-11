package gt.edu.usac.cunoc.ingenieria.eps.property.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.property.Property;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class PropertyRepository {

    public static Property MAXIMUM_SUPERVISOR_WORKLOAD;
    public static Property VALIDATION_PERCENTAGE_EXTENSION;
    public static Property REVIEW_TIME_DAYS;
    public static Property TIME_OF_PROCESS_WITHOUT_MOVEMENT;
    public static Property DEADLINE_TO_SUBMIT_DOCUMENT;
    public static Property MINIMUN_EXECUTION_MONTHS;
    public static Property MAXIMUN_EXECUTION_MONTHS;
    public static Property CHARACTER_LIMIT_TITLE;
    public static Property LIMIT_GENERAL_OBJECTIVE;
    public static Property LIMIT_SPECIFIC_OBJECTIVE;
    public static Property CHARACTER_LIMIT_JUSTIFICATION;
    public final static Integer CHARACTER_LIMIT_FOR_TEXT_OF_SECTION = 65500;

    private EntityManager entityManager;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void initialize() {
        loadProperties();
    }

    public void loadProperties() {
        MAXIMUM_SUPERVISOR_WORKLOAD = entityManager.createQuery("SELECT p FROM Property p WHERE p.name = 'MAXIMUM_SUPERVISOR_WORKLOAD'", Property.class).getSingleResult();
        VALIDATION_PERCENTAGE_EXTENSION = entityManager.createQuery("SELECT p FROM Property p WHERE p.name = 'VALIDATION_PERCENTAGE_EXTENSION'", Property.class).getSingleResult();
        REVIEW_TIME_DAYS = entityManager.createQuery("SELECT p FROM Property p WHERE p.name = 'REVIEW_TIME_DAYS'", Property.class).getSingleResult();
        TIME_OF_PROCESS_WITHOUT_MOVEMENT = entityManager.createQuery("SELECT p FROM Property p WHERE p.name = 'TIME_OF_PROCESS_WITHOUT_MOVEMENT'", Property.class).getSingleResult();
        DEADLINE_TO_SUBMIT_DOCUMENT = entityManager.createQuery("SELECT p FROM Property p WHERE p.name = 'DEADLINE_TO_SUBMIT_DOCUMENT'", Property.class).getSingleResult();
        MINIMUN_EXECUTION_MONTHS = entityManager.createQuery("SELECT p FROM Property p WHERE p.name = 'MINIMUN_EXECUTION_MONTHS'", Property.class).getSingleResult();
        MAXIMUN_EXECUTION_MONTHS = entityManager.createQuery("SELECT p FROM Property p WHERE p.name = 'MAXIMUN_EXECUTION_MONTHS'", Property.class).getSingleResult();
        CHARACTER_LIMIT_TITLE = entityManager.createQuery("SELECT p FROM Property p WHERE p.name = 'CHARACTER_LIMIT_TITLE'", Property.class).getSingleResult();
        LIMIT_GENERAL_OBJECTIVE = entityManager.createQuery("SELECT p FROM Property p WHERE p.name = 'LIMIT_GENERAL_OBJECTIVE'", Property.class).getSingleResult();
        LIMIT_SPECIFIC_OBJECTIVE = entityManager.createQuery("SELECT p FROM Property p WHERE p.name = 'LIMIT_SPECIFIC_OBJECTIVE'", Property.class).getSingleResult();
        CHARACTER_LIMIT_JUSTIFICATION = entityManager.createQuery("SELECT p FROM Property p WHERE p.name = 'CHARACTER_LIMIT_JUSTIFICATION'", Property.class).getSingleResult();
    }
}
