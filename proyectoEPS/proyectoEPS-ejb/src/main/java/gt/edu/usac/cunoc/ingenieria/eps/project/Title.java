
package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Texto;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
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
@Table(name = "TITLE")
public class Title implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "text")
    private Byte[] text;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tITLEid")
    private Collection<Texto> textoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tITLEid")
    private Collection<Title> titleCollection;
    @JoinColumn(name = "TITLE_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Title tITLEid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tITLEid")
    private Collection<Section> sectionCollection;

    public Title() {
    }

    public Title(Integer id) {
        this.id = id;
    }

    public Title(Integer id, Byte[] text) {
        this.id = id;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte[] getText() {
        return text;
    }

    public void setText(Byte[] text) {
        this.text = text;
    }

    public Collection<Texto> getTextoCollection() {
        return textoCollection;
    }

    public void setTextoCollection(Collection<Texto> textoCollection) {
        this.textoCollection = textoCollection;
    }

    public Collection<Title> getTitleCollection() {
        return titleCollection;
    }

    public void setTitleCollection(Collection<Title> titleCollection) {
        this.titleCollection = titleCollection;
    }

    public Title getTITLEid() {
        return tITLEid;
    }

    public void setTITLEid(Title tITLEid) {
        this.tITLEid = tITLEid;
    }

    public Collection<Section> getSectionCollection() {
        return sectionCollection;
    }

    public void setSectionCollection(Collection<Section> sectionCollection) {
        this.sectionCollection = sectionCollection;
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
        if (!(object instanceof Title)) {
            return false;
        }
        Title other = (Title) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.project.Title[ id=" + id + " ]";
    }
    
}
