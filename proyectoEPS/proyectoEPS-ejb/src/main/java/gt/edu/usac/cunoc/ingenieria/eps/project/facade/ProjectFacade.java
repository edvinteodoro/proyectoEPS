package gt.edu.usac.cunoc.ingenieria.eps.project.facade;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection;
import gt.edu.usac.cunoc.ingenieria.eps.project.repository.CorrectionRepository;
import gt.edu.usac.cunoc.ingenieria.eps.project.repository.ProjectRepository;
import gt.edu.usac.cunoc.ingenieria.eps.project.service.CorrectionService;
import gt.edu.usac.cunoc.ingenieria.eps.project.service.ProjectService;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import java.io.IOException;
import java.io.InputStream;
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
    public Project createProject(Project project) throws MandatoryException, LimitException {
        return projectService.createProject(project);
    }
    
    @Override
    public Project updateProject(Project project) throws MandatoryException, LimitException {
        return projectService.update(project);
    }

    @Override
    public Project getProject(Process process) {
        return projectRepository.getProjectByProcess(process);
    }

    @Override
    public InputStream createPDF(Project project, UserCareer userCareer) throws IOException, ValidationException, MandatoryException, LimitException{
        return projectService.createPDF(project, userCareer);
    }

    @Override
    public Correction createCorrection(Correction correction) throws UserException, MandatoryException {
        return correctionService.createCorrection(correction);
    }

    @Override
    public List<Correction> getCorrections(TypeCorrection typeCorrection, Integer projectID, Integer section,Boolean status) {
        return correctionRepository.getCorrections(typeCorrection, projectID, section, status);
    }

    @Override
    public void returnCorrections(Project project) throws MandatoryException {
       correctionService.returnCorrections(project);
    }

    @Override
    public void searchUnnotifiedCorrections(Project project) throws ValidationException{
        correctionRepository.searchUnnotifiedCorrection(project);
    }
    
}
