/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Texto;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author teodoro
 */
@Entity
@Table(name = "TITLE")
@NamedQueries({
    @NamedQuery(name = "Title.findAll", query = "SELECT t FROM Title t")})
public class Title implements Serializable {

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

    public Title(Integer id, byte[] text) {
        this.id = id;
        this.text = text;
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
        return "gt.edu.usac.cunoc.ingenieria.Title[ id=" + id + " ]";
    }
    
}
