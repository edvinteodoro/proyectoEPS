package gt.edu.usac.cunoc.ingenieria.eps.user;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private User uSERuserId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PROCESS_id",referencedColumnName = "id")
    private Process process;

    public UserCareer() {
        
    }

    public UserCareer(Integer id) {
        this.id = id;
    }
    
    public UserCareer(Career career,User user){
        this.cAREERcodigo=career;
        this.uSERuserId=user;
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

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
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
