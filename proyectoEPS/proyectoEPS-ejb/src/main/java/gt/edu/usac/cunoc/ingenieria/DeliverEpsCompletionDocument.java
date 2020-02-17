/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "DELIVER__EPS_COMPLETION_DOCUMENT")
@NamedQueries({
    @NamedQuery(name = "DeliverEpsCompletionDocument.findAll", query = "SELECT d FROM DeliverEpsCompletionDocument d")})
public class DeliverEpsCompletionDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "deliveryFinalReport")
    private short deliveryFinalReport;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "counterpartSettlement")
    private byte[] counterpartSettlement;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "reviewerLetter")
    private byte[] reviewerLetter;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "adviserLetter")
    private byte[] adviserLetter;
    @OneToMany(mappedBy = "dELIVEREPSCOMPLETIONDOCUMENTid")
    private Collection<Process> processCollection;

    public DeliverEpsCompletionDocument() {
    }

    public DeliverEpsCompletionDocument(Integer id) {
        this.id = id;
    }

    public DeliverEpsCompletionDocument(Integer id, short deliveryFinalReport, byte[] counterpartSettlement, byte[] reviewerLetter, byte[] adviserLetter) {
        this.id = id;
        this.deliveryFinalReport = deliveryFinalReport;
        this.counterpartSettlement = counterpartSettlement;
        this.reviewerLetter = reviewerLetter;
        this.adviserLetter = adviserLetter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getDeliveryFinalReport() {
        return deliveryFinalReport;
    }

    public void setDeliveryFinalReport(short deliveryFinalReport) {
        this.deliveryFinalReport = deliveryFinalReport;
    }

    public byte[] getCounterpartSettlement() {
        return counterpartSettlement;
    }

    public void setCounterpartSettlement(byte[] counterpartSettlement) {
        this.counterpartSettlement = counterpartSettlement;
    }

    public byte[] getReviewerLetter() {
        return reviewerLetter;
    }

    public void setReviewerLetter(byte[] reviewerLetter) {
        this.reviewerLetter = reviewerLetter;
    }

    public byte[] getAdviserLetter() {
        return adviserLetter;
    }

    public void setAdviserLetter(byte[] adviserLetter) {
        this.adviserLetter = adviserLetter;
    }

    public Collection<Process> getProcessCollection() {
        return processCollection;
    }

    public void setProcessCollection(Collection<Process> processCollection) {
        this.processCollection = processCollection;
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
        if (!(object instanceof DeliverEpsCompletionDocument)) {
            return false;
        }
        DeliverEpsCompletionDocument other = (DeliverEpsCompletionDocument) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.DeliverEpsCompletionDocument[ id=" + id + " ]";
    }
    
}
