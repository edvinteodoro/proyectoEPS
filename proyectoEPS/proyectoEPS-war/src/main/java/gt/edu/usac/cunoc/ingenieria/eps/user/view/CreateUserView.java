package gt.edu.usac.cunoc.ingenieria.eps.user.view;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ASESOR;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_CARRERA;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_EPS;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.DIRECTOR;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.REVISOR;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SECRETARIA_COORDINACION;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SECRETARIA_EPS;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SUPERVISOR_EMPRESA;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SUPERVISOR_EPS;
import gt.edu.usac.cunoc.ingenieria.eps.exception.HttpClientException;
import gt.edu.usac.cunoc.ingenieria.eps.thirdparty.studentdata.StudentData;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

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
    private User loginUser;

    private Boolean personalCodeFlag;
    private Boolean academicRegisterFlag;
    private Boolean careerSelectionFlag;
    private Boolean personalResumeFlag;
    private Boolean createCompanySupervisorFlag;

    private Boolean searchStudent;

    private StreamedContent personalResumeStream;
    private UploadedFile personalResume;
    private String personalResumeFileName = "";

    @PostConstruct
    public void init() {
        try {
            loginUser = userFacade.getAuthenticatedUser().get(0);
            rolUsers = userFacade.getAllRolUser();
            switch (loginUser.getROLid().getName()) {
                case COORDINADOR_EPS:
                    eliminatedRolByName(DIRECTOR);
                    eliminatedRolByName(COORDINADOR_EPS);
                    break;
                case SUPERVISOR_EPS:
                    eliminatedRolByName(DIRECTOR);
                    eliminatedRolByName(COORDINADOR_EPS);
                    eliminatedRolByName(SECRETARIA_EPS);
                    eliminatedRolByName(COORDINADOR_CARRERA);
                    eliminatedRolByName(SECRETARIA_COORDINACION);
                    eliminatedRolByName(SUPERVISOR_EPS);
                    break;
                case SECRETARIA_EPS:
                    eliminatedRolByName(DIRECTOR);
                    eliminatedRolByName(COORDINADOR_EPS);
                    eliminatedRolByName(SECRETARIA_EPS);
                    eliminatedRolByName(COORDINADOR_CARRERA);
                    eliminatedRolByName(SECRETARIA_COORDINACION);
                    eliminatedRolByName(REVISOR);
                    eliminatedRolByName(ASESOR);
                    eliminatedRolByName(SUPERVISOR_EPS);
                    eliminatedRolByName(SUPERVISOR_EMPRESA);
                    break;
            }
            careers = userFacade.getAllCareer();
            verifyView();
        } catch (UserException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void eliminatedRolByName(String rolName) {
        boolean flagRemove = false;
        int indexRolUser = 0;
        for (int i = 0; i < rolUsers.size(); i++) {
            if (rolUsers.get(i).getName().equalsIgnoreCase(rolName)) {
                indexRolUser = i;
                flagRemove = true;
            }
        }
        if (flagRemove) {
            rolUsers.remove(indexRolUser);
        }
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

    public Boolean getPersonalResumeFlag() {
        return personalResumeFlag;
    }

    public void setPersonalResumeFlag(Boolean personalResumeFlag) {
        this.personalResumeFlag = personalResumeFlag;
    }

    public Boolean getCreateCompanySupervisorFlag() {
        return createCompanySupervisorFlag;
    }

    public void setCreateCompanySupervisorFlag(Boolean createCompanySupervisorFlag) {
        this.createCompanySupervisorFlag = createCompanySupervisorFlag;
    }

    public Boolean getSearchStudent() {
        return searchStudent;
    }

    public void setSearchStudent(Boolean searchStudent) {
        this.searchStudent = searchStudent;
    }

    public void createUser() {
        try {
            getUserCareers().clear();
            for (int i = 0; i < getSelectedCareers().size(); i++) {
                getUserCareers().add(new UserCareer(getSelectedCareers().get(i), getUser()));
            }
            getUser().setUserCareers(getUserCareers());
            if (personalResume != null){
                getUser().setPersonalResume(personalResume.getContents());
            }
            userFacade.createUser(getUser());
            MessageUtils.addSuccessMessage("Se ha registrado el usuario correctamente");
            cleanNewUser();
            verifyView();
        } catch (UserException ex) {
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
                    searchStudent = true;
                    createCompanySupervisorFlag = false;
                    personalResumeFlag = false;
                    break;
                case Constants.COORDINADOR_CARRERA:
                case Constants.SUPERVISOR_EPS:
                    personalCodeFlag = true;
                    academicRegisterFlag = false;
                    careerSelectionFlag = true;
                    searchStudent = false;
                    createCompanySupervisorFlag = false;
                    personalResumeFlag = false;
                    break;
                case Constants.REVISOR:
                case Constants.ASESOR:
                    personalCodeFlag = true;
                    academicRegisterFlag = false;
                    careerSelectionFlag = true;
                    searchStudent = false;
                    createCompanySupervisorFlag = false;
                    personalResumeFlag = true;
                    break;
                case Constants.SUPERVISOR_EMPRESA:
                    personalCodeFlag = false;
                    academicRegisterFlag = false;
                    careerSelectionFlag = false;
                    searchStudent = false;
                    createCompanySupervisorFlag = true;
                    personalResumeFlag = false;
                    break;
                default:
                    personalCodeFlag = true;
                    academicRegisterFlag = false;
                    careerSelectionFlag = false;
                    searchStudent = false;
                    createCompanySupervisorFlag = false;
                    personalResumeFlag = false;
            }
            cleanUser();
        } else {
            personalCodeFlag = false;
            academicRegisterFlag = true;
            careerSelectionFlag = true;
            searchStudent = true;
        }
    }

    public void fillStudent() {
        try {
            Optional<StudentData> data = userFacade.getStudentData(user.getAcademicRegister());
            if (data.isPresent()) {
                user.setDpi(data.get().getCui());
                user.setFirstName(data.get().getNombres());
                user.setLastName(data.get().getApellidos());
                MessageUtils.addSuccessMessage("Estudiante encontrado");
            } else {
                cleanUser();
                MessageUtils.addErrorMessage("No se encontró el Estudiante");
            }
        } catch (HttpClientException ex) {
            MessageUtils.addErrorMessage(ex.getMessage() + "\nIngrese los datos Manualmente");
            searchStudent = false;
        }
    }

    public void handlePersonalResume(FileUploadEvent event) {
        this.personalResume = event.getFile();
        this.personalResumeFileName = event.getFile().getFileName();
        this.personalResumeStream = new DefaultStreamedContent(new ByteArrayInputStream(personalResume.getContents()), "application/pdf", "Curriculum.pdf");
    }

    public String getPersonalResumeFileName() {
        return personalResumeFileName;
    }

    public void setPersonalResumeFileName(String personalResumeFileName) {
        this.personalResumeFileName = personalResumeFileName;
    }

    public StreamedContent getPersonalResumeStream() {
        return personalResumeStream;
    }

    public void setPersonalResumeStream(StreamedContent personalResumeStream) {
        this.personalResumeStream = personalResumeStream;
    }

    public void reloadPersonalResume() {
        this.personalResumeStream = new DefaultStreamedContent(new ByteArrayInputStream(personalResume.getContents()), "application/pdf", "Curriculum.pdf");
    }

    private void cleanNewUser() {
        user = null;
        personalResume = null;
        personalResumeFileName = "";
        getSelectedCareers().clear();
        getUserCareers().clear();
    }

    private void cleanUser() {
        getUser().setUserId("");
        getUser().setDpi("");
        getUser().setAcademicRegister("");
        getUser().setCodePersonal("");
        getUser().setFirstName("");
        getUser().setLastName("");
        getUser().setEmail("");
        getUser().setPhone1("");
        getUser().setPhone2("");
        getUser().setPassword("");
        getUser().setDirection("");
        getUser().setNameCompanyWork("");
        getUser().setDirectionCompanyWork("");
        getUser().setPhoneCompanyWork("");
        getUser().setPersonalResume(new byte[0]);
        personalResume = null;
        personalResumeFileName = "";
        getSelectedCareers().clear();
        getUserCareers().clear();
    }
}
