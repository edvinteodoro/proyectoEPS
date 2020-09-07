package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.correction.CorrectionResource;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.projectDto.ProjectDto;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author teodoro
 */
@Path("/")
@Produces("application/json")
public class ProjectResource {

    @EJB
    private ProcessFacadeLocal processFacade;

    @EJB
    private ProjectFacadeLocal projectFacade;

    @Inject
    private CorrectionResource correctionResource;

    @GET
    public Response getProject(@PathParam("processId") Integer processId) {
        try {
            Process process = processFacade.getProcess(new Process(processId)).get(0);
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
    
    @GET
    @Path("/{projectId}/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadProject(@PathParam("projectId") Integer projectId,
            @PathParam("processId") Integer processId) {
        try {
            Project project = projectFacade.getProject(projectId);
            UserCareer userCareer = processFacade.getProcess(new Process(processId)).get(0).getUserCareer();
            return Response
                    .ok()
                    .header("Content-Disposition", "attachment:filename=proyecto.pdf")
                    .entity(projectFacade.createPDF(project, userCareer))
                    .build();
        } catch (IOException ex) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Response.Status.INTERNAL_SERVER_ERROR + ": " + ex.getMessage())
                    .build();
        }
    }

    @Path("/{projectId}/corrections")
    public CorrectionResource getCorrectionResource() {
        return correctionResource;
    }

}
