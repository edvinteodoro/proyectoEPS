package gt.edu.usac.cunoc.ingenieria.eps.user.view;

import gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class CreateUserView implements Serializable {

    @EJB
    private UserFacadeLocal userFacade;

    private List<Career> careers;
    private List<Career> selectedCareers;
    private List<Rol> rolUsers;
    private List<UserCareer> userCareers;

    private User user;

    private Boolean personalCodeFlag;
    private Boolean academicRegisterFlag;
    private Boolean careerSelectionFlag;

    @PostConstruct
    public void init() {
        rolUsers = userFacade.getAllRolUser();
        careers = userFacade.getAllCareer();
        verifyView();
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

    public Boolean getPersonalCodeFlag() {
        return personalCodeFlag;
    }

    public void setPersonalCodeFlag(Boolean personalCodeFlag) {
        this.personalCodeFlag = personalCodeFlag;
    }

    public Boolean getAcademicRegisterFlag() {
        return academicRegisterFlag;
    }

    public void setAcademicRegisterFlag(Boolean academicRegisterFlag) {
        this.academicRegisterFlag = academicRegisterFlag;
    }

    public Boolean getCareerSelectionFlag() {
        return careerSelectionFlag;
    }

    public void setCareerSelectionFlag(Boolean careerSelectionFlag) {
        this.careerSelectionFlag = careerSelectionFlag;
    }

    public void createUser() {
        try {
            for (int i = 0; i < getSelectedCareers().size(); i++) {
                getUserCareers().add(new UserCareer(getSelectedCareers().get(i), getUser()));
            }
            getUser().setUserCareers(getUserCareers());
            userFacade.createUser(getUser());
            MessageUtils.addSuccessMessage("Se ha registrado el usuario correctamente");
            cleanUser();
        } catch (Exception ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    public void verifyView() {
        if (getUser().getROLid() != null) {
            switch (getUser().getROLid().getName()) {
                case Constants.ESTUDIANTE:
                    personalCodeFlag = false;
                    academicRegisterFlag = true;
                    careerSelectionFlag = true;
                    break;
                case Constants.COORDINADOR_CARRERA:
                case Constants.REVISOR:
                case Constants.ASESOR:
                case Constants.SUPERVISOR_EPS:
                    personalCodeFlag = true;
                    academicRegisterFlag = false;
                    careerSelectionFlag = true;
                    break;
                default:
                    personalCodeFlag = true;
                    academicRegisterFlag = false;
                    careerSelectionFlag = false;
            }
        } else {
            personalCodeFlag = true;
            academicRegisterFlag = false;
            careerSelectionFlag = false;
        }
    }

    private void cleanUser() {
        user = null;
        getSelectedCareers().clear();
        getUserCareers().clear();
    }
}
