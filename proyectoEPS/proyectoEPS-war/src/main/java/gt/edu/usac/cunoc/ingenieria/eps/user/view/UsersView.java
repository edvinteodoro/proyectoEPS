package gt.edu.usac.cunoc.ingenieria.eps.user.view;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author angelrg
 */
@Named
@ViewScoped
public class UsersView implements Serializable {

    @EJB
    private UserFacadeLocal userFacade;

    List<User> users;
    List<Career> careers;

    User selectedUser = new User();

    User searchCriteria = new User();

    @PostConstruct
    public void init() {
        findUsers();
        careers = userFacade.getAllCareer();
    }

    /**
     * find all users, and use searchCriteria.
     */
    public void findUsers() {
        try {
            setUsers(userFacade.getUser(searchCriteria));
        } catch (UserException e) {
            MessageUtils.addSuccessMessage(e.getMessage());
        }
    }

    /**
     * Save modifies did to the user
     *
     * @param modalIdToClose
     */
    public void saveChanges(final String modalIdToClose) {
        if (selectedUser.getUserId() != null) {
            try {
                userFacade.updateUser(selectedUser);
                MessageUtils.addSuccessMessage("Se actualizo el usuario");
            } catch (UserException ex) {
                MessageUtils.addErrorMessage(ex.getMessage());
            }

            cleanSelectedUser();
            PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
        }
    }

    public void resetPassword(final String modalIdToClose) {
        if (selectedUser.getUserId() != null) {
            try {
                userFacade.resetPassword(selectedUser);
                MessageUtils.addSuccessMessage("Se actualizo la contraseña del usuario");
            } catch (UserException ex) {
                MessageUtils.addErrorMessage(ex.getMessage());
            }
        }
        cleanSelectedUser();
        PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
    }

    public void cleanCriteria() {
        this.searchCriteria = new User();
    }

    public void cleanSelectedUser() {
        this.selectedUser = new User();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getSelectedUser() {
        if (selectedUser == null) {
            selectedUser = new User();
        }
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public User getSearchCriteria() {
        if (searchCriteria == null) {
            searchCriteria = new User();
        }
        return searchCriteria;
    }

    public void setSearchCriteria(User searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public List<Career> getCareers() {
        return careers;
    }

    public void setCareers(List<Career> careers) {
        this.careers = careers;
    }

}
