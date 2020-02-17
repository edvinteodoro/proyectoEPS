/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;

/**
 *
 * @author teodoro
 */
@Entity
@Table(name = "EXTRA_FILE")
@NamedQueries({
    @NamedQuery(name = "ExtraFile.findAll", query = "SELECT e FROM ExtraFile e")})
public class ExtraFile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "file")
    private byte[] file;
    @JoinColumn(name = "BINNACLE_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Journal bINNACLEid;

    public ExtraFile() {
    }

    public ExtraFile(Integer id) {
        this.id = id;
    }

    public ExtraFile(Integer id, byte[] file) {
        this.id = id;
        this.file = file;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Journal getBINNACLEid() {
        return bINNACLEid;
    }

    public void setBINNACLEid(Journal bINNACLEid) {
        this.bINNACLEid = bINNACLEid;
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
        if (!(object instanceof ExtraFile)) {
            return false;
        }
        ExtraFile other = (ExtraFile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.ExtraFile[ id=" + id + " ]";
    }
    
}
