/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author teodoro
 */
@Entity
@Table(name = "CORRECTION")
@NamedQueries({
    @NamedQuery(name = "Correction.findAll", query = "SELECT c FROM Correction c")})
public class Correction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "text")
    private byte[] text;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
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

    public Correction(Integer id, byte[] text, Date date) {
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

    public byte[] getText() {
        return text;
    }

    public void setText(byte[] text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
        return "gt.edu.usac.cunoc.ingenieria.Correction[ id=" + id + " ]";
    }
    
}
