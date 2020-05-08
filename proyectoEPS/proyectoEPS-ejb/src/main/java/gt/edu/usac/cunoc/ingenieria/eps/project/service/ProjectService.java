package gt.edu.usac.cunoc.ingenieria.eps.project.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.configuration.repository.PropertyRepository;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ProjectService {

    private EntityManager entityManager;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ProjectService() {
    }

    public Project update(Project project) throws MandatoryException, LimitException {
        verifyProject(project);
        project.setState(Project.ACTIVE);
        project.setLimitReceptionDate(PropertyRepository.GENERAL_LIMIT_RECEPTION_DATE.getValueDate());
        entityManager.merge(project);
        return project;
    }

    public Project create(Project project, Process process) throws MandatoryException, LimitException {
        verifyProject(project);
        project.setState(Project.ACTIVE);
        project.setLimitReceptionDate(PropertyRepository.GENERAL_LIMIT_RECEPTION_DATE.getValueDate());
        project.setpROCESSid(process);
        process.setProject(project);
        entityManager.merge(process);
        return project;
    }

    private void verifyProject(Project project) throws MandatoryException, LimitException {
        if (project.getTitle() == null) {
            throw new MandatoryException("Atributo Titulo Obligatorio");
        }
        if (project.getTitle().length() > PropertyRepository.CHARACTER_LIMIT_TITLE.getValueInt()) {
            throw new LimitException("Titulo fuera de limites, Número de Caracteres Maximo " + PropertyRepository.CHARACTER_LIMIT_TITLE.getValueInt());
        }
        if (project.getSchedule() == null) {
            throw new MandatoryException("Archivo Calendario Obligatorio");
        }
        if (project.getInvestmentPlan() == null) {
            throw new MandatoryException("Archivo Plan de Inversiones Obligatorio");
        }
    }
    
//    public Boolean createPDF(Project project){
//        
//    }
}
