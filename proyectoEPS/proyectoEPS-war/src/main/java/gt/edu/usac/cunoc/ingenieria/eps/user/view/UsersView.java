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

@Named
@ViewScoped
public class UsersView implements Serializable {

    @EJB
    private UserFacadeLocal userFacade;

    List<User> users;
    List<Career> careers;

    User selectedUser;

    String userID;
    String DPI;
    String nombre;
    String apellido;
    
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
            if (users != null) {
                users.clear();
            }
            setUsers(userFacade.getUser(new User(userID, DPI, nombre, apellido, null, null, null, null)));
            MessageUtils.addSuccessMessage("Busqueda realizada");
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
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
    
    public void changeStatusUser(User user){
        try {
            user.setStatus(!user.getStatus()); 
            userFacade.updateUser(user);
            if (user.getStatus()){
                MessageUtils.addSuccessMessage("Usuario Activado");
            } else {
                MessageUtils.addSuccessMessage("Usuario Desactivado");
            }
        } catch (UserException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    public void cleanCriteria() {
        userID = null;
        DPI = null;
        nombre = null;
        apellido = null;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDPI() {
        return DPI;
    }

    public void setDPI(String DPI) {
        this.DPI = DPI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<Career> getCareers() {
        return careers;
    }

    public void setCareers(List<Career> careers) {
        this.careers = careers;
    }

}
