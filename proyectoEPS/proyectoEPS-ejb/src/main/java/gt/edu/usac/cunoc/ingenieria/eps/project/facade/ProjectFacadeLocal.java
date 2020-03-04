
package gt.edu.usac.cunoc.ingenieria.eps.project.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import javax.ejb.Local;

@Local
public interface ProjectFacadeLocal {
    
    public Project createProject(Project project) throws MandatoryException;

}
