
package gt.edu.usac.cunoc.ingenieria.eps.project.view;

import gt.edu.usac.cunoc.ingenieria.eps.configuration.repository.PropertyRepository;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ejb.EJB;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class ProjectView implements Serializable{
    
    @EJB
    private ProjectFacadeLocal projectFacade;
    
    private UploadedFile schedule;
    private UploadedFile investmentPlan;
    private UploadedFile annexed;
    
    private String sheduleFileName = "";
    private String investmentPlanFileName = "";
    private String annexedFileName = "";

    private Project project;
    
    private List<Objectives> generalObjectves;
    private List<Objectives> specificObjectives;
    
    private Integer projectId;
     
    @PostConstruct
    public void init() {
        project = new Project();
        generalObjectves = new ArrayList<>();
        specificObjectives = new ArrayList<>();
        project.addSection();
        project.getSections().get(0).getTitles().get(0).setText("Introducción");
        project.addSection();
        project.getSections().get(1).getTitles().get(0).setText("Justificación");
    }
    
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

    public List<Objectives> getGeneralObjectves() {
        return generalObjectves;
    }

    public void setGeneralObjectves(List<Objectives> generalObjectves) {
        this.generalObjectves = generalObjectves;
    }
    
    public void addGeneralObjectives(){
        if (getGeneralObjectves().size() < PropertyRepository.LIMIT_GENERAL_OBJECTIVE.getValueInt()){
            getGeneralObjectves().add(new Objectives());
        } else {
            MessageUtils.addErrorMessage("Número Maximo de Objetivos Generales: " + PropertyRepository.LIMIT_GENERAL_OBJECTIVE.getValueInt());
        }
    }
    
    public void removeGeneralOBjectives(Integer objectiveIndex){
        getGeneralObjectves().remove(objectiveIndex.intValue());
    }

    public List<Objectives> getSpecificObjectives() {
        return specificObjectives;
    }

    public void setSpecificObjectives(List<Objectives> specificObjectives) {
        this.specificObjectives = specificObjectives;
    }
   
    public void addSpecificObjectives(){
        if (getSpecificObjectives().size() < PropertyRepository.LIMIT_SPECIFIC_OBJECTIVE.getValueInt()){
            getSpecificObjectives().add(new Objectives());
        } else {
            MessageUtils.addErrorMessage("Número Maximo de Objetivos Especificos: " + PropertyRepository.LIMIT_SPECIFIC_OBJECTIVE.getValueInt());
        }
    }
    
    public void removeSpecificOBjectives(Integer objectiveIndex){
        getSpecificObjectives().remove(objectiveIndex.intValue());
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
                projectFacade.createProject(getProject(),getGeneralObjectves(),getSpecificObjectives());
                MessageUtils.addSuccessMessage("Se ha creado el proyecto exitosamente");
                clean();
            } catch (MandatoryException | LimitException ex) {
                MessageUtils.addErrorMessage(ex.getMessage());
            } 
        }
    }
    
    public void loadCurrentProject(){
        project = projectFacade.getProject(projectId);
        for (int i = 0; i < project.getObjectives().size(); i++) {
            if (project.getObjectives().get(i).getState() == Objectives.GENERAL_OBJETICVE){
                generalObjectves.add(project.getObjectives().get(i));
            } else {
                specificObjectives.add(project.getObjectives().get(i));
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
        this.generalObjectves.clear();
        this.specificObjectives.clear();
    }
   
}
