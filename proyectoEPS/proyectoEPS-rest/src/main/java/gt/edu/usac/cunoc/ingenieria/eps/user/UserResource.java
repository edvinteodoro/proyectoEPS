/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.user;

import gt.edu.usac.cunoc.ingenieria.eps.process.ProcessReviewResource;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author crystian
 */
@Path("/users")
@Produces("application/json")
public class UserResource {
    
    @Inject
    ProcessReviewResource processReviewResource;
    
    @Path("/{userId}/processes")
    public ProcessReviewResource getProcess(){
        return processReviewResource;
    }
    
}
