package gt.edu.usac.cunoc.ingenieria.eps.requeriment;

import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import javax.ejb.EJB;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.projectDto.RequerimentDto;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author teodoro
 */
@Produces("application/json")
public class RequerimentResource {

    @EJB
    private ProcessFacadeLocal processFacade;

    @GET
    public Response getRequeriment(@PathParam("processId") Integer processId) {
        try {
            Process process = processFacade.getProcess(new gt.edu.usac.cunoc.ingenieria.eps.process.Process(processId)).get(0);
            RequerimentDto requeriment = new RequerimentDto(process.getRequeriment());
            return Response
                    .status(Response.Status.OK)
                    .entity(requeriment)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }

    }
}
