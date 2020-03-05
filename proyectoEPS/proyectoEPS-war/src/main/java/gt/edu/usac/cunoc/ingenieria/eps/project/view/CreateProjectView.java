
package gt.edu.usac.cunoc.ingenieria.eps.project.view;

import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ejb.EJB;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class CreateProjectView implements Serializable{
    
    @EJB
    private ProjectFacadeLocal projectFacade;
    
    private UploadedFile schedule;
    private UploadedFile investmentPlan;
    private UploadedFile annexed;
    
    private String sheduleFileName = "";
    private String investmentPlanFileName = "";
    private String annexedFileName = "";

    private Project project;
    
    public Project getProject() {
        if (this.project == null){
            this.project = new Project();
        }
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    public void handleSchedule(FileUploadEvent event){
       this.schedule = event.getFile();
       this.sheduleFileName = event.getFile().getFileName();
    } 
   
    public void handleInvestmentPlan(FileUploadEvent event){
       this.investmentPlan = event.getFile();
       this.investmentPlanFileName = event.getFile().getFileName();
    }
   
    public void handleAnnexed(FileUploadEvent event){
       this.annexed = event.getFile();
       this.annexedFileName = event.getFile().getFileName();
    }

    public String getSheduleFileName() {
        return sheduleFileName;
    }

    public void setSheduleFileName(String sheduleFileName) {
        this.sheduleFileName = sheduleFileName;
    }

    public String getInvestmentPlanFileName() {
        return investmentPlanFileName;
    }

    public void setInvestmentPlanFileName(String investmentPlanFileName) {
        this.investmentPlanFileName = investmentPlanFileName;
    }

    public String getAnnexedFileName() {
        return annexedFileName;
    }

    public void setAnnexedFileName(String annexedFileName) {
        this.annexedFileName = annexedFileName;
    }
   
    public Boolean nullFiles() {
        if (schedule == null || investmentPlan == null) {
            MessageUtils.addErrorMessage("Ingrese los documentos obligatorios *");
            return true;
        }
        return false;
    }
    
    public void upload(){
        if (!nullFiles()) {     
            try {
                getProject().setSchedule(schedule.getContents());
                getProject().setInvestmentPlan(investmentPlan.getContents());
                if (annexed != null) {
                    getProject().setAnnexed(annexed.getContents());
                }
                projectFacade.createProject(getProject());
                MessageUtils.addSuccessMessage("Se ha creado el proyecto exitosamente");
                clean();
            } catch (MandatoryException ex) {
                MessageUtils.addErrorMessage(ex.getMessage());
            } 
        }
    }
    
    public void clean(){
        this.project = new Project();
        this.schedule = new DefaultUploadedFile();
        this.annexed = new DefaultUploadedFile();
        this.investmentPlan = new DefaultUploadedFile();
        this.annexedFileName = "";
        this.investmentPlanFileName = "";
        this.sheduleFileName = "";
    }
}
