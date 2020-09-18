package gt.edu.usac.cunoc.ingenieria.eps.project.view;

import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_CARRERA;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_EPS;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SUPERVISOR_EPS;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import static gt.edu.usac.cunoc.ingenieria.eps.project.Objectives.GENERAL_OBJETICVE;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCommitteeEPSFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCoordinatorFacadeLocal;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.primefaces.PrimeFaces;

@Named
@ViewScoped
public class ProjectReviewView implements Serializable {

    @Inject
    private ExternalContext externalContext;

    @EJB
    private ProcessFacadeLocal processFacade;

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private ProjectFacadeLocal projectFacade;

    @EJB
    private TailCoordinatorFacadeLocal tailFacade;

    @EJB
    private TailCommitteeEPSFacadeLocal tailCommitteeEPSFacade;

    private Boolean isFirstProcess;

    private User user;

    private Integer processId;
    private Process actualProcess;

    private StreamedContent scheduleStream;
    private StreamedContent investmentPlanStream;
    private StreamedContent annexedStream;

    private String scheduleFileName = "";
    private String investmentPlanFileName = "";
    private String annexedFileName = "";

    private List<Correction> actualCorrections;

    private Correction newCorrection;
    private TypeCorrection currentTypeCorrection;
    private Section actualSection;

    @PostConstruct
    public void init() {
        actualCorrections = new ArrayList<>();
        newCorrection = new Correction();
    }

