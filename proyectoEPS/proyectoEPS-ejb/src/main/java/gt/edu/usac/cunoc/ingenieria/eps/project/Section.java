
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
import javax.persistence.Table;

@Entity
@Table(name = "SECTION")
public class Section implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "lastModificationDate")
    private LocalDate lastModificationDate;

    
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "section", orphanRemoval = true)
    private List<Title> titles;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "section", orphanRemoval = true)
    private List<Correction> corrections = new ArrayList<>();

    public Section() {
        titles = new ArrayList<>();
        this.addTitle();
    }

    public Section(LocalDate lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
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

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }
    
    public void addTitle(){
        Title title = new Title();
        titles.add(title);
        title.setSection(this);
    }
    
    public void removeTitle(Integer titleIndex){
        titles.get(titleIndex).setSection(null);
        titles.remove(titleIndex.intValue());
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
