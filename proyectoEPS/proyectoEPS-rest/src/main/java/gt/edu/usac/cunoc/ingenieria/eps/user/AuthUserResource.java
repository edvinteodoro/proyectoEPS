package gt.edu.usac.cunoc.ingenieria.eps.user;

import gt.edu.usac.cunoc.ingenieria.eps.processDto.ProcessDto;
import gt.edu.usac.cunoc.ingenieria.eps.security.JWTAuthenticationMechanism;
import gt.edu.usac.cunoc.ingenieria.eps.security.TokenProvider;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.userDto.UserDto;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 *
 * @author teodoro
 */
@Produces("application/json")
public class AuthUserResource {

    private static final Logger LOGGER = Logger.getLogger(AuthUserResource.class.getName());

    @Inject
    private SecurityContext securityContext;

    @EJB
    private UserFacadeLocal userFacade;
    
    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Inject
    private TokenProvider tokenProvider;

    @GET
    public Response getUser(@PathParam("userId") String userId) {
        try {
            User user = userFacade.getUser(new User(userId)).get(0);
            UserDto userDto = new UserDto(user);
            return Response
                    .status(Response.Status.OK)
                    .entity(userDto)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @GET
    @Path("/login")
    public Response login(@QueryParam("userId") String userId, @QueryParam("password") String password) {

        LOGGER.log(Level.INFO, "login");
        if (securityContext.getCallerPrincipal() != null) {
            JsonObject result = Json.createObjectBuilder()
                    .add("user", securityContext.getCallerPrincipal().getName())
                    .build();
            return Response.ok(result).build();
        }
        return Response.status(UNAUTHORIZED).build();
    }
}
