package gt.edu.usac.cunoc.ingenieria.eps.logout.view;

import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class LogoutView {

    @Inject
    private HttpServletRequest request;
    
    @Inject
    private ExternalContext externalContext;
    
    public String logout()
            throws ServletException {
        request.logout();
        request.getSession().invalidate();
        return "login";
    }
    
    public void goToMyProfile() throws IOException{
        externalContext.redirect(externalContext.getRequestContextPath() + "/user/userProfile.xhtml");
    }
}
