package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.projectDto.SectionDto;
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
public class SectionResource {
    @EJB
    private ProjectFacadeLocal projectFacade;
    
    @GET
    public Response getSections(@PathParam("projectId") Integer projectId) {
        try {
            Project project = projectFacade.getProject(projectId);
            List<SectionDto> seccitons=project.getSections().stream().map(section -> new SectionDto(section)).collect(Collectors.toList());
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
