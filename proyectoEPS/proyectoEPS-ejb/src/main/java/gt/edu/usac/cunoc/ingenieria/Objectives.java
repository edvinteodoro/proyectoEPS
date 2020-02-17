/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author teodoro
 */
@Entity
@Table(name = "OBJECTIVES")
@NamedQueries({
    @NamedQuery(name = "Objectives.findAll", query = "SELECT o FROM Objectives o")})
public class Objectives implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "state")
    private short state;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "text")
    private byte[] text;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lastModificationDate")
    @Temporal(TemporalType.DATE)
    private Date lastModificationDate;
    @JoinColumn(name = "PROJECT_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Project pROJECTid;
    @OneToMany(mappedBy = "oBJECTIVESid")
    private Collection<Correction> correctionCollection;

    public Objectives() {
    }

    public Objectives(Integer id) {
        this.id = id;
    }

    public Objectives(Integer id, short state, byte[] text, Date lastModificationDate) {
        this.id = id;
        this.state = state;
        this.text = text;
        this.lastModificationDate = lastModificationDate;
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

    public byte[] getText() {
        return text;
    }

    public void setText(byte[] text) {
        this.text = text;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Project getPROJECTid() {
        return pROJECTid;
    }

    public void setPROJECTid(Project pROJECTid) {
        this.pROJECTid = pROJECTid;
    }

    public Collection<Correction> getCorrectionCollection() {
        return correctionCollection;
    }

    public void setCorrectionCollection(Collection<Correction> correctionCollection) {
        this.correctionCollection = correctionCollection;
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
        if (!(object instanceof Objectives)) {
            return false;
        }
        Objectives other = (Objectives) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.Objectives[ id=" + id + " ]";
    }
    
}
