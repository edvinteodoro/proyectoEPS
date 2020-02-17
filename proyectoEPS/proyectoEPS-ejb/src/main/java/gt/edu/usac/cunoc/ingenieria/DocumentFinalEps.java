/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author teodoro
 */
@Entity
@Table(name = "DOCUMENT_FINAL_EPS")
@NamedQueries({
    @NamedQuery(name = "DocumentFinalEps.findAll", query = "SELECT d FROM DocumentFinalEps d")})
public class DocumentFinalEps implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "epsCareerSupervisorOpinion")
    private String epsCareerSupervisorOpinion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "certificateConclusionByEPSCoordinator")
    private byte[] certificateConclusionByEPSCoordinator;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "finalReport")
    private byte[] finalReport;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "transcriptionCertificateConclusionEPS")
    private byte[] transcriptionCertificateConclusionEPS;
    @Basic(optional = false)
    @NotNull
    @Column(name = "transferToCoordinatingSecretary")
    private short transferToCoordinatingSecretary;
    @JoinColumn(name = "PROCESS_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Process pROCESSid;

    public DocumentFinalEps() {
    }

    public DocumentFinalEps(Integer id) {
        this.id = id;
    }

    public DocumentFinalEps(Integer id, String epsCareerSupervisorOpinion, byte[] certificateConclusionByEPSCoordinator, byte[] finalReport, byte[] transcriptionCertificateConclusionEPS, short transferToCoordinatingSecretary) {
        this.id = id;
        this.epsCareerSupervisorOpinion = epsCareerSupervisorOpinion;
        this.certificateConclusionByEPSCoordinator = certificateConclusionByEPSCoordinator;
        this.finalReport = finalReport;
        this.transcriptionCertificateConclusionEPS = transcriptionCertificateConclusionEPS;
        this.transferToCoordinatingSecretary = transferToCoordinatingSecretary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEpsCareerSupervisorOpinion() {
        return epsCareerSupervisorOpinion;
    }

    public void setEpsCareerSupervisorOpinion(String epsCareerSupervisorOpinion) {
        this.epsCareerSupervisorOpinion = epsCareerSupervisorOpinion;
    }

    public byte[] getCertificateConclusionByEPSCoordinator() {
        return certificateConclusionByEPSCoordinator;
    }

    public void setCertificateConclusionByEPSCoordinator(byte[] certificateConclusionByEPSCoordinator) {
        this.certificateConclusionByEPSCoordinator = certificateConclusionByEPSCoordinator;
    }

    public byte[] getFinalReport() {
        return finalReport;
    }

    public void setFinalReport(byte[] finalReport) {
        this.finalReport = finalReport;
    }

    public byte[] getTranscriptionCertificateConclusionEPS() {
        return transcriptionCertificateConclusionEPS;
    }

    public void setTranscriptionCertificateConclusionEPS(byte[] transcriptionCertificateConclusionEPS) {
        this.transcriptionCertificateConclusionEPS = transcriptionCertificateConclusionEPS;
    }

    public short getTransferToCoordinatingSecretary() {
        return transferToCoordinatingSecretary;
    }

    public void setTransferToCoordinatingSecretary(short transferToCoordinatingSecretary) {
        this.transferToCoordinatingSecretary = transferToCoordinatingSecretary;
    }

    public Process getPROCESSid() {
        return pROCESSid;
    }

    public void setPROCESSid(Process pROCESSid) {
        this.pROCESSid = pROCESSid;
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
        if (!(object instanceof DocumentFinalEps)) {
            return false;
        }
        DocumentFinalEps other = (DocumentFinalEps) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.DocumentFinalEps[ id=" + id + " ]";
    }
    
}
