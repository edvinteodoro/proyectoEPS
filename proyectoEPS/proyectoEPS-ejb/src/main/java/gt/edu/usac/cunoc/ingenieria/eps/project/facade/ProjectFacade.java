
package gt.edu.usac.cunoc.ingenieria.eps.project.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.service.ProjectService;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class ProjectFacade implements ProjectFacadeLocal{

    @EJB
    private ProjectService projectService;
                    
    @Override
    public Project createProject(Project project) throws MandatoryException{
        return projectService.create(project);
    }
    
}
