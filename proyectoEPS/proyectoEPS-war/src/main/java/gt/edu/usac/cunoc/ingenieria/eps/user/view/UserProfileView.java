package gt.edu.usac.cunoc.ingenieria.eps.user.view;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import static javax.security.enterprise.AuthenticationStatus.SUCCESS;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.PrimeFaces;

/**
 *
 * @author angelrg
 */
@Named
@ViewScoped
public class UserProfileView implements Serializable {

    @EJB
    private UserFacadeLocal userFacade;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ExternalContext externalContext;

    User user;

    String password;
    String newPassword;

    @PostConstruct
    public void init() {
        getInitialData();
    }

    public void getInitialData() {
        try {
            user = userFacade.getAuthenticatedUser().get(0);
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    public void saveChanges() {
        try {
            userFacade.updateUser(user);
            MessageUtils.addSuccessMessage("Cambios realizados exitosamente");
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    public void changePassword(final String modalIdToClose) {
        if (user.getUserId() != null && getPassword() != null) {
            Credential credential = new UsernamePasswordCredential(user.getUserId(), new Password(password));
            AuthenticationStatus status = securityContext.authenticate(
                    getRequest(),
                    getResponse(),
                    AuthenticationParameters.withParams().credential(credential));

            if (status == SUCCESS) {
                try {
                    user.setPassword(getNewPassword());
                    userFacade.updateUser(user);
                    MessageUtils.addSuccessMessage("Se actualizo la contraseña");
                } catch (UserException e) {
                    MessageUtils.addErrorMessage(e.getMessage());
                }
            } else {
                cleanPass();
                MessageUtils.addErrorMessage("Constraseña Incorrecta");
            }
        }
        PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
    }

    public void cleanPass() {
        setPassword(null);
        setNewPassword(null);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) externalContext.getRequest();
    }

    private HttpServletResponse getResponse() {
        return (HttpServletResponse) externalContext.getResponse();
    }
}