    public void loadCurrentProject() throws IOException {
        try {
            isFirstProcess = false;
            user = userFacade.getAuthenticatedUser().get(0);
            actualProcess = processFacade.getProcess(new Process(processId)).get(0);
            if (validateUser(actualProcess, user)) {
                scheduleStream = new DefaultStreamedContent(new ByteArrayInputStream(actualProcess.getProject().getSchedule()), "application/pdf", "Calendario.pdf");
                scheduleFileName = scheduleStream.getName();
                investmentPlanStream = new DefaultStreamedContent(new ByteArrayInputStream(actualProcess.getProject().getInvestmentPlan()), "application/pdf", "Plan de Inversion.pdf");
                investmentPlanFileName = investmentPlanStream.getName();
                if (actualProcess.getProject().getAnnexed() != null) {
                    annexedStream = new DefaultStreamedContent(new ByteArrayInputStream(actualProcess.getProject().getAnnexed()), "application/pdf", "Anexos.pdf");
                    annexedFileName = annexedStream.getName();
                }
                validateFirsProcess();
            } else {
                externalContext.redirect(externalContext.getRequestContextPath() + "/error/error-403.xhtml");
            }
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        } catch (IndexOutOfBoundsException ex) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/error/error-404.xhtml");
        }
    }

    private boolean validateUser(Process currentProcess, User userlogged) throws IOException {
        List<Process> assignedProcesses;
        switch (userlogged.getROLid().getName()) {
            case COORDINADOR_CARRERA:
                assignedProcesses = tailFacade.getProcessByCoordinator(userlogged);
                if (!existProcessOnList(currentProcess, assignedProcesses)) {
                    return false;
                }
                return true;
            case COORDINADOR_EPS:
                if (userlogged.getEpsCommittee()) {
                    assignedProcesses = tailCommitteeEPSFacade.getTailCommitteeEPS();
                    return existProcessOnList(currentProcess, assignedProcesses);
                } else {
                    return false;
                }
            case SUPERVISOR_EPS:
                if (userlogged.getEpsCommittee()) {
                    assignedProcesses = tailCommitteeEPSFacade.getTailCommitteeEPS();
                    return existProcessOnList(currentProcess, assignedProcesses);
                } else {
                    if (currentProcess.getSupervisor_EPS() != null){
                        return currentProcess.getSupervisor_EPS().getUserId().equals(userlogged.getUserId());
                    } else {
                        return false;
                    }                   
                }
            default:
                return false;
        }
    }

    private boolean existProcessOnList(Process process, List<Process> processes) {
        return processes.stream().anyMatch(assignedProcess -> (process.getId().compareTo(assignedProcess.getId()) == 0));
    }

    private void validateFirsProcess() {
        Process firstProcess;
        if (getActualProcess().getApprovedCareerCoordinator() != null
                && getActualProcess().getApprovalEPSCommission() != null
                && getActualProcess().getApprovedCareerCoordinator()
                && getActualProcess().getApprovalEPSCommission()) {
            this.isFirstProcess = true;
        } else {
            if (getActualProcess().getApprovedCareerCoordinator() == null) {
                firstProcess = tailFacade.getProcessByCoordinator(user).get(0);
            } else {
                firstProcess = tailCommitteeEPSFacade.getTailCommitteeEPS().get(0);
            }
            if (getActualProcess().equals(firstProcess)) {
                this.isFirstProcess = true;
            }
        }
    }

    public void comment(final String modalIdToClose) {
        if (newCorrection != null && newCorrection.getText() != null && !newCorrection.getText().isBlank()) {
            if (currentTypeCorrection != null) {
                try {
                    if (actualSection != null) {
                        newCorrection.setSection(actualSection);
                    }
                    newCorrection.setDate(LocalDate.now());
                    newCorrection.setProject(actualProcess.getProject());
                    newCorrection.setStatus(false);
                    newCorrection.setType(currentTypeCorrection);
                    newCorrection.setUser(user);
                    projectFacade.createCorrection(newCorrection);
                    PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
                    MessageUtils.addSuccessMessage("Corrección Guardada");
                    cleanCorrectionList();
                } catch (UserException | MandatoryException ex) {
                    MessageUtils.addErrorMessage(ex.getMessage());
                }
            } else {
                MessageUtils.addErrorMessage("Debe indicar a que componente hará la corrección");
            }
        } else {
            MessageUtils.addErrorMessage("Debe colocar texto en la corrección");
        }
    }

    public void aprovedProject() {
        try {
            projectFacade.searchUnnotifiedCorrections(actualProcess.getProject());
            if (getActualProcess().getApprovedCareerCoordinator() == null) {
                processFacade.rejectProcess(tailFacade.getTailCoordinator(getActualProcess()), "Proceso Eps Aceptado", "Su Anteproyecto ha sido aceptado por el coordinador de carrera.");
                tailFacade.deleteTailCoordinatod(getActualProcess());
                getActualProcess().setState(StateProcess.REVISION);
                getActualProcess().setApprovedCareerCoordinator(true);
                processFacade.assignEPSSUpervisorToProcess(getActualProcess().getUserCareer().getCAREERcodigo(), getActualProcess());
                processFacade.updateProcess(getActualProcess());
                tailCommitteeEPSFacade.createTailCommiteeEPS(getActualProcess());
            } else if (getActualProcess().getApprovedCareerCoordinator()) {
                processFacade.aproveedByEPSCommittee(actualProcess.getId());
            }
            redirectToProcesses();
        } catch (UserException | LimitException | MandatoryException | ValidationException | IOException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    public void rejectProcess() {
        try {
            projectFacade.searchUnnotifiedCorrections(actualProcess.getProject());
            if (getActualProcess().getApprovedCareerCoordinator() == null) {
                processFacade.rejectProcess(tailFacade.getTailCoordinator(getActualProcess()), "Proceso Eps Rechazado", newCorrection.getText());
                tailFacade.deleteTailCoordinatod(getActualProcess());
                getActualProcess().setState(StateProcess.RECHAZADO);
                getActualProcess().setApprovedCareerCoordinator(false);
                processFacade.updateProcess(getActualProcess());
            } else if (getActualProcess().getApprovedCareerCoordinator()) {
                processFacade.EPSCommitteeRejectProyect(
                        actualProcess.getId(),
                        userFacade.getAuthenticatedUser().get(0),
                        newCorrection.getText()
                );
            }
            redirectToProcesses();
            newCorrection.setType(TypeCorrection.REJECTED);
            newCorrection.setDate(LocalDate.now());
            newCorrection.setProject(actualProcess.getProject());
            newCorrection.setStatus(true);
            newCorrection.setUser(user);
            projectFacade.createCorrection(newCorrection);
        } catch (UserException | MandatoryException | IOException | ValidationException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    public void returnCorrections() {
        try {
            if (getActualProcess().getApprovedCareerCoordinator() == null) {
                processFacade.rejectProcess(tailFacade.getTailCoordinator(getActualProcess()), "Revisión Proceso Eps", "Su Anteproyecto ha sido revisado por el Coordinador de Carrera, ya es posible editar el documento y realizar los cambios solicitados.");
                tailFacade.deleteTailCoordinatod(getActualProcess());
                getActualProcess().setState(StateProcess.ACTIVO);
                processFacade.updateProcess(getActualProcess());
                projectFacade.returnCorrections(getActualProcess().getProject());
            } else if (getActualProcess().getApprovedCareerCoordinator()) {
                processFacade.returnEPSCommitteeRevisionToStudent(actualProcess.getId());
                projectFacade.returnCorrections(getActualProcess().getProject());
            }
            redirectToProcesses();
        } catch (UserException | IOException | MandatoryException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    private void redirectToProcesses() throws IOException {
        externalContext.redirect(externalContext.getRequestContextPath() + "/process/processesReview.xhtml");
    }

    public String titlePage() {
        String value = "Anteproyecto";
        if (getActualProcess().getApprovalEPSCommission() != null && getActualProcess().getApprovalEPSCommission()) {
            value = "Proyecto";
        }
        return value;
    }

    public String correctionTitle() {
        if (currentTypeCorrection != null) {
            return currentTypeCorrection.toText();
        } else {
            MessageUtils.addErrorMessage("Debe colocar texto en la corrección");
        }
        return null;
    }

    public Boolean renderWarningCoordinator() {
        Boolean value = false;
        if (getActualProcess().getState() == StateProcess.REVISION && !getIsFirstProcess()) {
            value = true;
        }
        return value;
    }

    public boolean isGeneralObjective(Objectives objective) {
        return objective.getType().equals(GENERAL_OBJETICVE);
    }

    private void getCommentaries(TypeCorrection typeCorrection, Section section) {
        currentTypeCorrection = typeCorrection;
        actualSection = section;
        actualCorrections.clear();
        if (actualSection == null) {
            setActualCorrections(projectFacade.getCorrections(typeCorrection, actualProcess.getProject().getId(), null, null));
        } else {
            setActualCorrections(projectFacade.getCorrections(typeCorrection, actualProcess.getProject().getId(), section.getId(), null));
        }
    }

    public void titleComments() {
        getCommentaries(TypeCorrection.TITLE, null);
    }

    public void generalObjComments() {
        getCommentaries(TypeCorrection.GENERAL_OBJETIVES, null);
    }

    public void specificObjComments() {
        getCommentaries(TypeCorrection.SPECIFIC_OBJETIVES, null);
    }

    public void sectionComments(Section section) {
        getCommentaries(TypeCorrection.SECTION, section);
    }

    public void coordinatesComments() {
        getCommentaries(TypeCorrection.COORDINATE, null);
    }

    public void calendarComments() {
        getCommentaries(TypeCorrection.SCHEDULE, null);
    }

    public void inversionComments() {
        getCommentaries(TypeCorrection.INVESTMENT_PLAN, null);
    }

    public void bibliographyComments() {
        getCommentaries(TypeCorrection.BIBLIOGRAPHY, null);
    }

    public void anexosComments() {
        getCommentaries(TypeCorrection.ANNEXED, null);
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer porcessId) {
        this.processId = porcessId;
    }

    public Process getActualProcess() {
        return actualProcess;
    }

    public void setActualProcess(Process actualProcess) {
        this.actualProcess = actualProcess;
    }

    public Boolean getIsFirstProcess() {
        return isFirstProcess;
    }

    public void setIsFirstProcess(Boolean isFirstProcess) {
        this.isFirstProcess = isFirstProcess;
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

    public String getScheduleFileName() {
        return scheduleFileName;
    }

    public void setScheduleFileName(String scheduleFileName) {
        this.scheduleFileName = scheduleFileName;
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

    public void reloadSchedule() {
        this.scheduleStream = new DefaultStreamedContent(new ByteArrayInputStream(actualProcess.getProject().getSchedule()), "application/pdf", "Calendario.pdf");
    }

    public void reloadInvestmentPlan() {
        this.investmentPlanStream = new DefaultStreamedContent(new ByteArrayInputStream(actualProcess.getProject().getInvestmentPlan()), "application/pdf", "Plan de Inversión.pdf");
    }

    public void reloadAnnexed() {
        this.annexedStream = new DefaultStreamedContent(new ByteArrayInputStream(actualProcess.getProject().getAnnexed()), "application/pdf", "Anexos.pdf");
    }

    public List<Correction> getActualCorrections() {
        return actualCorrections;
    }

    public void setActualCorrections(List<Correction> actualCorrections) {
        this.actualCorrections = actualCorrections;
    }

    public Correction getNewCorrection() {
        return newCorrection;
    }

    public void setNewCorrection(Correction newCorrection) {
        this.newCorrection = newCorrection;
    }

    public void cleanCorrectionList() {
        actualSection = null;
        init();
    }
}
