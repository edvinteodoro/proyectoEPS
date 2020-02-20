/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.process;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "JOURNAL")
@NamedQueries({
    @NamedQuery(name = "Journal.findAll", query = "SELECT j FROM Journal j")})
public class Journal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "activity")
    private String activity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "description")
    private String description;
    @Size(max = 200)
    @Column(name = "annotation")
    private String annotation;
    @JoinColumn(name = "DOCUMENT_INITIAL_EPS_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DocumentInitialEps dOCUMENTINITIALEPSid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bINNACLEid")
    private Collection<ExtraFile> extraFileCollection;

    public Journal() {
    }

    public Journal(Integer id) {
        this.id = id;
    }

    public Journal(Integer id, String activity, Date dateTime, String description) {
        this.id = id;
        this.activity = activity;
        this.dateTime = dateTime;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public DocumentInitialEps getDOCUMENTINITIALEPSid() {
        return dOCUMENTINITIALEPSid;
    }

    public void setDOCUMENTINITIALEPSid(DocumentInitialEps dOCUMENTINITIALEPSid) {
        this.dOCUMENTINITIALEPSid = dOCUMENTINITIALEPSid;
    }

    public Collection<ExtraFile> getExtraFileCollection() {
        return extraFileCollection;
    }

    public void setExtraFileCollection(Collection<ExtraFile> extraFileCollection) {
        this.extraFileCollection = extraFileCollection;
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
        if (!(object instanceof Journal)) {
            return false;
        }
        Journal other = (Journal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.Journal[ id=" + id + " ]";
    }
    
}
