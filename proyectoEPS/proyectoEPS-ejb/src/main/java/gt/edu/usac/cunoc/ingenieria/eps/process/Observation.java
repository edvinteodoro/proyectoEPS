package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OBSERVATION")
public class Observation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "text")
    private byte[] text;
    @Column(name = "date")
    private LocalDate date;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER", referencedColumnName = "userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUERIMENT", referencedColumnName = "id")
    private Requeriment requeriment;

    public Observation() {
    }

    public Integer getId() {
        return id;
    }

    public Observation(byte[] text, LocalDate date, User user, Requeriment requeriment) {
        this.text = text;
        this.date = date;
        this.user = user;
        this.requeriment = requeriment;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        if (this.text != null){
            String result = new String(this.text);
            return result;
        } else {
            return new String();
        }
    }

    public void setText(String text) {
        if (text != null){
            this.text = text.getBytes();
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Requeriment getRequeriment() {
        return requeriment;
    }

    public void setRequeriment(Requeriment requeriment) {
        this.requeriment = requeriment;
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
        if (!(object instanceof Observation)) {
            return false;
        }
        Observation other = (Observation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.process.Observation[ id=" + id + " ]";
    }
    
}
