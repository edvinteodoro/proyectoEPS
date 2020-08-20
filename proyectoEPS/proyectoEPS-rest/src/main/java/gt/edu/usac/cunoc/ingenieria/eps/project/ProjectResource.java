/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.projectDto.ProjectDto;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author teodoro
 */
@Path("/project")
@Stateless
@Produces("application/json")
public class ProjectResource {
    @EJB
    private ProjectFacadeLocal projectFacade;

    @GET
    @Path("get/{id}")
    public ProjectDto getProject(@PathParam("id") Integer id) {
        Project project = projectFacade.getProject(id);
        return new ProjectDto(project);
    }
}
