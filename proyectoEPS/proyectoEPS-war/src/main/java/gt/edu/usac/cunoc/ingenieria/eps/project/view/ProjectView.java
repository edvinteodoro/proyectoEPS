package gt.edu.usac.cunoc.ingenieria.eps.project.view;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.property.repository.PropertyRepository;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCommitteeEPSFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCoordinatorFacadeLocal;

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
    private TailCoordinatorFacadeLocal tailFacade;

    @EJB
    private TailCommitteeEPSFacadeLocal tailCommitteeEPSFacade;

    private User user;

    private StreamedContent scheduleStream;
    private StreamedContent investmentPlanStream;
    private StreamedContent annexedStream;

    private StreamedContent pdfFile;

    private UploadedFile schedule;
    private UploadedFile investmentPlan;
    private UploadedFile annexed;

    private String scheduleFileName = "";
    private String investmentPlanFileName = "";
    private String annexedFileName = "";

    private Integer processId;
    private Process process;

    private List<Objectives> generalObjectves;
    private List<Objectives> specificObjectives;

    private List<Correction> corrections;
    private List<Correction> currentCorrections;

    private Boolean flagUpdate = false;

    private String comment;
    private TypeCorrection typeCorrectionCurrent;

    @PostConstruct
    public void init() {
        try {
            generalObjectves = new ArrayList<>();
            specificObjectives = new ArrayList<>();
            corrections = new ArrayList<>();
            user = userFacade.getAuthenticatedUser().get(0);
        } catch (UserException e) {
        }
    }

    public TypeCorrection getTypeCorrectionCurrent() {
        return typeCorrectionCurrent;
    }

    public void setTypeCorrectionCurrent(TypeCorrection typeCorrectionCurrent) {
        this.typeCorrectionCurrent = typeCorrectionCurrent;
    }

    public Project getProject() {
        if (this.process.getProject() == null) {
            Project newProject = new Project();
            newProject.setpROCESSid(this.process);
            this.process.setProject(newProject);
        }
        return this.process.getProject();
    }

    public void setProject(Project project) {
        this.process.setProject(project);
    }

    public List<Correction> getCorrections() {
        return corrections;
    }

    public void setCorrections(List<Correction> corrections) {
        this.corrections = corrections;
    }

    public String arrayToString(byte[] text) {
        String result = new String(text);
        return result;
    }

    public List<Correction> getCurrentCorrections() {
        return currentCorrections;
    }

    public void setCurrentCorrections(List<Correction> currentCorrections) {
        this.currentCorrections = currentCorrections;
    }

    public Boolean renderTitleCorrections() {
        return !getCurrentCorrections(TypeCorrection.TITLE).isEmpty() && stateActived();
    }

    public Boolean renderObjetivesCorrections() {
        return !getCurrentCorrections(TypeCorrection.GENERAL_OBJETIVES).isEmpty() && stateActived();
    }

    public Boolean renderSpecificObjetivesCorrections() {
        return !getCurrentCorrections(TypeCorrection.SPECIFIC_OBJETIVES).isEmpty() && stateActived();
    }

    public Boolean renderAnexoCorrections() {
        return !getCurrentCorrections(TypeCorrection.ANNEXED).isEmpty() && stateActived();
    }

    public Boolean renderBibligraphyCorrections() {
        return !getCurrentCorrections(TypeCorrection.BIBLIOGRAPHY).isEmpty() && stateActived();
    }

    public Boolean renderCalendarCorrections() {
        return !getCurrentCorrections(TypeCorrection.SCHEDULE).isEmpty() && stateActived();
    }

    public Boolean renderCoordinateCorrections() {
        return !getCurrentCorrections(TypeCorrection.COORDINATE).isEmpty() && stateActived();
    }

    public Boolean renderPlanCorrections() {
        return !getCurrentCorrections(TypeCorrection.INVESTMENT_PLAN).isEmpty() && stateActived();
    }

    public Boolean renderedSection(Integer section) {
        return (!getSectionCorrection(section).isEmpty()) && stateActived();
    }

    public List<Correction> getTitleCorrections() {
        typeCorrectionCurrent = TypeCorrection.TITLE;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getObjetivesCorrections() {
        typeCorrectionCurrent = TypeCorrection.GENERAL_OBJETIVES;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getSpecificObjetivesCorrections() {
        typeCorrectionCurrent = TypeCorrection.SPECIFIC_OBJETIVES;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getAnexoCorrections() {
        typeCorrectionCurrent = TypeCorrection.ANNEXED;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getBibliographyCorrections() {
        typeCorrectionCurrent = TypeCorrection.BIBLIOGRAPHY;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getCalendarCorrections() {
        typeCorrectionCurrent = TypeCorrection.SCHEDULE;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getCoordenteCorrections() {
        typeCorrectionCurrent = TypeCorrection.COORDINATE;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getPlanCorrections() {
        typeCorrectionCurrent = TypeCorrection.INVESTMENT_PLAN;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getSectionCorrection(Integer section) {
        this.currentCorrections = projectFacade.getCorrections(TypeCorrection.SECTION, getProject().getId(), section,true);
        return this.currentCorrections;
    }

    public List<Correction> getCurrentCorrections(TypeCorrection type) {
        this.currentCorrections = projectFacade.getCorrections(type, getProject().getId(), null, true);; 
        return this.currentCorrections;
    }

    public void handleSchedule(FileUploadEvent event) {
        this.schedule = event.getFile();
        getProject().setSchedule(schedule.getContents());
        this.scheduleFileName = event.getFile().getFileName();
        this.scheduleStream = new DefaultStreamedContent(new ByteArrayInputStream(schedule.getContents()), "application/pdf", "Calendario.pdf");
    }

    public void handleInvestmentPlan(FileUploadEvent event) {
        this.investmentPlan = event.getFile();
        getProject().setInvestmentPlan(investmentPlan.getContents());
        this.investmentPlanFileName = event.getFile().getFileName();
        this.investmentPlanStream = new DefaultStreamedContent(new ByteArrayInputStream(investmentPlan.getContents()), "application/pdf", "Plan de Inversión.pdf");
    }

    public void handleAnnexed(FileUploadEvent event) {
        this.annexed = event.getFile();
        getProject().setAnnexed(annexed.getContents());
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

    public StreamedContent getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(StreamedContent pdfFile) {
        this.pdfFile = pdfFile;
    }

    public void reloadSchedule() {
        this.scheduleStream = new DefaultStreamedContent(new ByteArrayInputStream(getProject().getSchedule()), "application/pdf", "Calendario.pdf");
    }

    public void reloadInvestmentPlan() {
        this.investmentPlanStream = new DefaultStreamedContent(new ByteArrayInputStream(getProject().getInvestmentPlan()), "application/pdf", "Plan de Inversión.pdf");
    }

    public void reloadAnnexed() {
        this.annexedStream = new DefaultStreamedContent(new ByteArrayInputStream(getProject().getAnnexed()), "application/pdf", "Anexos.pdf");
    }

    public void setGeneralObjectves(List<Objectives> generalObjectves) {
        this.generalObjectves = generalObjectves;
    }

    public void addGeneralObjectives() {
        if (getGeneralObjectves().size() < PropertyRepository.LIMIT_GENERAL_OBJECTIVE.getValueInt()) {
            Objectives newGeneralObjective = new Objectives();
            newGeneralObjective.setType(Objectives.GENERAL_OBJETICVE);
            newGeneralObjective.setLastModificationDate(LocalDate.now());
            newGeneralObjective.setProject(getProject());
            getGeneralObjectves().add(newGeneralObjective);
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
            Objectives newSpecificObjective = new Objectives();
            newSpecificObjective.setType(Objectives.SPECIFIC_OBJECTIVE);
            newSpecificObjective.setLastModificationDate(LocalDate.now());
            newSpecificObjective.setProject(getProject());
            getSpecificObjectives().add(newSpecificObjective);
        } else {
            MessageUtils.addErrorMessage("Número Maximo de Objetivos Especificos: " + PropertyRepository.LIMIT_SPECIFIC_OBJECTIVE.getValueInt());
        }
    }

    public void removeSpecificOBjectives(Integer objectiveIndex) {
        getSpecificObjectives().remove(objectiveIndex.intValue());
    }

    public void uploadCreate() {
        try {
            loadDocuments();
            loadObjectives();
            if (flagUpdate) {
                projectFacade.updateProject(getProject());
                MessageUtils.addSuccessMessage("Se han Guardado los Cambios");
            } else {
                projectFacade.createProject(getProject());
                processFacade.updateProcess(process);
                flagUpdate = true;
                MessageUtils.addSuccessMessage("Se ha creado el Anteproyecto");
            }
        } catch (MandatoryException | LimitException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    private void loadDocuments() {
        if (schedule != null) {
            getProject().setSchedule(schedule.getContents());
        }
        if (investmentPlan != null) {
            getProject().setInvestmentPlan(investmentPlan.getContents());
        }
        if (annexed != null) {
            getProject().setAnnexed(annexed.getContents());
        }
    }

    private void loadDocumentsView() {
        if (getProject().getSchedule() != null) {
            scheduleStream = new DefaultStreamedContent(new ByteArrayInputStream(getProject().getSchedule()), "application/pdf", "Calendario.pdf");
            scheduleFileName = scheduleStream.getName();
        }
        if (getProject().getInvestmentPlan() != null) {
            investmentPlanStream = new DefaultStreamedContent(new ByteArrayInputStream(getProject().getInvestmentPlan()), "application/pdf", "Plan de Inversión.pdf");
            investmentPlanFileName = investmentPlanStream.getName();

        }
        if (getProject().getAnnexed() != null) {
            annexedStream = new DefaultStreamedContent(new ByteArrayInputStream(getProject().getAnnexed()), "application/pdf", "Anexos.pdf");
            annexedFileName = annexedStream.getName();
        }
    }

    private void loadObjectives() {
        List<Objectives> objectivesList = new ArrayList<>();
        getGeneralObjectves().forEach(generalObjectve -> {
            objectivesList.add(generalObjectve);
        });
        getSpecificObjectives().forEach(specificObjectve -> {
            objectivesList.add(specificObjectve);
        });
        getProject().setObjectives(objectivesList);
    }

    public void loadCurrentProject() throws IOException {
        try {
            corrections = new ArrayList<>();
            this.user = userFacade.getAuthenticatedUser().get(0);
            this.process = processFacade.getProcess(new Process(processId)).get(0);
            if (this.process.getUserCareer().getUSERuserId().equals(this.user)) {
                flagUpdate = false;
                if (getProject().getId() != null) {
                    this.corrections = getProject().getCorrections();
                    loadDocumentsView();
                    flagUpdate = true;
                    for (int i = 0; i < getProject().getObjectives().size(); i++) {
                        if (Objects.equals(getProject().getObjectives().get(i).getType(), Objectives.GENERAL_OBJETICVE)) {
                            generalObjectves.add(getProject().getObjectives().get(i));
                        } else {
                            specificObjectives.add(getProject().getObjectives().get(i));
                        }
                    }
                }
            } else {
                externalContext.redirect(externalContext.getRequestContextPath() + "/error/error-403.xhtml");
            }
        } catch (UserException ex) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/error/error-403.xhtml");
        } catch (ArrayIndexOutOfBoundsException ex){
            externalContext.redirect(externalContext.getRequestContextPath() + "/error/error-404.xhtml");
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

    public void clearCorrectionSelected() {
        this.currentCorrections = new ArrayList<>();
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void reviewRequeried() {
        try {
            uploadCreate();
            if (getProcess().getApprovedCareerCoordinator() == null) {
                tailFacade.createTailCoordinator(getProcess());
            } else if (getProcess().getApprovedCareerCoordinator()) {
                tailCommitteeEPSFacade.createTailCommiteeEPS(getProcess());
            }
            pdfFile = null;
            MessageUtils.addSuccessMessage("La solicitud de revision se ha realizado exitosamente.");
        } catch (ValidationException | MandatoryException | LimitException ex) {
            
            MessageUtils.addErrorMessage(ex.getMessage());
        } 
    }

    public String titlePage() {
        String value = "Anteproyecto";
        if (getProcess().getApprovalEPSCommission() != null) {
            value = "Proyecto";
        }
        return value;
    }

    public void createPDF() {
        try {
            this.pdfFile = new DefaultStreamedContent(projectFacade.createPDF(getProject(), process.getUserCareer()), "application/pdf", getProject().getTitle());
            MessageUtils.addSuccessMessage("Archivo PDF Generado");
        } catch (IOException | ValidationException | MandatoryException | LimitException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    public Boolean stateRevision() {
        Boolean value = false;
        if (getProcess().getState() == StateProcess.REVISION) {
            value = true;
        }
        return value;
    }

    public Boolean stateReject() {
        Boolean value = false;
        if (getProcess().getState() == StateProcess.RECHAZADO) {
            value = true;
        }
        return value;
    }

    public Boolean stateActived() {
        Boolean value = false;
        if (getProcess().getState() == StateProcess.ACTIVO) {
            value = true;
        }
        return value;
    }

    public Boolean editOptions() {
        Boolean value = false;
        if (stateActived()) {
            value = true;
        }
        return value;
    }

    public Boolean showButtonRevision(Correction correction) {
        Boolean value = false;
        if (getProcess().getState() != StateProcess.REVISION && correction != null) {
            if (correction.getStatus() != null) {
                if (correction.getStatus() == true) {
                    value = true;
                }
            }
        }
        return value;
    }
}
