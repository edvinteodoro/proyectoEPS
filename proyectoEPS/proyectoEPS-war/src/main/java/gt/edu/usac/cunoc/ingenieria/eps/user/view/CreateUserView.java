package gt.edu.usac.cunoc.ingenieria.eps.user.view;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class CreateUserView implements Serializable {

    @EJB
    private UserFacadeLocal userFacade;

    List<Career> careers;
    List<Career> selectedCareers;
    List<Rol> rolUsers;
    List<UserCareer> userCareers;
    User user;

    @PostConstruct
    public void init() {
        rolUsers = userFacade.getAllRolUser();
        careers = userFacade.getAllCareer();
    }

    public UserFacadeLocal getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacadeLocal userFacade) {
        this.userFacade = userFacade;
    }

    public List<Career> getCareers() {
        return careers;
    }

    public void setCareers(List<Career> careers) {
        this.careers = careers;
    }

    public List<Rol> getRolUsers() {
        return rolUsers;
    }

    public void setRolUsers(List<Rol> rolUsers) {
        this.rolUsers = rolUsers;
    }

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Career> getSelectedCareers() {
        if (selectedCareers == null) {
            return selectedCareers = new ArrayList<>();
        } else {
            return selectedCareers;
        }
    }

    public void setSelectedCareers(List<Career> selectedCareers) {
        this.selectedCareers = selectedCareers;
    }

    public List<UserCareer> getUserCareers() {
        if (userCareers == null) {
            return userCareers = new ArrayList<>();
        } else {
            return userCareers;
        }
    }

    public void setUserCareers(List<UserCareer> userCareers) {
        this.userCareers = userCareers;
    }

    public void createUser() {
        try {
            for (int i = 0; i < getSelectedCareers().size(); i++) {
                getUserCareers().add(new UserCareer(getSelectedCareers().get(i), getUser()));
            }
            getUser().setState(Boolean.TRUE); 
            getUser().setUserCareers(getUserCareers());
            userFacade.createUser(getUser());
            MessageUtils.addSuccessMessage("Se ha creado registrado el usuario");
            cleanUser();
        } catch (Exception ex) {

        }
    }
    
    private void cleanUser(){
        user=null;
        getSelectedCareers().clear();
        getUserCareers().clear();
    }
}
