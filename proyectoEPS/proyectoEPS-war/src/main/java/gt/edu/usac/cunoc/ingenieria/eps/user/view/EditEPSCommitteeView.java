package gt.edu.usac.cunoc.ingenieria.eps.user.view;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author angelrg
 */
@Named
@ViewScoped
public class EditEPSCommitteeView implements Serializable {

    @EJB
    private UserFacadeLocal userFacade;

    List<User> users;

    @PostConstruct
    public void init() {
        try {
            users = userFacade.getEPSCommitteeUser(null);
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    public void updateCommitteeState(User userSelected) {
        try {
            if (!userSelected.getEpsCommittee()) {
                userSelected.setEpsCommittee(true);
                userFacade.updateUser(userSelected);
                MessageUtils.addSuccessMessage("Agregado al Comite");
            } else {
                userSelected.setEpsCommittee(false);
                userFacade.updateUser(userSelected);
                MessageUtils.addWarningMessage("Eliminado del Comite");
            }
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
