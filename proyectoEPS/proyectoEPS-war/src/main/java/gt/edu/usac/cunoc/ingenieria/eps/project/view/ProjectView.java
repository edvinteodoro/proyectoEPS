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
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection;
import static gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection.ANEXO;
import static gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection.BIBLIOGRAPHY;
import static gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection.CALENDAR;
import static gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection.COORDINATE;
import static gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection.OBJETIVES;
import static gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection.OTHER;
import static gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection.PLAN;
import static gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection.REJECTED;
import static gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection.SPECIFIC_OBJETIVES;
import static gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection.TITLE;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCoordinator;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCommitteeEPSFacadeLocal;
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
import java.util.Objects;
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

    @EJB
    private TailCommitteeEPSFacadeLocal tailCommitteeEPSFacade;

    private User user;
    private User coordinator;

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

    private Project project;
    private Process process;
    private TailCoordinator tailCoordiantor;
    private List<Objectives> generalObjectves;
    private List<Objectives> specificObjectives;
    private List<Correction> corrections;
    private Integer processId;
    private Boolean flagUpdate = false;
    private Boolean isFirstProcess = false;
    private Correction correctionSelected;
    private String comment;
    private String textObser;
    private StateProcess revisionState;

    //corrections
    private Correction correctionTitle;
    private Correction correctionPlan;
    private Correction correctionAnexo;
    private Correction correctionCalendar;
    private Correction correctionCoordinates;
    private Correction correctionObjetives;
    private Correction correctionSpecificObjetives;
    private Correction correctionSection;
    private Correction correctionBibliography;
    private Correction rejectProcess;

    @PostConstruct
    public void init() {
        try {
            generalObjectves = new ArrayList<>();
            specificObjectives = new ArrayList<>();
            revisionState = StateProcess.REVISION;
            user = userFacade.getAuthenticatedUser().get(0);
            if (isStuden()) {
                textObser = "Ver Observaciones";
            } else {
                textObser = "Realizar Observaciones";
            }
        } catch (Exception e) {
        }
    }

    public String getTextObser() {
        return textObser;
    }

    public void setTextObser(String textObser) {
        this.textObser = textObser;
    }

    public Boolean getIsFirstProcess() {
        return isFirstProcess;
    }

    public void setIsFirstProcess(Boolean isFirstProcess) {
        this.isFirstProcess = isFirstProcess;
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
        correctionTitle = getCorrection(TITLE);
        if (correctionTitle == null && getCorrections() != null) {
            getCorrections().add(new Correction(LocalDate.now(), user, TITLE, getProject()));
            correctionTitle = getCorrections().get(getCorrections().size() - 1);
        }
        return correctionTitle;
    }

    public void setCorrectionTitle(Correction correctionTitle) {
        this.correctionTitle = correctionTitle;
    }

    public Correction getCorrectionPlan() {
        correctionPlan = getCorrection(PLAN);
        if (getCorrection(PLAN) == null && getCorrections() != null) {
            getCorrections().add(new Correction(LocalDate.now(), user, PLAN, getProject()));
            correctionPlan = getCorrections().get(getCorrections().size() - 1);
        }
        return correctionPlan;
    }

    public void setCorrectionPlan(Correction correctionPlan) {
        this.correctionPlan = correctionPlan;

    }

    public Correction getCorrectionAnexo() {
        correctionAnexo = getCorrection(ANEXO);
        if (correctionAnexo == null && getCorrections() != null) {
            getCorrections().add(new Correction(LocalDate.now(), user, ANEXO, getProject()));
            correctionAnexo = getCorrections().get(getCorrections().size() - 1);
        }
        return correctionAnexo;
    }

    public void setCorrectionAnexo(Correction correctionAnexo) {
        this.correctionAnexo = correctionAnexo;

    }

    public Correction getCorrectionCalendar() {
        correctionCalendar = getCorrection(CALENDAR);
        if (correctionCalendar == null && getCorrections() != null) {
            getCorrections().add(new Correction(LocalDate.now(), user, CALENDAR, getProject()));
            correctionCalendar = getCorrections().get(getCorrections().size() - 1);
        }
        return correctionCalendar;
    }

    public void setCorrectionCalendar(Correction correctionCalendar) {
        this.correctionCalendar = correctionCalendar;
    }

    public Correction getCorrectionCoordinates() {
        correctionCoordinates = getCorrection(COORDINATE);
        if (correctionCoordinates == null && getCorrections() != null) {
            getCorrections().add(new Correction(LocalDate.now(), user, COORDINATE, getProject()));
            correctionCoordinates = getCorrections().get(getCorrections().size() - 1);
        }
        return correctionCoordinates;
    }

    public void setCorrectionCoordinates(Correction correctionCoordinates) {
        this.correctionCoordinates = correctionCoordinates;
    }

    public Correction getCorrectionObjetives() {
        correctionObjetives = getCorrection(OBJETIVES);
        if (correctionObjetives == null && getCorrections() != null) {
            getCorrections().add(new Correction(LocalDate.now(), user, OBJETIVES, getProject()));
            correctionObjetives = getCorrections().get(getCorrections().size() - 1);
        }
        return correctionObjetives;
    }

    public void setCorrectionObjetives(Correction correctionObjetives) {
        this.correctionObjetives = correctionObjetives;
    }

    public Correction getCorrectionSpecificObjetives() {
        correctionSpecificObjetives = getCorrection(SPECIFIC_OBJETIVES);
        if (correctionSpecificObjetives == null && getCorrections() != null) {
            getCorrections().add(new Correction(LocalDate.now(), user, SPECIFIC_OBJETIVES, getProject()));
            correctionSpecificObjetives = getCorrections().get(getCorrections().size() - 1);
        }
        return correctionSpecificObjetives;
    }

    public void setCorrectionSpecificObjetives(Correction correctionSpecificObjetives) {
        this.correctionSpecificObjetives = correctionSpecificObjetives;
    }

    public Correction getCorrectionBibliography() {
        correctionBibliography = getCorrection(BIBLIOGRAPHY);
        if (correctionBibliography == null && getCorrections() != null) {
            getCorrections().add(new Correction(LocalDate.now(), user, BIBLIOGRAPHY, getProject()));
            correctionBibliography = getCorrections().get(getCorrections().size() - 1);
        }
        return correctionBibliography;
    }

    public void setCorrectionBibliography(Correction correctionBibliography) {
        this.correctionBibliography = correctionBibliography;
    }

    public Correction getRejectProject() {
        rejectProcess = getCorrection(REJECTED);
        if (rejectProcess == null && getCorrections() != null) {
            getCorrections().add(new Correction(LocalDate.now(), user, REJECTED, getProject(), Boolean.TRUE));
            rejectProcess = getCorrections().get(getCorrections().size() - 1);
        }
        return rejectProcess;
    }

    public void setRejectProject(Correction rejectProcess) {
        this.rejectProcess = rejectProcess;
    }

    public Correction getCorrectionSection(Section section) {
        correctionSection = getCorrectionSection1(section);
        if (correctionSection == null && getCorrections() != null) {
            getCorrections().add(new Correction(LocalDate.now(), user, OTHER, getProject(), section));
            correctionSection = getCorrections().get(getCorrections().size() - 1);
        }
        return correctionSection;
    }

    public void setCorrectionSection(Correction correctionSection) {
        this.correctionSection = correctionSection;
    }

    public List<Correction> getCorrections() {
        return corrections;
    }

    public void setCorrections(List<Correction> corrections) {
        this.corrections = corrections;
    }

    public Correction getCorrectionSection1(Section section) {
        if (getCorrections() != null) {
            for (int i = 0; i < getCorrections().size(); i++) {
                if (getCorrections().get(i).getSection() != null) {
                    if (getCorrections().get(i).getSection().equals(section)) {
                        if (getCorrections().get(i).getStatus() == null) {
                            return getCorrections().get(i);
                        } else if (getCorrections().get(i).getStatus() == true) {
                            return getCorrections().get(i);
                        }
                    }
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public Correction getCorrection(TypeCorrection type) {
        Correction value = null;
        if (getCorrections() != null) {
            for (int i = 0; i < getCorrections().size(); i++) {
                if (getCorrections().get(i).getType().equals(type)) {
                    if (getCorrections().get(i).getStatus() == null) {
                        value = getCorrections().get(i);
                    } else if (getCorrections().get(i).getStatus() == true) {
                        value = getCorrections().get(i);
                    }
                }
            }
        }
        return value;
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

    public StreamedContent getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(StreamedContent pdfFile) {
        this.pdfFile = pdfFile;
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
            clearCorrections();
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
        if (!isStuden()) {
            Process firstProcess = tailFacade.getProcessByCoordinator(user).get(0);
            if (getProcess().equals(firstProcess)) {
                isFirstProcess = true;
            }
        }
        //coordinator = userFacade.getCareerCoordinator(getProcess()).get(0);
        //System.out.println(coordinator.getFirstName());
        this.project = process.getProject();
        if (project != null) {
            this.corrections = project.getCorrections();
        }
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
            if (Objects.equals(project.getObjectives().get(i).getType(), Objectives.GENERAL_OBJETICVE)) {
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
        String value = "";
        if (getCorrectionSelected() != null) {
            if (getCorrectionSelected().getText() != null) {
                if (getCorrectionSelected().getStatus() == null) {
                    value = new String(getCorrectionSelected().getText());
                } else if (getCorrectionSelected().getStatus() == true) {
                    value = new String(getCorrectionSelected().getText());
                }
            }
        }
        return value;
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
        clearCorrections();
        getProcess().setState(getRevisionState());
        changeStatusCorrection();
        processFacade.updateProcess(getProcess());
        if (!getProcess().getApprovedCareerCoordinator()) {
            tailFacade.createTailCoordinator(user, getProcess());
        } else {
            tailCommitteeEPSFacade.createTailCommiteeEPS(getProcess());
        }
        MessageUtils.addSuccessMessage("La solicitud de revision se ha realizado exitosamente.");
    }

    public void acceptChanges() {
        try {
            getProcess().setApprovedCareerCoordinator(true);
            clearCorrections();
            changeStatusCorrection();
            processFacade.rejectProcess(tailFacade.getTailCoordianteor(getProcess()), "Proceso Eps Aceptado", "Su projecto ha sido aceptado por el coordinador de carrera.");
            tailFacade.deleteTailCoordinatod(getProcess());
            getProcess().setState(StateProcess.ACTIVO);
            processFacade.updateProcess(getProcess());
            getProcess().setState(StateProcess.REVISION);
            processFacade.updateProcess(getProcess());
            tailCommitteeEPSFacade.createTailCommiteeEPS(process);
            redirectToProcesses();
        } catch (Exception e) {

        }
    }

    public void finishReview() {
        try {
            clearCorrections();
            changeStatusCorrection();
            processFacade.rejectProcess(tailFacade.getTailCoordianteor(getProcess()), "Revision Proceso Eps", "Su projecto ha sido revisado, ya es posible editar el documento y realizar los cambios solicitados.");
            tailFacade.deleteTailCoordinatod(getProcess());
            getProcess().setState(StateProcess.ACTIVO);
            processFacade.updateProcess(getProcess());
            redirectToProcesses();
        } catch (Exception e) {
            MessageUtils.addErrorMessage("No se pudo finalizar la revision");
        }
    }

    public void rejectThisProject() {
        try {
            clearCorrections();
            changeStatusCorrection();
            processFacade.rejectProcess(tailFacade.getTailCoordianteor(getProcess()), "Proceso Eps Rechazado", new String(getCorrectionSelected().getText()));
            tailFacade.deleteTailCoordinatod(getProcess());
            getProcess().setState(StateProcess.RECHAZADO);
            processFacade.updateProcess(getProcess());
            redirectToProcesses();
        } catch (Exception e) {
            MessageUtils.addErrorMessage("No se pudo rechazar el proyecto");
        }
    }

    public void createPDF() {
        try {
            this.pdfFile = new DefaultStreamedContent(projectFacade.createPDF(project, process.getUserCareer()), "application/pdf", getProject().getTitle());
            MessageUtils.addSuccessMessage("Archivo PDF Generado");
        } catch (IOException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    public Boolean isStuden() {
        return user.getROLid().getName().equals(ESTUDIANTE);
    }

    private void clearCorrections() {
        if (getCorrections() != null) {
            for (int i = 0; i < corrections.size(); i++) {
                if (getCorrections().get(i).getText() == null) {
                    getCorrections().remove(i);
                    i--;
                }
            }
        }
    }

    public void comment(final String modalIdToClose) {
        try {
            if (getCorrectionSelected().getText() != null) {
                getCorrectionSelected().setStatus(null);
                String text = new String(getCorrectionSelected().getText());
                if (text.isEmpty()) {
                    getCorrectionSelected().setText(null);
                }
            }
            clearCorrections();
            //getCorrections().add(correction);
            projectFacade.updateProject(getProject());
            clearCorrectionSelected();
            PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
        } catch (Exception e) {
            System.out.println(e);
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

    public String getListCorrection() {
        String value = "";
        if (getCorrectionSelected() != null) {
            for (int i = 0; i < getCorrections().size(); i++) {
                if (getCorrections().get(i).getType().equals(getCorrectionSelected().getType()) && !getCorrections().get(i).getTextHistory().isEmpty()) {
                    value = getCorrections().get(i).getTextHistory();
                }
            }
        }
        return value;
    }

    public Boolean stateRevision() {
        Boolean value = false;
        if (isStuden() && getProcess().getState() == getRevisionState()) {
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
        if (isStuden() && stateActived()) {
            value = true;
        }
        return value;
    }

    public Boolean showButtonRevision(Correction correction) {
        Boolean value = false;
        if (isStuden() && getProcess().getState() != getRevisionState() && correction != null) {
            if (correction.getStatus() != null) {
                if (correction.getStatus() == true) {
                    value = true;
                }
            }
        } else if (!isStuden() && getIsFirstProcess()) {
            value = true;
        }
        return value;
    }

    public Boolean renderWarningCoordinator() {
        Boolean value = false;
        if (!isStuden() && getProcess().getState() == getRevisionState() && !getIsFirstProcess()) {
            value = true;
        }
        return value;
    }

    public void acceptChanges(final String modalIdToClose) {
//        try {
//            if(getProject().getCorrectionTitle()==getCorrectionSelected()){
//                getProject().setCorrectionTitle(null);
//            }
//            PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
//        } catch (Exception e) {
//        }
    }
}
