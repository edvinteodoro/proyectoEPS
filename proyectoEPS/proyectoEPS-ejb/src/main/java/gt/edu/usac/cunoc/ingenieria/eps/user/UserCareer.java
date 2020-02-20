/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.user;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author teodoro
 */
@Entity
@Table(name = "USER_CAREER")
@NamedQueries({
    @NamedQuery(name = "UserCareer.findAll", query = "SELECT u FROM UserCareer u")})
public class UserCareer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "CAREER_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Career cAREERcodigo;
    @JoinColumn(name = "USER_userId", referencedColumnName = "userId")
    @ManyToOne(optional = false)
    private User uSERuserId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uSERCAREERid")
    private Collection<Process> processCollection;

    public UserCareer() {
    }

    public UserCareer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Career getCAREERcodigo() {
        return cAREERcodigo;
    }

    public void setCAREERcodigo(Career cAREERcodigo) {
        this.cAREERcodigo = cAREERcodigo;
    }

    public User getUSERuserId() {
        return uSERuserId;
    }

    public void setUSERuserId(User uSERuserId) {
        this.uSERuserId = uSERuserId;
    }

    public Collection<Process> getProcessCollection() {
        return processCollection;
    }

    public void setProcessCollection(Collection<Process> processCollection) {
        this.processCollection = processCollection;
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
        if (!(object instanceof UserCareer)) {
            return false;
        }
        UserCareer other = (UserCareer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.UserCareer[ id=" + id + " ]";
    }
    
}
