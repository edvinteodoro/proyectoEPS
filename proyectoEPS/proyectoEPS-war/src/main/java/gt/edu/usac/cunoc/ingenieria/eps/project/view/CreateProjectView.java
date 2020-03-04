
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
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class CreateProjectView implements Serializable{
    
    @EJB
    private ProjectFacadeLocal projectFacade;
    
    private UploadedFile schedule;
    private UploadedFile investmentPlan;
    private UploadedFile annexed;

    private Project project;
    
    private String title;

     public Project getProject() {
        if (project == null){
            return new Project();
        }
        return project;
    }
  
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
   public void handleSchedule(FileUploadEvent event){
       this.schedule = event.getFile();
   } 
   
   public void handleInvestmentPlan(FileUploadEvent event){
       this.investmentPlan = event.getFile();
   }
   
   public void handleAnnexed(FileUploadEvent event){
       this.annexed = event.getFile();
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
            getProject().setSchedule(schedule.getContents());
            getProject().setInvestmentPlan(investmentPlan.getContents());
            if (annexed != null) {
                getProject().setAnnexed(annexed.getContents());
            }
            getProject().setTitle(title);
            try {
                projectFacade.createProject(project);
                MessageUtils.addSuccessMessage("Se ha creado el proyecto exitosamente");
            } catch (MandatoryException ex) {
                MessageUtils.addErrorMessage(ex.getMessage());
            } 
        }
    }
}
