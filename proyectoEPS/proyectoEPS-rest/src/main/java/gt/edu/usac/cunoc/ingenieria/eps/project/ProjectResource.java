package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.correction.CorrectionResource;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.projectDto.ProjectDto;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.processDto.ProcessDto;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author teodoro
 */
@Produces("application/json")
public class ProjectResource {

    @EJB
    private ProcessFacadeLocal processFacadeLocal;
    
    @Inject
    CorrectionResource correctionResource;

    @GET
    public Response getProject(@PathParam("processId") Integer processId) {
        try {
            Process process = processFacadeLocal.getProcess(new Process(processId)).get(0);
            ProjectDto project = new ProjectDto(process.getProject());
            return Response
                    .status(Response.Status.OK)
                    .entity(project)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
    
    @Path("/{projectId}/corrections")
    public CorrectionResource getCorrectionResource(){
        return correctionResource;
    }
    
    
    
    
}
