
package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Title;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SECTION")
public class Section implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
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
        return "gt.edu.usac.cunoc.ingenieria.eps.project.Section[ id=" + id + " ]";
    }
    
}
