/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.process.Journal;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "DOCUMENT_INITIAL_EPS")
@NamedQueries({
    @NamedQuery(name = "DocumentInitialEps.findAll", query = "SELECT d FROM DocumentInitialEps d")})
public class DocumentInitialEps implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "approvalAct")
    private byte[] approvalAct;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dOCUMENTINITIALEPSid")
    private Collection<Journal> journalCollection;
    @JoinColumn(name = "USER_adviserProposal", referencedColumnName = "userId")
    @ManyToOne(optional = false)
    private User uSERadviserProposal;
    @JoinColumn(name = "USER_reviewerProposal", referencedColumnName = "userId")
    @ManyToOne(optional = false)
    private User uSERreviewerProposal;

    public DocumentInitialEps() {
    }

    public DocumentInitialEps(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getApprovalAct() {
        return approvalAct;
    }

    public void setApprovalAct(byte[] approvalAct) {
        this.approvalAct = approvalAct;
    }

    public Collection<Journal> getJournalCollection() {
        return journalCollection;
    }

    public void setJournalCollection(Collection<Journal> journalCollection) {
        this.journalCollection = journalCollection;
    }

    public User getUSERadviserProposal() {
        return uSERadviserProposal;
    }

    public void setUSERadviserProposal(User uSERadviserProposal) {
        this.uSERadviserProposal = uSERadviserProposal;
    }

    public User getUSERreviewerProposal() {
        return uSERreviewerProposal;
    }

    public void setUSERreviewerProposal(User uSERreviewerProposal) {
        this.uSERreviewerProposal = uSERreviewerProposal;
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
        if (!(object instanceof DocumentInitialEps)) {
            return false;
        }
        DocumentInitialEps other = (DocumentInitialEps) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.DocumentInitialEps[ id=" + id + " ]";
    }

}
