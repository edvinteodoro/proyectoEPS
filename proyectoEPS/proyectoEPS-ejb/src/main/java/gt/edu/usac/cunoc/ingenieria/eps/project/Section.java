/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Title;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author teodoro
 */
@Entity
@Table(name = "SECTION")
@NamedQueries({
    @NamedQuery(name = "Section.findAll", query = "SELECT s FROM Section s")})
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lastModificationDate")
    private LocalDate lastModificationDate;
    @OneToMany(mappedBy = "sECTIONid")
    private Collection<Correction> correctionCollection;
    @JoinColumn(name = "PROJECT_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Project pROJECTid;
    @JoinColumn(name = "TITLE_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Title tITLEid;

    public Section() {
    }

    public Section(Integer id) {
        this.id = id;
    }

    public Section(Integer id, String name, LocalDate lastModificationDate) {
        this.id = id;
        this.name = name;
        this.lastModificationDate = lastModificationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(LocalDate lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Collection<Correction> getCorrectionCollection() {
        return correctionCollection;
    }

    public void setCorrectionCollection(Collection<Correction> correctionCollection) {
        this.correctionCollection = correctionCollection;
    }

    public Project getPROJECTid() {
        return pROJECTid;
    }

    public void setPROJECTid(Project pROJECTid) {
        this.pROJECTid = pROJECTid;
    }

    public Title getTITLEid() {
        return tITLEid;
    }

    public void setTITLEid(Title tITLEid) {
        this.tITLEid = tITLEid;
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
        if (!(object instanceof Section)) {
            return false;
        }
        Section other = (Section) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.Section[ id=" + id + " ]";
    }
    
}
