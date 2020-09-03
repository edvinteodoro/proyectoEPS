package gt.edu.usac.cunoc.ingenieria.eps.journal;

import gt.edu.usac.cunoc.ingenieria.eps.journal.facade.JournalLogFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.journalLogDto.CommentaryDto;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
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
public class CommentaryResource {

    @EJB
    JournalLogFacadeLocal journalFacade;

    @EJB
    UserFacadeLocal userFacade;
    
    private LocalDate date = LocalDate.now();

    @GET
    public Response getComments(@PathParam("journalId") Integer journalId) {
        try {
            List<Commentary> comments = journalFacade.getCommentariesJournal(journalId);
            return Response
                    .status(Response.Status.OK)
                    .entity(comments)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @POST
    public Response addCommentary(CommentaryDto comentary,
            @PathParam("journalId") Integer journalId,
             @PathParam("userId") String userId) {
        try {
            JournalLog journal = journalFacade.getJournalById(journalId);
            User user = userFacade.getUser(new User(userId)).get(0);
            Commentary commentary = new Commentary(comentary.getText(), this.date, journal, user);
            journal.addCommentary(commentary);
            journalFacade.updateJournal(journal);
            return Response
                    .status(Response.Status.OK)
                    .entity(commentary)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
}
