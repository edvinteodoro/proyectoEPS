package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.journal.JournalLog;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import java.io.Serializable;
import java.util.List;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author teodoro
 */
@Entity
@Table(name = "PROCESS")
@NamedQueries({
    @NamedQuery(name = "Process.findAll", query = "SELECT p FROM Process p")})
public class Process implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private StateProcess state;
    @Column(name = "approvedCareerCoordinator")
    private Boolean approvedCareerCoordinator;
    @Lob
    @Column(name = "documentApprovedCareerCoordinator")
    private byte[] documentApprovedCareerCoordinator;
    @Column(name = "approvalEPSCommission")
    private Boolean approvalEPSCommission;
    @Column(name = "date_ApproveddEpsDevelopment")
    private LocalDate dateApproveddEpsDevelopment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "approvedEPSDevelopment")
    private Boolean approvedEPSDevelopment;
    @Column(name = "progress")
    private Integer progress;
    @JoinColumn(name = "APPOINTMENT_id", referencedColumnName = "id")
    @OneToOne
    private Appointment appointmentId;
    @OneToOne(mappedBy = "pROCESSid", cascade = CascadeType.PERSIST)
    private Requeriment requeriment;
    @OneToOne(mappedBy = "process", cascade = CascadeType.REFRESH)
    private UserCareer userCareer;
    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL)
    private List<JournalLog> journalLog;
    @OneToOne(mappedBy = "pROCESSid",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    private User supervisor_EPS;
    
    public Process() {
    }

    public Process(UserCareer userCareer) {
        this.userCareer = userCareer;
    }

    public Process(Boolean approvedCareerCoordinator) {
        this.approvedCareerCoordinator = approvedCareerCoordinator;
    }

    public Process(Integer id) {
        this.id = id;
    }

    public Process(Integer id, Boolean approvedEPSDevelopment) {
        this.id = id;
        this.approvedEPSDevelopment = approvedEPSDevelopment;
    }

    public List<JournalLog> getJournalLog() {
        return journalLog;
    }

    public void setJournalLog(List<JournalLog> journalLog) {
        this.journalLog = journalLog;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StateProcess getState() {
        return state;
    }

    public void setState(StateProcess state) {
        this.state = state;
    }

    public Boolean getApprovedCareerCoordinator() {
        return approvedCareerCoordinator;
    }

    public void setApprovedCareerCoordinator(Boolean approvedCareerCoordinator) {
        this.approvedCareerCoordinator = approvedCareerCoordinator;
    }

    public byte[] getDocumentApprovedCareerCoordinator() {
        return documentApprovedCareerCoordinator;
    }

    public void setDocumentApprovedCareerCoordinator(byte[] documentApprovedCareerCoordinator) {
        this.documentApprovedCareerCoordinator = documentApprovedCareerCoordinator;
    }

    public Boolean getApprovalEPSCommission() {
        return approvalEPSCommission;
    }

    public void setApprovalEPSCommission(Boolean approvalEPSCommission) {
        this.approvalEPSCommission = approvalEPSCommission;
    }

    public Boolean getApprovedEPSDevelopment() {
        return approvedEPSDevelopment;
    }

    public void setApprovedEPSDevelopment(Boolean approvedEPSDevelopment) {
        this.approvedEPSDevelopment = approvedEPSDevelopment;
    }

    public LocalDate getDateApproveddEpsDevelopment() {
        return dateApproveddEpsDevelopment;
    }

    public void setDateApproveddEpsDevelopment(LocalDate dateApproveddEpsDevelopment) {
        this.dateApproveddEpsDevelopment = dateApproveddEpsDevelopment;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Appointment getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Appointment appointmentId) {
        this.appointmentId = appointmentId;
    }

    public UserCareer getUserCareer() {
        return userCareer;
    }

    public void setUserCareer(UserCareer userCareer) {
        this.userCareer = userCareer;
    }

    public String getApprovedCareerCoordinatorMessage() {
        if (approvedCareerCoordinator != null) {
            if (approvedCareerCoordinator == false) {
                return "Rechazado";
            } else {
                return "Aprobado";
            }
        } else {
            return "Pendiente";
        }
    }

    public String getApprovedEPSMessage() {
        if (approvalEPSCommission != null) {
            if (approvalEPSCommission == false) {
                return "Rechazado";
            } else {
                return "Aprobado";
            }
        } else {
            return "Pendiente";
        }
    }

    public Requeriment getRequeriment() {
        return requeriment;
    }

    public void setRequeriment(Requeriment requeriment) {
        this.requeriment = requeriment;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getSudentStyleRow() {
        String value = "";
        if (this.state.equals(StateProcess.ACTIVO)) {
            value = "enabled-row";
        }
        return value;
    }

    public User getSupervisor_EPS() {
        return supervisor_EPS;
    }

    public void setSupervisor_EPS(User supervisor_EPS) {
        this.supervisor_EPS = supervisor_EPS;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Process)) {
            return false;
        }
        Process other = (Process) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.process.Process[ id=" + id + " ]";
    }

}
