package gt.edu.usac.cunoc.ingenieria.eps.correction;

import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.projectDto.CorrectionDto;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
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
public class CorrectionResource {

    @EJB
    ProjectFacadeLocal projectFacade;

    @GET
    public Response getCorrections(@PathParam("projectId") Integer projectId) {
        try {
            Project project = projectFacade.getProject(projectId);
            List<CorrectionDto> corrections = project.getCorrections().stream().map(correction -> new CorrectionDto(correction))
                    .collect(Collectors.toList());
            return Response
                    .status(Response.Status.OK)
                    .entity(corrections)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
}
