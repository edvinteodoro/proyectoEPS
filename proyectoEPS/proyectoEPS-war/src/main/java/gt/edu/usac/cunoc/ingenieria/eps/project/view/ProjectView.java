package gt.edu.usac.cunoc.ingenieria.eps.project.view;

import gt.edu.usac.cunoc.ingenieria.eps.configuration.repository.PropertyRepository;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.Title;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ejb.EJB;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class ProjectView implements Serializable {

    @EJB
    private ProjectFacadeLocal projectFacade;

    @EJB
    private ProcessFacadeLocal processFacade;

    @EJB
    private UserFacadeLocal userFacade;
    
    @EJB
    private TailFacadeLocal tailFacade;

    private User user;
    private User coordinator;

    private StreamedContent scheduleStream;
    private StreamedContent investmentPlanStream;
    private StreamedContent annexedStream;

    private UploadedFile schedule;
    private UploadedFile investmentPlan;
    private UploadedFile annexed;

    private String scheduleFileName = "";
    private String investmentPlanFileName = "";
    private String annexedFileName = "";

    private Project project;
    private Process process;
    private List<Objectives> generalObjectves;
    private List<Objectives> specificObjectives;

    private Integer processId;
    private Title parentTitle;

    private Boolean flagUpdate = false;

    @PostConstruct
    public void init() {
        try {
            generalObjectves = new ArrayList<>();
            specificObjectives = new ArrayList<>();
            user = userFacade.getAuthenticatedUser().get(0);
        } catch (Exception e) {
        }
    }

    public Project getProject() {
        if (this.project == null) {
            this.project = new Project();
        }
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void handleSchedule(FileUploadEvent event) {
        this.schedule = event.getFile();
        this.project.setSchedule(schedule.getContents());
        this.scheduleFileName = event.getFile().getFileName();
        this.scheduleStream = new DefaultStreamedContent(new ByteArrayInputStream(schedule.getContents()), "application/pdf", "Calendario.pdf");
    }

    public void handleInvestmentPlan(FileUploadEvent event) {
        this.investmentPlan = event.getFile();
        this.project.setInvestmentPlan(investmentPlan.getContents());
        this.investmentPlanFileName = event.getFile().getFileName();
        this.investmentPlanStream = new DefaultStreamedContent(new ByteArrayInputStream(investmentPlan.getContents()), "application/pdf", "Plan de Inversión.pdf");
    }

    public void handleAnnexed(FileUploadEvent event) {
        this.annexed = event.getFile();
        this.project.setAnnexed(annexed.getContents());
        this.annexedFileName = event.getFile().getFileName();
        this.annexedStream = new DefaultStreamedContent(new ByteArrayInputStream(annexed.getContents()), "application/pdf", "Anexos.pdf");
    }

    public String getSheduleFileName() {
        return scheduleFileName;
    }

    public void setSheduleFileName(String sheduleFileName) {
        this.scheduleFileName = sheduleFileName;
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

    public StreamedContent getScheduleStream() {
        return scheduleStream;
    }

    public void setScheduleStream(StreamedContent scheduleStream) {
        this.scheduleStream = scheduleStream;
    }

    public StreamedContent getInvestmentPlanStream() {
        return investmentPlanStream;
    }

    public void setInvestmentPlanStream(StreamedContent investmentPlanStream) {
        this.investmentPlanStream = investmentPlanStream;
    }

    public StreamedContent getAnnexedStream() {
        return annexedStream;
    }

    public void setAnnexedStream(StreamedContent annexedStream) {
        this.annexedStream = annexedStream;
    }

    public void reloadSchedule() {
        this.scheduleStream = new DefaultStreamedContent(new ByteArrayInputStream(project.getSchedule()), "application/pdf", "Calendario.pdf");
    }

    public void reloadInvestmentPlan() {
        this.investmentPlanStream = new DefaultStreamedContent(new ByteArrayInputStream(project.getInvestmentPlan()), "application/pdf", "Plan de Inversión.pdf");
    }

    public void reloadAnnexed() {
        this.annexedStream = new DefaultStreamedContent(new ByteArrayInputStream(project.getAnnexed()), "application/pdf", "Anexos.pdf");
    }

    public void setGeneralObjectves(List<Objectives> generalObjectves) {
        this.generalObjectves = generalObjectves;
    }

    public void addGeneralObjectives() {
        if (getGeneralObjectves().size() < PropertyRepository.LIMIT_GENERAL_OBJECTIVE.getValueInt()) {
            getGeneralObjectves().add(new Objectives());
        } else {
            MessageUtils.addErrorMessage("Número Maximo de Objetivos Generales: " + PropertyRepository.LIMIT_GENERAL_OBJECTIVE.getValueInt());
        }
    }

    public void removeGeneralOBjectives(Integer objectiveIndex) {
        getGeneralObjectves().remove(objectiveIndex.intValue());
    }

    public List<Objectives> getSpecificObjectives() {
        return specificObjectives;
    }

    public void setSpecificObjectives(List<Objectives> specificObjectives) {
        this.specificObjectives = specificObjectives;
    }

    public void addSpecificObjectives() {
        if (getSpecificObjectives().size() < PropertyRepository.LIMIT_SPECIFIC_OBJECTIVE.getValueInt()) {
            getSpecificObjectives().add(new Objectives());
        } else {
            MessageUtils.addErrorMessage("Número Maximo de Objetivos Especificos: " + PropertyRepository.LIMIT_SPECIFIC_OBJECTIVE.getValueInt());
        }
    }

    public void removeSpecificOBjectives(Integer objectiveIndex) {
        getSpecificObjectives().remove(objectiveIndex.intValue());
    }

    public Boolean nullFiles() {
        if (schedule == null || investmentPlan == null) {
            MessageUtils.addErrorMessage("Ingrese los documentos obligatorios *");
            return true;
        }
        return false;
    }

    public void uploadCreate() {
        try {
            if (flagUpdate) {
                if (schedule != null){
                    getProject().setSchedule(schedule.getContents());
                }
                if (investmentPlan != null){
                    getProject().setInvestmentPlan(investmentPlan.getContents());
                }
                if (annexed != null){
                    getProject().setAnnexed(annexed.getContents());
                }
                projectFacade.updateProject(getProject(), getGeneralObjectves(), getSpecificObjectives());
                MessageUtils.addSuccessMessage("Se han Guardado los Cambios");
            } else {
                if (!nullFiles()) {
                    getProject().setSchedule(schedule.getContents());
                    getProject().setInvestmentPlan(investmentPlan.getContents());
                    if (annexed != null) {
                        getProject().setAnnexed(annexed.getContents());
                    }
                    projectFacade.createProject(getProject(), getGeneralObjectves(), getSpecificObjectives(), process);
                    MessageUtils.addSuccessMessage("Se ha Creado el Proyecto");
                }

            }
        } catch (MandatoryException | LimitException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    public void loadCurrentProject() {
        this.process = processFacade.getProcess(new Process(processId)).get(0);
        coordinator=userFacade.getCareerCoordinator(getProcess()).get(0);
        System.out.println(coordinator.getFirstName());
        this.project = process.getProject();
        flagUpdate = true;
        if (this.project == null) {
            this.project = new Project();
            flagUpdate = false;
        } else {
            flagUpdate = true;
            scheduleStream = new DefaultStreamedContent(new ByteArrayInputStream(project.getSchedule()), "application/pdf", "Calendario.pdf");
            scheduleFileName = scheduleStream.getName();
            investmentPlanStream = new DefaultStreamedContent(new ByteArrayInputStream(project.getInvestmentPlan()), "application/pdf", "Plan de Inversión.pdf");
            investmentPlanFileName = investmentPlanStream.getName();
            if (project.getAnnexed() != null) {
                annexedStream = new DefaultStreamedContent(new ByteArrayInputStream(project.getAnnexed()), "application/pdf", "Anexos.pdf");
                annexedFileName = annexedStream.getName();
            }
        }

        for (int i = 0; i < project.getObjectives().size(); i++) {
            if (project.getObjectives().get(i).getState() == Objectives.GENERAL_OBJETICVE) {
                generalObjectves.add(project.getObjectives().get(i));
            } else {
                specificObjectives.add(project.getObjectives().get(i));
            }
        }
    }

    public void removeSection(Integer indexSection) {
        try {
            getProject().removeSection(indexSection);
        } catch (MandatoryException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    public Integer getProcessId() {
        return processId;
    }
    
    public Process getProcess(){
        return process;
    }
    
    public void setProcess(Process process){
        this.process=process;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }
    public User getCareerCoordinator() {
        return coordinator; 
    }

    public void reviewRequeried() {
       //validaciones
        tailFacade.createTailCoordinator(user,getProcess());
     }
    public Title getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(Title parentTitle) {
        this.parentTitle = parentTitle;
    }
    
//    public void createPDF(Project project){
//        projectFacade.createPDF(project);
//    }
}
