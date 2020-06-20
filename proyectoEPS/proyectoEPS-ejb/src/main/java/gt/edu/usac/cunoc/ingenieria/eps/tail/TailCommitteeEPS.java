package gt.edu.usac.cunoc.ingenieria.eps.tail;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;

@Entity
@Table(name = "TAIL_COMMITTEE_EPS")
public class TailCommitteeEPS implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "dateRequestReview")
    private LocalDate dateRequestReview;
    @OneToOne
    @JoinColumn(name="PROCESS_id",referencedColumnName = "id")
    private Process process;

    public TailCommitteeEPS() {
    }

    public TailCommitteeEPS(LocalDate dateRequestReview, Process process) {
        this.dateRequestReview = dateRequestReview;
        this.process = process;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return dateRequestReview;
    }

    public void setDate(LocalDate dateRequestReview) {
        this.dateRequestReview = dateRequestReview;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }
    
}
