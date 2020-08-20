/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.config.Constans;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.processDto.ProcessDto;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCoordinator;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailFacade;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import java.util.List;
import java.util.stream.Collectors;
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

@Path("/process")
@Stateless
@Produces("application/json")
public class ProcessResource {
    @EJB
    private ProcessFacadeLocal processFacade;
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private TailFacade tailCoordinatorFacade; 
    
    
    @GET
    @Path("student/list/{userId}")
    public List<ProcessDto> getStudentProjects(@PathParam("userId") String userId) {
        try {
            User user = userFacade.getUser(new User(userId)).get(0);
            List<ProcessDto> processes=processFacade.getProcessUser(user).stream().map(process -> new ProcessDto(process)).collect(Collectors.toList());
            return processes;
        } catch (Exception e) {
            return null;
        }
    }
    
    @GET
    @Path("coordinator/list/{coordinatorId}")
    public List<ProcessDto> getCoordinatorProjects(@PathParam("coordinatorId") String coordinatorId) {
        try {
            User user = userFacade.getUser(new User(coordinatorId)).get(0);
            List<ProcessDto> processes=tailCoordinatorFacade.getProcessByCoordinator(user).stream().map(process -> new ProcessDto(process)).collect(Collectors.toList());
            return processes;
        } catch (Exception e) {
            return null;
        }
    }
    
    @GET
    @Path("reject/{idCoordinator}/{idProcess}")
    public Boolean rejectProcess(@PathParam("idCoordinator") Integer idCoordinator,@PathParam("idCoordinator") Integer idProcess) {
        try {
            Process process=processFacade.getProcess(new Process(idProcess)).get(0);
            TailCoordinator tailCoordinator=tailCoordinatorFacade.getTailCoordianteor(process);
            processFacade.rejectProcess(tailCoordinator, Constans.TL_REJECT_PROCESS_BY_COORDINATOR, Constans.MSG_REJECT_PROCESS_BY_COORDINATOR);
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
