package gt.edu.usac.cunoc.ingenieria.eps.project;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "OBJECTIVES")
public class Objectives implements Serializable {
    
    public static final Short SPECIFIC_OBJECTIVE = 0;
    public static final Short GENERAL_OBJETICVE = 1;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    private Short type;
    @Column(name = "text")
    private String text;
    @Column(name = "lastModificationDate")
    private LocalDate lastModificationDate;
    @Column(name = "position")
    private Integer position;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "objectives", orphanRemoval = true)
    private List<Correction> corrections = new ArrayList<>();

    public Objectives() {
    }

    public Objectives(Integer id) {
        this.id = id;
    }

    public Objectives(Integer id, Short type, String text, LocalDate lastModificationDate) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.lastModificationDate = lastModificationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(LocalDate lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Correction> getCorrections() {
        return corrections;
    }

    public void setCorrections(List<Correction> corrections) {
        this.corrections = corrections;
    }

    public void addCorrection(Correction correction){
        corrections.add(correction);
        correction.setObjective(this);
    }
    
    public void removeCorrection(Correction correction){
        corrections.remove(correction);
        correction.setObjective(null);
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
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.project.Objectives[ id=" + id + " ]";
    }
    
}
