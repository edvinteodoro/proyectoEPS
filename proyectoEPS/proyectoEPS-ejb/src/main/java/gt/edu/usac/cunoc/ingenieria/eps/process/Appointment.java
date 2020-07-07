package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author teodoro
 */
@Entity
@Table(name = "APPOINTMENT")
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "adviser_state")
    @Enumerated(EnumType.STRING)
    private appointmentState adviserState;
    @Column(name = "reviewer_state")
    @Enumerated(EnumType.STRING)
    private appointmentState reviewerState;
    @Basic(optional = false)
    @Column(name = "date_action")
    private LocalDateTime dateAction;
    @JoinColumn(name = "USER_adviser", referencedColumnName = "userId")
    @ManyToOne
    private User userAdviser;
    @JoinColumn(name = "USER_reviewer", referencedColumnName = "userId")
    @ManyToOne
    private User userReviewer;

    public Appointment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserAdviser() {
        return userAdviser;
    }

    public void setUserAdviser(User userAdviser) {
        this.userAdviser = userAdviser;
    }

    public User getUserReviewer() {
        return userReviewer;
    }

    public void setUserReviewer(User userReviewer) {
        this.userReviewer = userReviewer;
    }

    public appointmentState getAdviserState() {
        return adviserState;
    }

    public void setAdviserState(appointmentState adviserState) {
        this.adviserState = adviserState;
    }

    public appointmentState getReviewerState() {
        return reviewerState;
    }

    public void setReviewerState(appointmentState reviewerState) {
        this.reviewerState = reviewerState;
    }

    public LocalDateTime getDateAction() {
        return dateAction;
    }

    public void setDateAction(LocalDateTime dateAction) {
        this.dateAction = dateAction;
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
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.Appointment[ id=" + id + " ]";
    }

}
