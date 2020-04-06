
package gt.edu.usac.cunoc.ingenieria.eps.project;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SECTION")
public class Section implements Serializable {
    
    public static final Short INTRODUCTION = 0;
    public static final Short JUSTIFICATION = 1;
    public static final Short CUSTOM = 2;
    public static final String INTRODUCTION_TEXT = "Introducción";
    public static final String JUSTIFICATION_TEXT = "Justificación";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "lastModificationDate")
    private LocalDate lastModificationDate;
    @Column(name = "type")
    private Short type;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    
    @OneToOne(mappedBy="section",cascade = CascadeType.ALL)
    private Title title;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "section", orphanRemoval = true)
    private List<Correction> corrections = new ArrayList<>();

    public Section() {
        this.title = new Title();
    }

    public Section(LocalDate lastModificationDate, Short type) {
        this.lastModificationDate = lastModificationDate;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(LocalDate lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public List<Correction> getCorrections() {
        return corrections;
    }

    public void setCorrections(List<Correction> corrections) {
        this.corrections = corrections;
    }
    
    public void addCorrection(Correction correction){
        corrections.add(correction);
        correction.setSection(this);
    }
    
    public void removeCorrection(Correction correction){
        corrections.remove(correction);
        correction.setSection(null);
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
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
