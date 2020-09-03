/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.project.ProjectReview;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author crystian
 */
@Path("/")
@Produces("application/json")
public class ProcessReviewResource {
    
    @Inject
    ProjectReview projectReview;
    
    @Path("/{processId}/project")
    public ProjectReview getProject(){
        return projectReview;
    }

}
