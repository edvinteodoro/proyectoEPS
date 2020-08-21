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
public class UserDto  implements Serializable{
    
    private String id;
    private String dpi;
    private String codePersonal;
    private String academicRegister;
    private String firstName;
    private String lastName;
    private String email;
    private String phone1;
    private String phone2;
    private String direction;
    private Boolean status;
    private Boolean epsCommittee;
    
    private String rol;
    private List<UserCareerDto> careers;

    public UserDto() {
    }
    
    public UserDto(User user) {
        this.id = user.getUserId();
        this.dpi = user.getDpi();
        this.codePersonal = user.getCodePersonal();
        this.academicRegister = user.getAcademicRegister();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phone1 = user.getPhone1();
        this.phone2 = user.getPhone2();
        this.direction = user.getDirection();
        this.status = user.getStatus();
        this.epsCommittee = user.getEpsCommittee();
        this.rol = user.getROLid().getName();
        this.careers = user.getUserCareers().stream()
                .map(UserCareerDto::new)
                .collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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
