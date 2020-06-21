package gt.edu.usac.cunoc.ingenieria.eps.login.view;

import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_CARRERA;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_EPS;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ESTUDIANTE;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SECRETARIA_EPS;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SUPERVISOR_EPS;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.PrimeFaces;

@Named
@ViewScoped
public class LoginView implements Serializable {

    @EJB
    private UserFacadeLocal userFacade;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    private String userId;
    private String password;
    private String userIDReset;
    private String email;

    @PostConstruct
    public void init() {
    }

    public void login() throws IOException, UserException {
        Credential credential = new UsernamePasswordCredential(userId, new Password(password));
        AuthenticationStatus status = securityContext.authenticate(
                getRequest(),
                getResponse(),
                AuthenticationParameters.withParams().credential(credential));
        switch (status) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                MessageUtils.addErrorMessage("Usuario no encontrado");
                break;
            case SUCCESS:
                redirectToIndex();
            case NOT_DONE:
        }
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) externalContext.getRequest();
    }

    private HttpServletResponse getResponse() {
        return (HttpServletResponse) externalContext.getResponse();
    }

    private void redirectToIndex() throws IOException, UserException {
        User currentUser = userFacade.getAuthenticatedUser().get(0);
        switch (currentUser.getROLid().getName()) {
            case COORDINADOR_CARRERA:
                externalContext.redirect(externalContext.getRequestContextPath() + "/process/processes.xhtml");
                break;
            case ESTUDIANTE:
                externalContext.redirect(externalContext.getRequestContextPath() + "/process/processes.xhtml");
                break;
            case SECRETARIA_EPS:
                externalContext.redirect(externalContext.getRequestContextPath() + "/user/createUser.xhtml");
                break;
            case COORDINADOR_EPS:
                externalContext.redirect(externalContext.getRequestContextPath() + "/user/editEPSCommittee.xhtml");
                break;
            case SUPERVISOR_EPS:
                externalContext.redirect(externalContext.getRequestContextPath() + "/committeeEPS/processesCommitteeEPS.xhtml");
                break;
            default:
        }
    }

    public void resetPassword(final String modalIdToClose) {
        try {
            userFacade.resetPassword(userIDReset, email);
            MessageUtils.addSuccessMessage("Exito, verifique su correo");
            clean();
            PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserIDReset() {
        return userIDReset;
    }

    public void setUserIDReset(String userIDReset) {
        this.userIDReset = userIDReset;
    }

    public void clean() {
        setUserId("");
        setPassword("");
        setUserIDReset("");
        setEmail("");
    }

}
