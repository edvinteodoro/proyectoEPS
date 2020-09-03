package gt.edu.usac.cunoc.ingenieria.eps.journal;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.journal.Commentary;
import gt.edu.usac.cunoc.ingenieria.eps.journal.JournalLog;
import gt.edu.usac.cunoc.ingenieria.eps.journal.facade.JournalLogFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.journalLogDto.CommentaryDto;
import gt.edu.usac.cunoc.ingenieria.eps.journalLogDto.JournalLogDto;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author teodoro
 */
@Produces("application/json")
public class JournalResource {

    @EJB
    private ProcessFacadeLocal processFacade;

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private JournalLogFacadeLocal journalFacade;
    
    @Inject
    private CommentaryResource commentaryResource;

    @GET
    public Response getJournals(@PathParam("processId") Integer processId) {
        try {
            List<JournalLog> journalLogs = journalFacade.getJournal(processId);
            return Response
                    .status(Response.Status.OK)
                    .entity(journalLogs)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
    
    @Path("/{journalId}/comments")
    public CommentaryResource getCommentaryResource(){
        return commentaryResource;
    }
}
