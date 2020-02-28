
package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name = "PROJECT")
public class Project implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title; 
    @Column(name = "state")
    private Short state;
    @Column(name = "schedule")
    private Byte[] schedule;
    @Column(name = "investmentPlan")
    private Byte[] investmentPlan;
    @Column(name = "annexed")
    private Byte[] annexed;
    @Column(name = "limitReceptionDate")
    private LocalDate limitReceptionDate;
    @JoinColumn(name = "BIBLIOGRAPHY_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Bibliography bIBLIOGRAPHYid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pROJECTid")
    private Collection<DecimalCoordinate> decimalCoordinateCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pROJECTid")
    private Collection<Objectives> objectivesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pREPROJECTid")
    private Collection<Requeriment> requerimentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pROJECTid")
    private Collection<Section> sectionCollection;

    public Project() {
    }

    public Project(Integer id) {
        this.id = id;
    }

    public Project(Integer id, String title, Short state, Byte[] schedule, Byte[] investmentPlan, Byte[] annexed, LocalDate limitReceptionDate) {
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

    public Byte[] getSchedule() {
        return schedule;
    }

    public void setSchedule(Byte[] schedule) {
        this.schedule = schedule;
    }

    public Byte[] getInvestmentPlan() {
        return investmentPlan;
    }

    public void setInvestmentPlan(Byte[] investmentPlan) {
        this.investmentPlan = investmentPlan;
    }

    public Byte[] getAnnexed() {
        return annexed;
    }

    public void setAnnexed(Byte[] annexed) {
        this.annexed = annexed;
    }

    public LocalDate getLimitReceptionDate() {
        return limitReceptionDate;
    }

    public void setLimitReceptionDate(LocalDate limitReceptionDate) {
        this.limitReceptionDate = limitReceptionDate;
    }

    public Bibliography getBIBLIOGRAPHYid() {
        return bIBLIOGRAPHYid;
    }

    public void setBIBLIOGRAPHYid(Bibliography bIBLIOGRAPHYid) {
        this.bIBLIOGRAPHYid = bIBLIOGRAPHYid;
    }

    public Collection<DecimalCoordinate> getDecimalCoordinateCollection() {
        return decimalCoordinateCollection;
    }

    public void setDecimalCoordinateCollection(Collection<DecimalCoordinate> decimalCoordinateCollection) {
        this.decimalCoordinateCollection = decimalCoordinateCollection;
    }

    public Collection<Objectives> getObjectivesCollection() {
        return objectivesCollection;
    }

    public void setObjectivesCollection(Collection<Objectives> objectivesCollection) {
        this.objectivesCollection = objectivesCollection;
    }

    public Collection<Requeriment> getRequerimentCollection() {
        return requerimentCollection;
    }

    public void setRequerimentCollection(Collection<Requeriment> requerimentCollection) {
        this.requerimentCollection = requerimentCollection;
    }

    public Collection<Section> getSectionCollection() {
        return sectionCollection;
    }

    public void setSectionCollection(Collection<Section> sectionCollection) {
        this.sectionCollection = sectionCollection;
    }
    
    public Byte[] convertByteToObject(byte[] byteToConvert){
        Byte[] converted = new Byte[byteToConvert.length];
        for (int i = 0; i < byteToConvert.length; i++) {
            converted[i] = Byte.valueOf(byteToConvert[i]);
        }
        return converted;
    }
    
    public byte[] convertByteToPrimitive(Byte[] byteToConvert){
        byte[] converted = new byte[byteToConvert.length];
        for (int i = 0; i < byteToConvert.length; i++) {
            converted[i] = byteToConvert[i];
        }
        return converted;
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
        return "gt.edu.usac.cunoc.ingenieria.eps.project.Project[ id=" + id + " ]";
    }
    
}
