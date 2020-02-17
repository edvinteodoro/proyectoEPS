/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "state")
    private short state;
    @Basic(optional = false)
    @NotNull
    @Column(name = "approvedCareerCoordinator")
    private short approvedCareerCoordinator;
    @Lob
    @Column(name = "documentApprovedCareerCoordinator")
    private byte[] documentApprovedCareerCoordinator;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DocumentsToEPSCareerSupervisor")
    private short documentsToEPSCareerSupervisor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DocumentsToEPSCommission")
    private short documentsToEPSCommission;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PreProjectToEPSCommission")
    private short preProjectToEPSCommission;
    @Column(name = "approvalEPSCommission")
    private Short approvalEPSCommission;
    @Basic(optional = false)
    @NotNull
    @Column(name = "approvedEPSDevelopment")
    private short approvedEPSDevelopment;
    @Column(name = "progress")
    private Integer progress;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pROCESSid")
    private Collection<Supervision> supervisionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pROCESSid")
    private Collection<DocumentFinalEps> documentFinalEpsCollection;
    @JoinColumn(name = "APPOINTMENT_id", referencedColumnName = "id")
    @ManyToOne
    private Appointment aPPOINTMENTid;
    @JoinColumn(name = "DELIVER__EPS_COMPLETION_DOCUMENT_id", referencedColumnName = "id")
    @ManyToOne
    private DeliverEpsCompletionDocument dELIVEREPSCOMPLETIONDOCUMENTid;
    @JoinColumn(name = "DOCUMENT_INITIAL_EPS_id", referencedColumnName = "id")
    @ManyToOne
    private DocumentInitialEps dOCUMENTINITIALEPSid;
    @JoinColumn(name = "REQUERIMENT_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Requeriment rEQUERIMENTid;
    @JoinColumn(name = "USER_CAREER_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserCareer uSERCAREERid;

    public Process() {
    }

    public Process(Integer id) {
        this.id = id;
    }

    public Process(Integer id, short state, short approvedCareerCoordinator, short documentsToEPSCareerSupervisor, short documentsToEPSCommission, short preProjectToEPSCommission, short approvedEPSDevelopment) {
        this.id = id;
        this.state = state;
        this.approvedCareerCoordinator = approvedCareerCoordinator;
        this.documentsToEPSCareerSupervisor = documentsToEPSCareerSupervisor;
        this.documentsToEPSCommission = documentsToEPSCommission;
        this.preProjectToEPSCommission = preProjectToEPSCommission;
        this.approvedEPSDevelopment = approvedEPSDevelopment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public short getApprovedCareerCoordinator() {
        return approvedCareerCoordinator;
    }

    public void setApprovedCareerCoordinator(short approvedCareerCoordinator) {
        this.approvedCareerCoordinator = approvedCareerCoordinator;
    }

    public byte[] getDocumentApprovedCareerCoordinator() {
        return documentApprovedCareerCoordinator;
    }

    public void setDocumentApprovedCareerCoordinator(byte[] documentApprovedCareerCoordinator) {
        this.documentApprovedCareerCoordinator = documentApprovedCareerCoordinator;
    }

    public short getDocumentsToEPSCareerSupervisor() {
        return documentsToEPSCareerSupervisor;
    }

    public void setDocumentsToEPSCareerSupervisor(short documentsToEPSCareerSupervisor) {
        this.documentsToEPSCareerSupervisor = documentsToEPSCareerSupervisor;
    }

    public short getDocumentsToEPSCommission() {
        return documentsToEPSCommission;
    }

    public void setDocumentsToEPSCommission(short documentsToEPSCommission) {
        this.documentsToEPSCommission = documentsToEPSCommission;
    }

    public short getPreProjectToEPSCommission() {
        return preProjectToEPSCommission;
    }

    public void setPreProjectToEPSCommission(short preProjectToEPSCommission) {
        this.preProjectToEPSCommission = preProjectToEPSCommission;
    }

    public Short getApprovalEPSCommission() {
        return approvalEPSCommission;
    }

    public void setApprovalEPSCommission(Short approvalEPSCommission) {
        this.approvalEPSCommission = approvalEPSCommission;
    }

    public short getApprovedEPSDevelopment() {
        return approvedEPSDevelopment;
    }

    public void setApprovedEPSDevelopment(short approvedEPSDevelopment) {
        this.approvedEPSDevelopment = approvedEPSDevelopment;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Collection<Supervision> getSupervisionCollection() {
        return supervisionCollection;
    }

    public void setSupervisionCollection(Collection<Supervision> supervisionCollection) {
        this.supervisionCollection = supervisionCollection;
    }

    public Collection<DocumentFinalEps> getDocumentFinalEpsCollection() {
        return documentFinalEpsCollection;
    }

    public void setDocumentFinalEpsCollection(Collection<DocumentFinalEps> documentFinalEpsCollection) {
        this.documentFinalEpsCollection = documentFinalEpsCollection;
    }

    public Appointment getAPPOINTMENTid() {
        return aPPOINTMENTid;
    }

    public void setAPPOINTMENTid(Appointment aPPOINTMENTid) {
        this.aPPOINTMENTid = aPPOINTMENTid;
    }

    public DeliverEpsCompletionDocument getDELIVEREPSCOMPLETIONDOCUMENTid() {
        return dELIVEREPSCOMPLETIONDOCUMENTid;
    }

    public void setDELIVEREPSCOMPLETIONDOCUMENTid(DeliverEpsCompletionDocument dELIVEREPSCOMPLETIONDOCUMENTid) {
        this.dELIVEREPSCOMPLETIONDOCUMENTid = dELIVEREPSCOMPLETIONDOCUMENTid;
    }

    public DocumentInitialEps getDOCUMENTINITIALEPSid() {
        return dOCUMENTINITIALEPSid;
    }

    public void setDOCUMENTINITIALEPSid(DocumentInitialEps dOCUMENTINITIALEPSid) {
        this.dOCUMENTINITIALEPSid = dOCUMENTINITIALEPSid;
    }

    public Requeriment getREQUERIMENTid() {
        return rEQUERIMENTid;
    }

    public void setREQUERIMENTid(Requeriment rEQUERIMENTid) {
        this.rEQUERIMENTid = rEQUERIMENTid;
    }

    public UserCareer getUSERCAREERid() {
        return uSERCAREERid;
    }

    public void setUSERCAREERid(UserCareer uSERCAREERid) {
        this.uSERCAREERid = uSERCAREERid;
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
        return "gt.edu.usac.cunoc.ingenieria.Process[ id=" + id + " ]";
    }
    
}
