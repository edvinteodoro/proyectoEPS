/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.io.IOException;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author crystian
 */
@Path("/")
@Produces("application/json")
public class ProjectReview {

    @EJB
    private ProjectFacadeLocal projectFacade;

    @EJB
    private ProcessFacadeLocal processFacade;

    @GET
    @Path("/{projectId}")
    public Response getProject(@PathParam("projectId") Integer projectId) {
        Project project = projectFacade.getProject(projectId);
        if (project != null) {
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(Response.Status.NOT_FOUND + ": Project not found")
                .build();
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
}
