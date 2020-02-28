
package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
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
@Table(name = "OBJECTIVES")
public class Objectives implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "state")
    private Short state;
    @Column(name = "text")
    private Byte[] text;
    @Column(name = "lastModificationDate")
    private LocalDate lastModificationDate;
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

    public Objectives(Integer id, Short state, Byte[] text, LocalDate lastModificationDate) {
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

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public Byte[] getText() {
        return text;
    }

    public void setText(Byte[] text) {
        this.text = text;
    }

    public LocalDate getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(LocalDate lastModificationDate) {
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
        return "gt.edu.usac.cunoc.ingenieria.eps.project.Objectives[ id=" + id + " ]";
    }
    
}
