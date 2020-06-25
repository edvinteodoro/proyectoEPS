package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import static gt.edu.usac.cunoc.ingenieria.eps.project.Objectives.GENERAL_OBJETICVE;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection;
import static gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection.OTHER;
import gt.edu.usac.cunoc.ingenieria.eps.project.facade.ProjectFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author angelrg
 */
@Named
@ViewScoped
public class committeeEPSReviewView implements Serializable {

    @Inject
    private ExternalContext externalContext;

    @EJB
    private ProcessFacadeLocal processFacade;

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private ProjectFacadeLocal projectFacade;

    private Process actualProcess;

    private Integer processId;
    private StreamedContent scheduleStream;
    private StreamedContent investmentPlanStream;
    private StreamedContent annexedStream;

    private String scheduleFileName = "";
    private String investmentPlanFileName = "";
    private String annexedFileName = "";

    private List<Correction> actualCorrections;
    private List<Correction> temporalCorrections = new LinkedList<>();

    private Correction newCorrection;
    private String correctionMessage;
    private TypeCorrection actualCorrection;
    private Section actualSection;

    public void loadCurrentProject() {
        actualProcess = null;
        Optional<Process> result;
        try {
            result = processFacade.ProcessAvailableToEPSCommittee(processId);
            if (result.isPresent()) {
                actualProcess = result.get();

                if (actualProcess != null) {
                    scheduleStream = new DefaultStreamedContent(new ByteArrayInputStream(actualProcess.getProject().getSchedule()), "application/pdf", "Calendario.pdf");
                    scheduleFileName = scheduleStream.getName();
                    investmentPlanStream = new DefaultStreamedContent(new ByteArrayInputStream(actualProcess.getProject().getInvestmentPlan()), "application/pdf", "Plan de Inversion.pdf");
                    investmentPlanFileName = investmentPlanStream.getName();
                    if (actualProcess.getProject().getAnnexed() != null) {
                        annexedStream = new DefaultStreamedContent(new ByteArrayInputStream(actualProcess.getProject().getAnnexed()), "application/pdf", "Anexos.pdf");
                        annexedFileName = annexedStream.getName();
                    }
                }
            } else {
                MessageUtils.addErrorMessage("No se ha encontrado el Proceso");
            }
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    public void titleComments() {
        getCommentaries(TypeCorrection.TITLE, null);
    }

    public void generalObjComments() {
        getCommentaries(TypeCorrection.OBJETIVES, null);
    }

    public void specificObjComments() {
        getCommentaries(TypeCorrection.SPECIFIC_OBJETIVES, null);
    }

    public void sectionComments(Section section) {
        getCommentaries(TypeCorrection.OTHER, section);
    }

    public void coordinatesComments() {
        getCommentaries(TypeCorrection.COORDINATE, null);
    }

    public void calendarComments() {
        getCommentaries(TypeCorrection.CALENDAR, null);
    }

    public void inversionComments() {
        getCommentaries(TypeCorrection.PLAN, null);
    }

    public void bibliographyComments() {
        getCommentaries(TypeCorrection.BIBLIOGRAPHY, null);
    }

    public void anexosComments() {
        getCommentaries(TypeCorrection.ANEXO, null);
    }

    private void getCommentaries(TypeCorrection correction, Section section) {

        actualCorrection = correction;
        actualSection = section;

        if (actualCorrections != null) {
            actualCorrections.clear();
        }

        switch (correction) {
            case BIBLIOGRAPHY:
            case OBJETIVES:
            case SPECIFIC_OBJETIVES:
            case CALENDAR:
            case PLAN:
            case ANEXO:
            case TITLE:
            case COORDINATE:
                setActualCorrections(projectFacade.getCorrections(correction, actualProcess.getProject().getId(), null));
                try {
                    newCorrection = actualTempCorrectionText();
                    if (!arrayToString(newCorrection.getText()).isEmpty()) {
                        setCorrectionMessage(arrayToString(newCorrection.getText()));
                    }
                } catch (UserException e) {
                    MessageUtils.addErrorMessage(e.getMessage());
                }
                break;

            case OTHER:
                setActualCorrections(projectFacade.getCorrections(correction, actualProcess.getProject().getId(), actualSection.getId()));
                try {
                    newCorrection = actualTempCorrectionText();
                    if (!arrayToString(newCorrection.getText()).isEmpty()) {
                        setCorrectionMessage(arrayToString(newCorrection.getText()));
                    }
                } catch (UserException e) {
                    MessageUtils.addErrorMessage(e.getMessage());
                }
                break;
        }
    }

    public boolean isGeneralObjective(Objectives objective) {
        return objective.getType().equals(GENERAL_OBJETICVE);
    }

    public void comment(final String modalIdToClose) {
        if (newCorrection != null && correctionMessage != null && !correctionMessage.isEmpty()) {
            if (actualCorrection != null) {
                switch (actualCorrection) {
                    case BIBLIOGRAPHY:
                    case OBJETIVES:
                    case SPECIFIC_OBJETIVES:
                    case CALENDAR:
                    case PLAN:
                    case ANEXO:
                    case TITLE:
                    case COORDINATE:
                        newCorrection.setText(correctionMessage.getBytes());
                        temporalCorrections.add(newCorrection);
                        break;
                    case OTHER:
                        if (actualSection != null) {
                            newCorrection.setText(correctionMessage.getBytes());
                            temporalCorrections.add(newCorrection);
                        } else {
                            MessageUtils.addErrorMessage("Debe elegir una sección");
                        }
                        break;
                }
                PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
            } else {
                MessageUtils.addErrorMessage("Debe indicar el dueño de la corrección");
            }
        } else {
            MessageUtils.addErrorMessage("Debe colocar texto en la corrección");
        }
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

    public String arrayToString(byte[] text) {
        String result = new String(text);
        return result;
    }

    public String correctionTitle() {
        if (actualCorrection != null) {
            switch (actualCorrection) {
                case BIBLIOGRAPHY:
                case OBJETIVES:
                case SPECIFIC_OBJETIVES:
                case CALENDAR:
                case PLAN:
                case ANEXO:
                case TITLE:
                case COORDINATE:
                case REJECTED:
                    return actualCorrection.toText();
                case OTHER:
                    if (actualSection != null) {
                        return actualSection.getTitle().getName();
                    } else {
                        MessageUtils.addErrorMessage("Debe colocar texto en la corrección");
                    }
            }
        } else {
            MessageUtils.addErrorMessage("Debe colocar texto en la corrección");
        }
        return null;
    }

    private Correction actualTempCorrectionText() throws UserException {
        if (!temporalCorrections.isEmpty()) {
            for (Correction correction : temporalCorrections) {
                if (correction.getType() == actualCorrection) {
                    if (correction.getType() != OTHER) {
                        return correction;
                    } else {
                        if (actualSection != null && correction.getSection().getId().equals(actualSection.getId())) {
                            return correction;
                        }
                    }
                }
            }
        }

        try {
            if (actualSection == null) {
                return new Correction(LocalDate.now(), userFacade.getAuthenticatedUser().get(0), actualCorrection, actualProcess.getProject(), null, "".getBytes());
            } else {
                return new Correction(LocalDate.now(), userFacade.getAuthenticatedUser().get(0), actualCorrection, actualProcess.getProject(), actualSection, "".getBytes());
            }
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
        throw new UserException("No es posible crear un comentario");
    }

    public void saveCorrections() {
        try {
            if (temporalCorrections != null && !temporalCorrections.isEmpty()) {
                for (Correction temporalCorrection : temporalCorrections) {
                    projectFacade.createCorrection(temporalCorrection);
                }
                redirectToEPSCommittee();
            } else {
                MessageUtils.addErrorMessage("No existen correcciones para guardar");
            }
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    public void returnEPSCommitteeCorrections() {
        try {
            processFacade.returnEPSCommitteeRevisionToStudent(actualProcess.getId());
            redirectToEPSCommittee();
        } catch (UserException e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    public void aproveByEPSCommittee() {
        try {
            processFacade.aproveedByEPSCommittee(actualProcess.getId());
            redirectToEPSCommittee();
        } catch (Exception e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    public void rejectByEPSCommittee() {
        try {
            processFacade.EPSCommitteeRejectProyect(
                    actualProcess.getId(),
                    userFacade.getAuthenticatedUser().get(0),
                    correctionMessage
            );
            redirectToEPSCommittee();
        } catch (Exception e) {
            MessageUtils.addErrorMessage(e.getMessage());
        }
    }

    private void redirectToEPSCommittee() {
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/committeeEPS/processesCommitteeEPS.xhtml");
        } catch (IOException e) {
            MessageUtils.addErrorMessage("Error to redirect");
        }
    }
    
    public Boolean listTemporalCorrection(){
        return (temporalCorrections == null || temporalCorrections.isEmpty());
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

    public List<Correction> getActualCorrections() {
        return actualCorrections;
    }

    public void setActualCorrections(List<Correction> actualCorrections) {
        this.actualCorrections = actualCorrections;
    }

    public String getCorrectionMessage() {
        return correctionMessage;
    }

    public void setCorrectionMessage(String correctionMessage) {
        this.correctionMessage = correctionMessage;
    }

    public void cleanCorrectionList() {
        actualSection = null;
        setActualCorrections(null);
        correctionMessage = "";
    }

}
