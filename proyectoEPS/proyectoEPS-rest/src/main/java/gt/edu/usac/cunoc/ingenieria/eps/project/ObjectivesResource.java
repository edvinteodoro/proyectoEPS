package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.projectDto.ObjectivesDto;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author teodoro
 */

@Produces("application/json")
public class ObjectivesResource {
    @EJB
    private ProjectFacadeLocal projectFacade;
    
    @GET
    public Response getObjectives(@PathParam("projectId") Integer projectId) {
        try {
            Project project = projectFacade.getProject(projectId);
            List<ObjectivesDto> seccitons=project.getObjectives().stream().map(objectives -> new ObjectivesDto(objectives)).collect(Collectors.toList());
            return Response
                    .status(Response.Status.OK)
                    .entity(seccitons)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
}
