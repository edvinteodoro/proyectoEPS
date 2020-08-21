package gt.edu.usac.cunoc.ingenieria.eps.process;

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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author teodoro
 */
@Path("/journal")
@Stateless
@Produces("application/json")
public class JournalResource {

    @EJB
    private ProcessFacadeLocal processFacade;

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private JournalLogFacadeLocal journalFacade;

    private LocalDate date = LocalDate.now();

    @GET
    @Path("list/{processId}")
    public List<JournalLog> getJournals(@PathParam("processId") Integer processId) {
        return journalFacade.getJournal(processId);
    }

    @POST
    @Path("addComment/{userId}/{journalId}")
    public Boolean commentJournal(@PathParam("processId") Integer journalId, @PathParam("userId") String userId, CommentaryDto comentary) throws UserException {
        JournalLog journal = journalFacade.getJournalById(journalId);
        User user = userFacade.getUser(new User(userId)).get(0);
        Commentary commentary = new Commentary(comentary.getText(), this.date, journal, user);
        journal.addCommentary(commentary);
        journalFacade.updateJournal(journal);
        return Boolean.TRUE;
    }

}
