/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.config.Constans;
import gt.edu.usac.cunoc.ingenieria.eps.journal.JournalResource;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.processDto.ProcessDto;
import gt.edu.usac.cunoc.ingenieria.eps.project.ProjectResource;
import gt.edu.usac.cunoc.ingenieria.eps.requeriment.RequerimentResource;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCoordinator;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCommitteeEPSFacade;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailFacade;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author teodoro
 */
@Path("/")
@Produces("application/json")
public class ProcessResource {

    @Inject
    private ProjectResource projectResource;

    @Inject
    private JournalResource journalResource;
    
    @Inject
    private RequerimentResource requerimentResource;

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private ProcessFacadeLocal processFacade;

    @EJB
    private TailFacade tailCoordinatorFacade;

    @EJB
    private TailCommitteeEPSFacade tailCommitteEpsFacade;

    @GET
    @Path("/{processId}")
    public Response getProcessById(@PathParam("userId") String userId,
            @PathParam("processId") Integer processId) {
        try {
            User user = userFacade.getUser(new User(userId)).get(0);
            List<ProcessDto> processes = processFacade.getProcess(new Process(processId))
                    .stream()
                    .map(process -> new ProcessDto(process))
                    .collect(Collectors.toList());
            return Response
                    .status(Response.Status.OK)
                    .entity(processes)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @GET
    @Path("/assigned")
    public Response getAssignedCoordinator(@PathParam("userId") String coordinatorId) {
        try {
            User user = userFacade.getUser(new User(coordinatorId)).get(0);
            List<ProcessDto> processes = tailCoordinatorFacade.getProcessByCoordinator(user).stream().map(process -> new ProcessDto(process)).collect(Collectors.toList());
            return Response
                    .status(Response.Status.OK)
                    .entity(processes)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @PATCH
    @Path("/{processId}/reject")
    public Response rejectProcess(@PathParam("userId") String userId,
            @PathParam("processId") Integer processId) {
        try {
            User user = userFacade.getUser(new User(userId)).get(0);
            Process process = processFacade.getProcess(new Process(processId)).get(0);
            TailCoordinator tailCoordinator = tailCoordinatorFacade.getTailCoordianteor(process);
            processFacade.rejectProcess(tailCoordinator, Constans.TL_REJECT_PROCESS_BY_COORDINATOR, Constans.MSG_REJECT_PROCESS_BY_COORDINATOR);
            tailCoordinatorFacade.deleteTailCoordinatod(process);
            process.setState(StateProcess.RECHAZADO);
            process.setApprovedCareerCoordinator(Boolean.FALSE);
            processFacade.updateProcess(process);
            return Response
                    .status(Response.Status.OK)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @PATCH
    @Path("/{processId}/accept")
    public Response acceptProcess(@PathParam("userId") String userId,
            @PathParam("processId") Integer processId) {
        try {
            User user = userFacade.getUser(new User(userId)).get(0);
            Process process = processFacade.getProcess(new Process(processId)).get(0);
            processFacade.rejectProcess(tailCoordinatorFacade.getTailCoordianteor(process), Constans.TL_ACCEPT_PROCESS_BY_COORDINATOR, Constans.MSG_ACCEPT_PROCESS_BY_COORDINATOR);
            tailCoordinatorFacade.deleteTailCoordinatod(process);
            process.setState(StateProcess.REVISION);
            process.setApprovedCareerCoordinator(true);
            processFacade.assignEPSSUpervisorToProcess(process.getUserCareer().getCAREERcodigo(), process);
            processFacade.updateProcess(process);
            tailCommitteEpsFacade.createTailCommiteeEPS(process);
            return Response
                    .status(Response.Status.OK)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @Path("/{processId}/projects")
    public ProjectResource getProjectResource() {
        return projectResource;
    }

    @Path("/{projectId}/journals")
    public JournalResource getJournalResource() {
        return journalResource;
    }
    
    @Path("/{projectId}/requeriments")
    public RequerimentResource getRequerimentResoruce(){
        return requerimentResource;
    } 
}
