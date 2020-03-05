
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT")
public class Project implements Serializable {

    public static final Short ACTIVE = 1;
    public static final Short INACTIVE = 0;
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title; 
    @Column(name = "state")
    private Short state;
    @Column(name = "schedule")
    private byte[] schedule;
    @Column(name = "investmentPlan")
    private byte[] investmentPlan;
    @Column(name = "annexed")
    private byte[] annexed;
    @Column(name = "limitReceptionDate")
    private LocalDate limitReceptionDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true)
    private List<Bibliography> bibliographies = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true)
    private List<DecimalCoordinate> decimalCoordinates = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true)
    private List<Objectives> objectives = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();
    
    public Project() {
    }

    public Project(Integer id) {
        this.id = id;
    }

    public Project(Integer id, String title, Short state, byte[] schedule, byte[] investmentPlan, byte[] annexed, LocalDate limitReceptionDate) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.schedule = schedule;
        this.investmentPlan = investmentPlan;
        this.annexed = annexed;
        this.limitReceptionDate = limitReceptionDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public byte[] getSchedule() {
        return schedule;
    }

    public void setSchedule(byte[] schedule) {
        this.schedule = schedule;
    }

    public byte[] getInvestmentPlan() {
        return investmentPlan;
    }

    public void setInvestmentPlan(byte[] investmentPlan) {
        this.investmentPlan = investmentPlan;
    }

    public byte[] getAnnexed() {
        return annexed;
    }

    public void setAnnexed(byte[] annexed) {
        this.annexed = annexed;
    }

    public LocalDate getLimitReceptionDate() {
        return limitReceptionDate;
    }

    public void setLimitReceptionDate(LocalDate limitReceptionDate) {
        this.limitReceptionDate = limitReceptionDate;
    }

    public List<Bibliography> getBibliographies() {
        return bibliographies;
    }

    public void setBibliographies(List<Bibliography> bibliographies) {
        this.bibliographies = bibliographies;
    }

    public void addBibliography(Bibliography bibliography){
        bibliographies.add(bibliography);
        bibliography.setProject(this);
    }
    
    public void removeBibliography(Bibliography bibliography){
        bibliographies.remove(bibliography);
        bibliography.setProject(null);
    }

    public List<DecimalCoordinate> getDecimalCoordinates() {
        return decimalCoordinates;
    }

    public void setDecimalCoordinates(List<DecimalCoordinate> decimalCoordinates) {
        this.decimalCoordinates = decimalCoordinates;
    }
    
    public void addDecimalCoordinates(DecimalCoordinate decimalCoordinate){
        decimalCoordinates.add(decimalCoordinate);
        decimalCoordinate.setProject(this);
    }
    
    public void removeDecimalCoordinates(DecimalCoordinate decimalCoordinate){
        decimalCoordinates.remove(decimalCoordinate);
        decimalCoordinate.setProject(null);
    }

    public List<Objectives> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objectives> objectives) {
        this.objectives = objectives;
    }
    
    public void addObjective(Objectives objective){
        objectives.add(objective);
        objective.setProject(this);
    }
    
    public void removeObjective(Objectives objective){
        objectives.remove(objective);
        objective.setProject(null);
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
    
    public void addSection(Section section){
        sections.add(section);
        section.setProject(this);
    }
    
    public void removeSection(Section section){
        sections.remove(section);
        section.setProject(null);
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
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.project.Project[ id=" + title + " ]";
    }
    
}
