package gt.edu.usac.cunoc.ingenieria.eps.project.view;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ESTUDIANTE;
import gt.edu.usac.cunoc.ingenieria.eps.configuration.repository.PropertyRepository;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class ProjectView implements Serializable {
    
    @Inject
    private ExternalContext externalContext;

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
    private Boolean flagUpdate = false;
    private Correction correctionSelected;
    private String comment;
    private StateProcess revisionState;

    @PostConstruct
    public void init() {
        try {
            generalObjectves = new ArrayList<>();
            specificObjectives = new ArrayList<>();
            revisionState=StateProcess.REVISION;
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

    public StateProcess getRevisionState() {
        return revisionState;
    }

    public void setRevisionState(StateProcess revisionState) {
        this.revisionState = revisionState;
    }
    
    

    public void setProject(Project project) {
        this.project = project;
    }

    public Correction getCorrectionTitle() {
        if (getProject().getCorrectionTitle() == null) {
            getProject().setCorrectionTitle(new Correction(LocalDate.now(), coordinator));
        }
        return getProject().getCorrectionTitle();
    }

    public void setCorrectionTitle(Correction correctionTitle) {
        getProject().setCorrectionTitle(correctionTitle);

    }

    public Correction getCorrectionPlan() {
        if (getProject().getCorrectionPlan() == null) {
            getProject().setCorrectionPlan(new Correction(LocalDate.now(), coordinator));
        }
        return getProject().getCorrectionPlan();
    }

    public void setCorrectionPlan(Correction correctionPlan) {
        getProject().setCorrectionPlan(correctionPlan);

    }

    public Correction getCorrectionAnexo() {
        if (getProject().getCorrectionAnexo() == null) {
            getProject().setCorrectionAnexo(new Correction(LocalDate.now(), coordinator));
        }
        return getProject().getCorrectionAnexo();
    }

    public void setCorrectionAnexo(Correction correctionAnexo) {
        getProject().setCorrectionAnexo(correctionAnexo);

    }

    public Correction getCorrectionCalendar() {
        if (getProject().getCorrectionCalendar() == null) {
            getProject().setCorrectionCalendar(new Correction(LocalDate.now(), coordinator));
        }
        return getProject().getCorrectionCalendar();
    }

    public void setCorrectionCalendar(Correction correctionCalendar) {
        getProject().setCorrectionCalendar(correctionCalendar);

    }

    public Correction getCorrectionCoordinates() {
        if (getProject().getCorrectionCoordinates() == null) {
            getProject().setCorrectionCoordinates(new Correction(LocalDate.now(), coordinator));
        }
        return getProject().getCorrectionCoordinates();
    }

    public void setCorrectionCoordinates(Correction correctionCoordinates) {
        getProject().setCorrectionCoordinates(correctionCoordinates);

    }

    public Correction getCorrectionObjetives() {
        if (getProject().getCorrectionObjetives() == null) {
            getProject().setCorrectionObjetives(new Correction(LocalDate.now(), coordinator));
        }
        return getProject().getCorrectionObjetives();
    }

    public void setCorrectionObjetives(Correction correctionObjetives) {
        getProject().setCorrectionObjetives(correctionObjetives);
    }

    public Correction getCorrectionSpecificObjetives() {
        if (getProject().getCorrectionSpecificObjetives() == null) {
            getProject().setCorrectionSpecificObjetives(new Correction(LocalDate.now(), coordinator));
        }
        return getProject().getCorrectionSpecificObjetives();
    }

    public void setCorrectionSpecificObjetives(Correction correctionSpecificObjetives) {
        getProject().setCorrectionSpecificObjetives(correctionSpecificObjetives);

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
                if (schedule != null) {
                    getProject().setSchedule(schedule.getContents());
                }
                if (investmentPlan != null) {
                    getProject().setInvestmentPlan(investmentPlan.getContents());
                }
                if (annexed != null) {
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
                    getProject().getSections().get(0).getTitle().setTitles(null);
                    getProject().getSections().get(1).getTitle().setTitles(null);
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
        coordinator = userFacade.getCareerCoordinator(getProcess()).get(0);
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

    public Process getProcess() {
        return process;
    }

    public String getCorrectionSelectedText() {
        if (getCorrectionSelected() == null) {
            return "";
        } else {
            if (getCorrectionSelected().getText() == null) {
                return "";
            } else {
                return new String(getCorrectionSelected().getText());
            }
        }
    }

    public void setCorrectionSelectedText(String text) {
        getCorrectionSelected().setText(text.getBytes());
    }

    public Correction getCorrectionSelected() {
        return correctionSelected;
    }

    public void setCorrectionSelected(Correction correctionSelected) {
        this.correctionSelected = correctionSelected;
    }

    public void clearCorrectionSelected() {
        this.correctionSelected = null;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public User getCareerCoordinator() {
        return coordinator;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void reviewRequeried() {
        getProcess().setState(getRevisionState()); 
        tailFacade.createTailCoordinator(user, getProcess());
        MessageUtils.addSuccessMessage("La solicitud de revision se ha realizado exitosamente");
    }

    public void finishReview() {
        try {
            projectFacade.updateProject(getProject());
            tailFacade.deleteTailCoordinatod(getProcess());
            getProcess().setState(StateProcess.ACTIVO);
            processFacade.updateProcess(getProcess());
            redirectToProcesses();
        } catch (Exception e) {
            MessageUtils.addErrorMessage("No se puederon agregar los cambios");
        }
    }

    public Boolean isStuden() {
        return user.getROLid().getName().equals(ESTUDIANTE);
    }

    public void createPDF() {
        projectFacade.createPDF(project);
    }

    public void comment(final String modalIdToClose) {
        clearCorrectionSelected();
        PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
    }
    private void redirectToProcesses() throws IOException {
        externalContext.redirect(externalContext.getRequestContextPath() + "/process/processes.xhtml");
    }
}
