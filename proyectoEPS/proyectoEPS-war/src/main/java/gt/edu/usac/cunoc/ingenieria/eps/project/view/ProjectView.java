package gt.edu.usac.cunoc.ingenieria.eps.project.view;

import gt.edu.usac.cunoc.ingenieria.eps.configuration.repository.PropertyRepository;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCommitteeEPSFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
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

    private Process process;
    private List<Objectives> generalObjectves;
    private List<Objectives> specificObjectives;
    private List<Correction> corrections;
    private List<Correction> currentCorrections;
    private Integer processId;
    private Boolean flagUpdate = false;
    private String comment;
    private StateProcess revisionState;
    private TypeCorrection typeCorrectionCurrent;

    @PostConstruct
    public void init() {
        try {
            generalObjectves = new ArrayList<>();
            specificObjectives = new ArrayList<>();
            revisionState = StateProcess.REVISION;
            user = userFacade.getAuthenticatedUser().get(0);
        } catch (Exception e) {
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
            this.process.setProject(new Project());
        }
        return this.process.getProject();
    }

    public StateProcess getRevisionState() {
        return revisionState;
    }

    public void setRevisionState(StateProcess revisionState) {
        this.revisionState = revisionState;
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
        return !getCurrentCorrections(TypeCorrection.TITLE).isEmpty();
    }

    public Boolean renderObjetivesCorrections() {
        return !getCurrentCorrections(TypeCorrection.OBJETIVES).isEmpty();
    }

    public Boolean renderSpecificObjetivesCorrections() {
        return !getCurrentCorrections(TypeCorrection.SPECIFIC_OBJETIVES).isEmpty();
    }

    public Boolean renderAnexoCorrections() {
        return !getCurrentCorrections(TypeCorrection.ANEXO).isEmpty();
    }

    public Boolean renderBibligraphyCorrections() {
        return !getCurrentCorrections(TypeCorrection.BIBLIOGRAPHY).isEmpty();
    }

    public Boolean renderCalendarCorrections() {
        return !getCurrentCorrections(TypeCorrection.CALENDAR).isEmpty();
    }

    public Boolean renderCoordinateCorrections() {
        return !getCurrentCorrections(TypeCorrection.COORDINATE).isEmpty();
    }

    public Boolean renderPlanCorrections() {
        return !getCurrentCorrections(TypeCorrection.PLAN).isEmpty();
    }

    public Boolean renderedSection(Integer section) {
        return !getSectionCorrection(section).isEmpty();
    }

    public List<Correction> getTitleCorrections() {
        typeCorrectionCurrent = TypeCorrection.TITLE;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getObjetivesCorrections() {
        typeCorrectionCurrent = TypeCorrection.OBJETIVES;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getSpecificObjetivesCorrections() {
        typeCorrectionCurrent = TypeCorrection.SPECIFIC_OBJETIVES;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getAnexoCorrections() {
        typeCorrectionCurrent = TypeCorrection.ANEXO;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getBibliographyCorrections() {
        typeCorrectionCurrent = TypeCorrection.BIBLIOGRAPHY;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getCalendarCorrections() {
        typeCorrectionCurrent = TypeCorrection.CALENDAR;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getCoordenteCorrections() {
        typeCorrectionCurrent = TypeCorrection.COORDINATE;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getPlanCorrections() {
        typeCorrectionCurrent = TypeCorrection.PLAN;
        return getCurrentCorrections(typeCorrectionCurrent);
    }

    public List<Correction> getSectionCorrection(Integer section) {
        currentCorrections = new ArrayList<>();
        for (int i = 0; i < corrections.size(); i++) {
            if (corrections.get(i).getSection() != null) {
                if (corrections.get(i).getSection().getId().equals(section)) {
                    currentCorrections.add(corrections.get(i));
                }
            }
        }
        return currentCorrections;
    }

    public List<Correction> getCurrentCorrections(TypeCorrection type) {
        this.currentCorrections = new ArrayList<>();
        if (getCorrections() != null) {
            for (int i = 0; i < getCorrections().size(); i++) {
                if (getCorrections().get(i).getType().equals(type)) {
                    this.currentCorrections.add(getCorrections().get(i));
                }
            }
        }
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
                    projectFacade.createProject(getGeneralObjectves(), getSpecificObjectives(), getProcess());
                    flagUpdate = true;
                    MessageUtils.addSuccessMessage("Se ha Creado el Proyecto");
                }
            }
        } catch (MandatoryException | LimitException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    public void loadCurrentProject() throws IOException {
        try {
            this.process = processFacade.getProcess(new Process(processId)).get(0);
            if (this.process.getUserCareer().getUSERuserId().equals(this.user)) {
                flagUpdate = false;
                if (getProject().getId() != null) {
                    this.corrections = getProject().getCorrections();
                    flagUpdate = true;
                    scheduleStream = new DefaultStreamedContent(new ByteArrayInputStream(getProject().getSchedule()), "application/pdf", "Calendario.pdf");
                    scheduleFileName = scheduleStream.getName();
                    investmentPlanStream = new DefaultStreamedContent(new ByteArrayInputStream(getProject().getInvestmentPlan()), "application/pdf", "Plan de Inversión.pdf");
                    investmentPlanFileName = investmentPlanStream.getName();
                    if (getProject().getAnnexed() != null) {
                        annexedStream = new DefaultStreamedContent(new ByteArrayInputStream(getProject().getAnnexed()), "application/pdf", "Anexos.pdf");
                        annexedFileName = annexedStream.getName();
                    }
                    flagUpdate = true;
                }
                for (int i = 0; i < getProject().getObjectives().size(); i++) {
                    if (Objects.equals(getProject().getObjectives().get(i).getType(), Objectives.GENERAL_OBJETICVE)) {
                        generalObjectves.add(getProject().getObjectives().get(i));
                    } else {
                        specificObjectives.add(getProject().getObjectives().get(i));
                    }
                }
            } else {
                externalContext.redirect(externalContext.getRequestContextPath() + "/error/error-404.xhtml");
            }
        } catch (Exception e) {
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
        getProcess().setState(getRevisionState());
        changeStatusCorrection();
        uploadCreate();
        if (getProcess().getApprovedCareerCoordinator() == null) {
            tailFacade.createTailCoordinator(user, getProcess());
        } else if (getProcess().getApprovedCareerCoordinator()) {
            tailCommitteeEPSFacade.createTailCommiteeEPS(getProcess());
        }
        pdfFile = null;
        MessageUtils.addSuccessMessage("La solicitud de revision se ha realizado exitosamente.");
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
        } catch (IOException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    private void redirectToProcesses() throws IOException {
        externalContext.redirect(externalContext.getRequestContextPath() + "/process/processes.xhtml");
    }

    private void changeStatusCorrection() {
        if (getCorrections() != null) {
            for (int i = 0; i < getCorrections().size(); i++) {
                if (getCorrections().get(i).getStatus() == null) {
                    getCorrections().get(i).setStatus(true);
                } else if (getCorrections().get(i).getStatus().equals(true)) {
                    getCorrections().get(i).setStatus(false);
                }
            }
        }
    }

    public Boolean stateRevision() {
        Boolean value = false;
        if (getProcess().getState() == getRevisionState()) {
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
        if (getProcess().getState() != getRevisionState() && correction != null) {
            if (correction.getStatus() != null) {
                if (correction.getStatus() == true) {
                    value = true;
                }
            }
        }
        return value;
    }
}
