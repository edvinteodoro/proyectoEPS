package gt.edu.usac.cunoc.ingenieria.eps.user;

import gt.edu.usac.cunoc.ingenieria.eps.process.Appointment;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.process.DocumentInitialEps;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author teodoro
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "userId")
    private String userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "DPI")
    private String dpi;
    @Size(max = 45)
    @Column(name = "codePersonal")
    private String codePersonal;
    @Size(max = 45)
    @Column(name = "carnet")
    private String carnet;
    @Size(max = 45)
    @Column(name = "academicRegister")
    private String academicRegister;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "firstName")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "lastName")
    private String lastName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email")
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "phone")
    private String phone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "password")
    private String password;
    @Size(max = 100)
    @Column(name = "direction")
    private String direction;
    @Basic(optional = false)
    @NotNull
    @Column(name = "state")
    private Boolean state;
    @JoinColumn(name = "ROL_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rol rOLid;
    @OneToMany(mappedBy="uSERuserId",cascade = CascadeType.ALL)
    private List<UserCareer> userCareers;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uSERadviser")
    private Collection<Appointment> appointmentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uSERreviewer")
    private Collection<Appointment> appointmentCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uSERadviserProposal")
    private Collection<DocumentInitialEps> documentInitialEpsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uSERreviewerProposal")
    private Collection<DocumentInitialEps> documentInitialEpsCollection1;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }

    public User(String userId, String dpi, String firstName, String lastName, String email, String phone, String password, Boolean state) {
        this.userId = userId;
        this.dpi = dpi;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Rol getROLid() {
        return rOLid;
    }

    public void setROLid(Rol rOLid) {
        this.rOLid = rOLid;
    }

    public Collection<Appointment> getAppointmentCollection() {
        return appointmentCollection;
    }

    public void setAppointmentCollection(Collection<Appointment> appointmentCollection) {
        this.appointmentCollection = appointmentCollection;
    }

    public Collection<Appointment> getAppointmentCollection1() {
        return appointmentCollection1;
    }

    public void setAppointmentCollection1(Collection<Appointment> appointmentCollection1) {
        this.appointmentCollection1 = appointmentCollection1;
    }

    public Collection<DocumentInitialEps> getDocumentInitialEpsCollection() {
        return documentInitialEpsCollection;
    }

    public void setDocumentInitialEpsCollection(Collection<DocumentInitialEps> documentInitialEpsCollection) {
        this.documentInitialEpsCollection = documentInitialEpsCollection;
    }

    public Collection<DocumentInitialEps> getDocumentInitialEpsCollection1() {
        return documentInitialEpsCollection1;
    }

    public void setDocumentInitialEpsCollection1(Collection<DocumentInitialEps> documentInitialEpsCollection1) {
        this.documentInitialEpsCollection1 = documentInitialEpsCollection1;
    }

    public Rol getrOLid() {
        return rOLid;
    }

    public void setrOLid(Rol rOLid) {
        this.rOLid = rOLid;
    }

    public List<UserCareer> getUserCareers() {
        return userCareers;
    }

    public void setUserCareers(List<UserCareer> userCareers) {
        this.userCareers = userCareers;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.User[ userId=" + userId + " ]";
    }
    
}
