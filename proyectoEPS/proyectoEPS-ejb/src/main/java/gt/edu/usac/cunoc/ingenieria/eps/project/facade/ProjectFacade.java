package gt.edu.usac.cunoc.ingenieria.eps.project.facade;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.configuration.repository.PropertyRepository;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection;
import gt.edu.usac.cunoc.ingenieria.eps.project.repository.CorrectionRepository;
import gt.edu.usac.cunoc.ingenieria.eps.project.repository.ProjectRepository;
import gt.edu.usac.cunoc.ingenieria.eps.project.service.CorrectionService;
import gt.edu.usac.cunoc.ingenieria.eps.project.service.ProjectService;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import java.io.IOException;
import java.io.InputStream;
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

    @EJB
    private CorrectionService correctionService;

    @EJB
    private CorrectionRepository correctionRepository;

    @Override
    public Project updateProject(Project project, List<Objectives> generalObjective, List<Objectives> specificObjective) throws MandatoryException, LimitException {
        verifyProject(project, generalObjective, specificObjective);
        return projectService.update(project);
    }

    @Override
    public Project getProject(Integer projectId) {
        return projectRepository.getProjects(projectId, null, null).get(0);
    }

    @Override
    public Project createProject(List<Objectives> generalObjective, List<Objectives> specificObjective, Process process) throws MandatoryException, LimitException {
        verifyProject(process.getProject(), generalObjective, specificObjective);
        return projectService.create(process);
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
            generalObjective.get(i).setType(Objectives.GENERAL_OBJETICVE);
            generalObjective.get(i).setLastModificationDate(LocalDate.now());
            project.addObjective(generalObjective.get(i));
        }
        for (int i = 0; i < specificObjective.size(); i++) {
            specificObjective.get(i).setType(Objectives.SPECIFIC_OBJECTIVE);
            specificObjective.get(i).setLastModificationDate(LocalDate.now());
            project.addObjective(specificObjective.get(i));
        }
    }

    @Override
    public InputStream createPDF(Project project, UserCareer userCareer) throws IOException {
        return projectService.createPDF(project, userCareer);
    }

    @Override
    public Project updateProject(Project project) throws MandatoryException, LimitException {
        return projectService.update(project);
    }

    @Override
    public Correction createCorrection(Correction correction) throws UserException {
        return correctionService.createCorrection(correction);
    }

    public List<Correction> getCorrections(TypeCorrection typeCorrection, Integer projectID, Integer section) {
        return correctionRepository.getCorrections(typeCorrection, projectID, section);
    }
}
