/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.user;

import gt.edu.usac.cunoc.ingenieria.eps.process.ProcessResource;
import gt.edu.usac.cunoc.ingenieria.eps.process.ProcessReviewResource;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import javax.ejb.EJB;
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

    @EJB
    private UserFacadeLocal userFacade;
    
    @EJB
    private ProcessFacadeLocal processFacade;
    
    @Inject 
    ProcessResource processResource;
    
    @Path("/{userId}/processes")
    public ProcessResource getProcessResounrce(){
        return processResource;
    }

    @Inject
    ProcessReviewResource processReviewResource;
    
    @Path("/{userId}/processes")
    public ProcessReviewResource getProcess(){
        return processReviewResource;
    }
    
}
