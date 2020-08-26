/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.userDto;


import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author crystian
 */
public class CompleteUserDto  extends BasicUserDto implements Serializable {
    
    
    private String dpi;
    private String codePersonal;
    private String academicRegister;
    private Boolean status;
    private Boolean epsCommittee;
    
    private String rol;
    private List<UserCareerDto> careers;

    public CompleteUserDto() {
    }
    
    public CompleteUserDto(User user) {
        super(user);
        this.dpi = user.getDpi();
        this.codePersonal = user.getCodePersonal();
        this.academicRegister = user.getAcademicRegister();
        this.status = user.getStatus();
        this.epsCommittee = user.getEpsCommittee();
        this.rol = user.getROLid().getName();
        this.careers = user.getUserCareers().stream()
                .map(UserCareerDto::new)
                .collect(Collectors.toList());
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getCodePersonal() {
        return codePersonal;
    }

    public void setCodePersonal(String codePersonal) {
        this.codePersonal = codePersonal;
    }

    public String getAcademicRegister() {
        return academicRegister;
    }

    public void setAcademicRegister(String academicRegister) {
        this.academicRegister = academicRegister;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getEpsCommittee() {
        return epsCommittee;
    }

    public void setEpsCommittee(Boolean epsCommittee) {
        this.epsCommittee = epsCommittee;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<UserCareerDto> getCareers() {
        return careers;
    }

    public void setCareers(List<UserCareerDto> careers) {
        this.careers = careers;
    }
    
}
