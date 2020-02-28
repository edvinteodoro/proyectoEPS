
package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CORRECTION")
public class Correction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "text")
    private Byte[] text;
    @Column(name = "date")
    private LocalDate date;
    @JoinColumn(name = "BIBLIOGRAPHY_id", referencedColumnName = "id")
    @ManyToOne
    private Bibliography bIBLIOGRAPHYid;
    @JoinColumn(name = "OBJECTIVES_id", referencedColumnName = "id")
    @ManyToOne
    private Objectives oBJECTIVESid;
    @JoinColumn(name = "SECTION_id", referencedColumnName = "id")
    @ManyToOne
    private Section sECTIONid;
    @JoinColumn(name = "USER_userId", referencedColumnName = "userId")
    @ManyToOne(optional = false)
    private User uSERuserId;

    public Correction() {
    }

    public Correction(Integer id) {
        this.id = id;
    }

    public Correction(Integer id, Byte[] text, LocalDate date) {
        this.id = id;
        this.text = text;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Bibliography getBIBLIOGRAPHYid() {
        return bIBLIOGRAPHYid;
    }

    public void setBIBLIOGRAPHYid(Bibliography bIBLIOGRAPHYid) {
        this.bIBLIOGRAPHYid = bIBLIOGRAPHYid;
    }

    public Objectives getOBJECTIVESid() {
        return oBJECTIVESid;
    }

    public void setOBJECTIVESid(Objectives oBJECTIVESid) {
        this.oBJECTIVESid = oBJECTIVESid;
    }

    public Section getSECTIONid() {
        return sECTIONid;
    }

    public void setSECTIONid(Section sECTIONid) {
        this.sECTIONid = sECTIONid;
    }

    public User getUSERuserId() {
        return uSERuserId;
    }

    public void setUSERuserId(User uSERuserId) {
        this.uSERuserId = uSERuserId;
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
        if (!(object instanceof Correction)) {
            return false;
        }
        Correction other = (Correction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.project.Correction[ id=" + id + " ]";
    }
    
}
