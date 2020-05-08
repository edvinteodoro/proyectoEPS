package gt.edu.usac.cunoc.ingenieria.eps.project.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ProjectFacadeLocal {
    
    public Project updateProject(Project project, List<Objectives> generalObjective, List<Objectives> specificObjective) throws MandatoryException, LimitException;

    public Project createProject(Project project, List<Objectives> generalObjective, List<Objectives> specificObjective, Process process) throws MandatoryException, LimitException;
            
    public Project getProject(Integer projectId);
//    
//    public Boolean createPDF(Project project);
}
