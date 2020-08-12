package gt.edu.usac.cunoc.ingenieria.eps.project.facade;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ProjectFacadeLocal {

    public Project createProject(Project project) throws MandatoryException, LimitException;

    public Project updateProject(Project project) throws MandatoryException, LimitException; 

    public Project getProject(Process process);

    public Correction createCorrection(Correction correction) throws UserException;

    public List<Correction> getCorrections(TypeCorrection typeCorrection, Integer projectID, Integer section);

    public InputStream createPDF(Project project, UserCareer userCareer) throws IOException, ValidationException, MandatoryException, LimitException;
}
