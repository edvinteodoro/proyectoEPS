package gt.edu.usac.cunoc.ingenieria.eps.project.facade;

import gt.edu.usac.cunoc.ingenieria.eps.configuration.repository.PropertyRepository;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.project.repository.ProjectRepository;
import gt.edu.usac.cunoc.ingenieria.eps.project.service.ProjectService;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class ProjectFacade implements ProjectFacadeLocal {

    @EJB
    private ProjectService projectService;

    @EJB
    private ProjectRepository projectRepository;
    
    @Override
    public Project updateProject(Project project, List<Objectives> generalObjective, List<Objectives> specificObjective) throws MandatoryException, LimitException {
        verifyProject(project, generalObjective, specificObjective);
        return projectService.update(project);
    }

    @Override
    public Project getProject(Integer projectId) {
        return projectRepository.getProjects(projectId.intValue(), null, null).get(0);
    }

    @Override
    public Project createProject(Project project, List<Objectives> generalObjective, List<Objectives> specificObjective, Process process) throws MandatoryException, LimitException {
        verifyProject(project, generalObjective, specificObjective);
        return projectService.create(project, process);
    }

    private void verifyProject(Project project, List<Objectives> generalObjective, List<Objectives> specificObjective) throws MandatoryException, LimitException {
        if (generalObjective.size() > PropertyRepository.LIMIT_GENERAL_OBJECTIVE.getValueInt()) {
            throw new LimitException("Número Maximo de Objetivos Generales: " + PropertyRepository.LIMIT_GENERAL_OBJECTIVE.getValueInt());
        }
        if (specificObjective.size() > PropertyRepository.LIMIT_SPECIFIC_OBJECTIVE.getValueInt()) {
            throw new LimitException("Número Maximo de Objetivos Especificos: " + PropertyRepository.LIMIT_SPECIFIC_OBJECTIVE.getValueInt());
        }
        project.getObjectives().clear();
        for (int i = 0; i < generalObjective.size(); i++) {
            generalObjective.get(i).setState(Objectives.GENERAL_OBJETICVE);
            generalObjective.get(i).setLastModificationDate(LocalDate.now());
            project.addObjective(generalObjective.get(i));
        }
        for (int i = 0; i < specificObjective.size(); i++) {
            specificObjective.get(i).setState(Objectives.SPECIFIC_OBJECTIVE);
            specificObjective.get(i).setLastModificationDate(LocalDate.now());
            project.addObjective(specificObjective.get(i));
        }
    }
//
//    @Override
//    public Boolean createPDF(Project project) {
//        return projectService.createPDF(project);
//    }
}
