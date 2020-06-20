package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT")
public class Project implements Serializable {

    public static final Short ACTIVE = 1;
    public static final Short INACTIVE = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "status")
    private Short status;
    @Column(name = "schedule")
    private byte[] schedule;
    @Column(name = "investmentPlan")
    private byte[] investmentPlan;
    @Column(name = "annexed")
    private byte[] annexed;
    @Column(name = "limitReceptionDate")
    private LocalDate limitReceptionDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true)
    @OrderBy("position ASC")
    private List<Bibliography> bibliographies;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true)
    @OrderBy("position ASC")
    private List<DecimalCoordinate> decimalCoordinates;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true)
    @OrderBy("position ASC")
    private List<Objectives> objectives;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true)
    @OrderBy("position ASC")
    private List<Section> sections;
    @OneToOne
    @JoinColumn(name = "PROCESS_id", referencedColumnName = "id")
    private Process pROCESSid;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "project", orphanRemoval = true)
    @OrderBy("id ASC")
    private List<Correction> corrections;

    public Project() {
        this.decimalCoordinates = new ArrayList<>();
        this.objectives = new ArrayList<>();
        this.bibliographies = new ArrayList<>();
        this.sections = new ArrayList<>();
        setInitialSections();
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

    public short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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

    public void addBibliography() {
        Bibliography bibliography = new Bibliography();
        bibliographies.add(bibliography);
        bibliography.setProject(this);
    }

    public void removeBibliography(Integer bibliographyIndex) {
        bibliographies.get(bibliographyIndex).setProject(null);
        bibliographies.remove(bibliographyIndex.intValue());
    }

    public List<DecimalCoordinate> getDecimalCoordinates() {
        return decimalCoordinates;
    }

    public void setDecimalCoordinates(List<DecimalCoordinate> decimalCoordinates) {
        this.decimalCoordinates = decimalCoordinates;
    }

    public void addDecimalCoordinates() {
        DecimalCoordinate decimalCoordinate = new DecimalCoordinate();
        decimalCoordinates.add(decimalCoordinate);
        decimalCoordinate.setProject(this);
    }

    public void removeDecimalCoordinates(Integer decimalCoordinateIndex) {
        decimalCoordinates.get(decimalCoordinateIndex).setProject(null);
        decimalCoordinates.remove(decimalCoordinateIndex.intValue());
    }

    public List<Objectives> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objectives> objectives) {
        this.objectives = objectives;
    }

    public void addObjective(Objectives objective) throws LimitException {
        objectives.add(objective);
        objective.setProject(this);
    }

    public void removeObjective(Objectives objective) {
        objectives.remove(objective);
        objective.setProject(null);
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<Correction> getCorrections() {
        return corrections;
    }

    public void setCorrections(List<Correction> corrections) {
        this.corrections = corrections;
    }
    
    

    public void addSection() {
        Section section = new Section();
        sections.add(section);
        section.setProject(this);
        section.setLastModificationDate(LocalDate.now());
        section.setType(Section.CUSTOM);
    }

    public void removeSection(Integer sectionIndex) throws MandatoryException {
        Section sectionToEliminate = sections.get(sectionIndex);
        if (sectionToEliminate.getType() == Section.INTRODUCTION) {
            throw new MandatoryException("Sección Introducción Obligatoria");
        } else if (sectionToEliminate.getType() == Section.JUSTIFICATION){
            throw new MandatoryException("Sección Justificación Obligatoria");
        } else {
            sections.get(sectionIndex).setProject(null);
            sections.remove(sectionIndex.intValue());
        }
    }

    public Process getpROCESSid() {
        return pROCESSid;
    }

    public void setpROCESSid(Process pROCESSid) {
        this.pROCESSid = pROCESSid;
    }

    private void setInitialSections() {
        Section sectionIntroduction = new Section();
        sections.add(sectionIntroduction);
        sectionIntroduction.getTitle().setName(Section.INTRODUCTION_TEXT);
        sectionIntroduction.setProject(this);
        sectionIntroduction.setLastModificationDate(LocalDate.now());
        sectionIntroduction.setType(Section.INTRODUCTION);
        
        Section sectionJustification = new Section();
        sections.add(sectionJustification);
        sectionJustification.getTitle().setName(Section.JUSTIFICATION_TEXT);
        sectionJustification.setProject(this);
        sectionJustification.setLastModificationDate(LocalDate.now());
        sectionJustification.setType(Section.JUSTIFICATION);
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
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.project.Project[ id=" + title + " ]";
    }

}
