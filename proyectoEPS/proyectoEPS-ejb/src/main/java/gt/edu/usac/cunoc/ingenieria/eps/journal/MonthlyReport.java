
package gt.edu.usac.cunoc.ingenieria.eps.journal;

import gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MONTHLY_REPORT")
public class MonthlyReport implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "state_monthly_report")
    @Enumerated(EnumType.STRING)
    private StateMonthlyReport stateMonthlyReport;
    
    @Column(name = "initial_date")
    private LocalDate initialDate;
    
    @Column(name = "final_date")
    private LocalDate finalDate;
    
    @Column(name = "report")
    private byte[] report;
    
    @Column(name = "progress_assessment")
    private Integer progressAssessment;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Process process;

    public MonthlyReport() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StateMonthlyReport getStateMonthlyReport() {
        return stateMonthlyReport;
    }

    public void setStateMonthlyReport(StateMonthlyReport stateMonthlyReport) {
        this.stateMonthlyReport = stateMonthlyReport;
    }
    
    public String getStateMonthlyReportString(){
        switch (this.getStateMonthlyReport()){
            case ACTIVE:
                return "Activo";
            case APPROVED:
                return "Aprobado";
            case REJECTED:
                return "Rechazado";
            case REVIEW:
                return "Revisión";
            default:
                 return "";
        }
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public String getInitialDateString(){
        return this.getInitialDate().format(Constants.DATE_FORMAT_1);
    }
    
    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public String getFinalDateString(){
        return this.getFinalDate().format(Constants.DATE_FORMAT_1);
    }
    
    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public byte[] getReport() {
        return report;
    }

    public void setReport(byte[] report) {
        this.report = report;
    }

    public Integer getProgressAssessment() {
        return progressAssessment;
    }

    public void setProgressAssessment(Integer progressAssessment) {
        this.progressAssessment = progressAssessment;
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
        if (!(object instanceof MonthlyReport)) {
            return false;
        }
        MonthlyReport other = (MonthlyReport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.journal.MonthlyReport[ id=" + id + " ]";
    }
    
}
