package gt.edu.usac.cunoc.ingenieria.eps.user.view;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

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

    User selectedUser = new User();

    User searchCriteria = new User();

    @PostConstruct
    public void init() {
        findUsers();
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

}
