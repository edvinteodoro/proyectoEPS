package gt.edu.usac.cunoc.ingenieria.eps.processDto;

import gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.io.Serializable;

public class ProcessDto implements Serializable{
    private Integer id;
    private StateProcess state;
    private Integer projectId;
    private Boolean approvedCareerCoordinator;
    private Boolean approvalEPSCommission;
    private Boolean approvedEPSDevelopment;
    private Integer progress;

    public ProcessDto(Process process) {
        this.id = process.getId();
        this.state = process.getState();
        this.approvedCareerCoordinator = process.getApprovedCareerCoordinator();
        this.approvalEPSCommission = process.getApprovalEPSCommission();
        this.approvedEPSDevelopment = process.getApprovedEPSDevelopment();
        this.progress = process.getProgress();
        this.projectId = process.getProject().getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StateProcess getState() {
        return state;
    }

    public void setState(StateProcess state) {
        this.state = state;
    }

    public Boolean getApprovedCareerCoordinator() {
        return approvedCareerCoordinator;
    }

    public void setApprovedCareerCoordinator(Boolean approvedCareerCoordinator) {
        this.approvedCareerCoordinator = approvedCareerCoordinator;
    }

    public Boolean getApprovalEPSCommission() {
        return approvalEPSCommission;
    }

    public void setApprovalEPSCommission(Boolean approvalEPSCommission) {
        this.approvalEPSCommission = approvalEPSCommission;
    }

    public Boolean getApprovedEPSDevelopment() {
        return approvedEPSDevelopment;
    }

    public void setApprovedEPSDevelopment(Boolean approvedEPSDevelopment) {
        this.approvedEPSDevelopment = approvedEPSDevelopment;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
}
