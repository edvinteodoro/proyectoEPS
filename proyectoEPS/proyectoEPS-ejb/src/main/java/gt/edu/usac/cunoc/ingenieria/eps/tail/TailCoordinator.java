package gt.edu.usac.cunoc.ingenieria.eps.tail;

import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author teodoro
 */
@Entity
@Table(name = "TAIL_COORDINATOR")
@NamedQueries({
    @NamedQuery(name = "TailCoordinator.findAll", query = "SELECT t FROM TailCoordinator t")})
public class TailCoordinator implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    private LocalDate date;
    @OneToOne
    @JoinColumn(name="USER_CAREER_id",referencedColumnName = "id")
    private UserCareer userCareer;
    @OneToOne
    @JoinColumn(name="PROCESS_id",referencedColumnName = "id")
    private Process process;

    public TailCoordinator() {
    }

    public TailCoordinator(Integer id) {
        this.id = id;
    }

    public TailCoordinator(Integer id, LocalDate date, UserCareer userCareer, Process process) {
        this.id = id;
        this.date = date;
        this.userCareer = userCareer;
        this.process = process;
    }
    
    public TailCoordinator(LocalDate date, UserCareer userCareer, Process process) {
        this.id = id;
        this.date = date;
        this.userCareer = userCareer;
        this.process = process;
    }


    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
        if (!(object instanceof TailCoordinator)) {
            return false;
        }
        TailCoordinator other = (TailCoordinator) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.tief.TailCoordinator[ id=" + id + " ]";
    }

    public UserCareer getUserCareer() {
        return userCareer;
    }

    public void setUserCareer(UserCareer userCareer) {
        this.userCareer = userCareer;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }
    
    
    
}
