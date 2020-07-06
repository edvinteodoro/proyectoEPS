package gt.edu.usac.cunoc.ingenieria.eps.user;

import gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ASESOR;
import gt.edu.usac.cunoc.ingenieria.eps.process.Appointment;
import gt.edu.usac.cunoc.ingenieria.eps.process.DocumentInitialEps;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Column(name = "phone1")
    private String phone1;
    @Column(name = "phone2")
    private String phone2;
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
    @Column(name = "status")
    private Boolean status;
    @Basic(optional = false)
    @Column(name = "eps_committee")
    private Boolean epsCommittee;
    @Column(name = "name_company_work")
    private String nameCompanyWork;
    @Column(name = "phone_company_work")
    private String phoneCompanyWork;
    @Column(name = "direction_company_work")
    private String directionCompanyWork;
    @Lob
    @Column(name = "personal_resume")
    private byte[] personalResume;
    @JoinColumn(name = "ROL_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rol rOLid;
    @OneToMany(mappedBy = "uSERuserId", cascade = CascadeType.ALL)
    private List<UserCareer> userCareers;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAdviser")
    private List<Appointment> appointmentAdvisor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userReviewer")
    private List<Appointment> appointmentReviewer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uSERadviserProposal")
    private Collection<DocumentInitialEps> documentInitialEpsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uSERreviewerProposal")
    private Collection<DocumentInitialEps> documentInitialEpsCollection1;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }

    public User(Rol rOLid, Boolean epsCommittee) {
        this.rOLid = rOLid;
        this.epsCommittee = epsCommittee;
    }

    public User(String userId, String dpi, String firstName, String lastName, String email, String phone1, String password, Boolean status) {
        this.userId = userId;
        this.dpi = dpi;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone1 = phone1;
        this.password = password;
        this.status = status;
    }

    public User(String userId, String dpi, String firstName, String lastName, String email, String phone, String direction, String nameCompanyWork, String phoneCompanyWork, String directionCompanyWork, byte[] personalResume, Rol rOLid) {
        this.userId = userId;
        this.dpi = dpi;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone1 = phone;
        this.direction = direction;
        this.nameCompanyWork = nameCompanyWork;
        this.phoneCompanyWork = phoneCompanyWork;
        this.directionCompanyWork = directionCompanyWork;
        this.personalResume = personalResume;
        this.rOLid = rOLid;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Rol getROLid() {
        return rOLid;
    }

    public void setROLid(Rol rOLid) {
        this.rOLid = rOLid;
    }

    public Boolean getEpsCommittee() {
        return epsCommittee;
    }

    public void setEpsCommittee(Boolean epsCommittee) {
        this.epsCommittee = epsCommittee;
    }

    public String getNameCompanyWork() {
        return nameCompanyWork;
    }

    public void setNameCompanyWork(String nameCompanyWork) {
        this.nameCompanyWork = nameCompanyWork;
    }

    public String getPhoneCompanyWork() {
        return phoneCompanyWork;
    }

    public void setPhoneCompanyWork(String phoneCompanyWork) {
        this.phoneCompanyWork = phoneCompanyWork;
    }

    public String getDirectionCompanyWork() {
        return directionCompanyWork;
    }

    public void setDirectionCompanyWork(String directionCompanyWork) {
        this.directionCompanyWork = directionCompanyWork;
    }

    public byte[] getPersonalResume() {
        return personalResume;
    }

    public void setPersonalResume(byte[] personalResume) {
        this.personalResume = personalResume;
    }

    public List<Appointment> getAppointmentAdvisor() {
        return appointmentAdvisor;
    }

    public void setAppointmentAdvisor(List<Appointment> appointmentAdvisor) {
        this.appointmentAdvisor = appointmentAdvisor;
    }

    public List<Appointment> getAppointmentReviewer() {
        return appointmentReviewer;
    }

    public void setAppointmentReviewer(List<Appointment> appointmentReviewer) {
        this.appointmentReviewer = appointmentReviewer;
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

    /**
     * This method verify if the user is inactive and it's role is Advisor or
     * Reviewer and if have any process assigned
     *
     * @return true when have all this
     */
    public boolean removable() {
        return (!this.status && (this.rOLid.getName().equals(ASESOR) || this.rOLid.getName().equals(Constants.REVISOR))
                && (!(this.appointmentAdvisor != null && !this.appointmentAdvisor.isEmpty())
                || !(this.appointmentReviewer != null && !this.appointmentReviewer.isEmpty())));
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
