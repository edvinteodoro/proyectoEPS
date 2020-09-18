package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_CARRERA;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_EPS;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ESTUDIANTE;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SUPERVISOR_EPS;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Observation;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCommitteeEPSFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailCoordinatorFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class updateRequerimentsView implements Serializable {

    @Inject
    private ExternalContext externalContext;

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private ProcessFacadeLocal processFacade;

    @EJB
    private TailCoordinatorFacadeLocal tailFacade;

    @EJB
    private TailCommitteeEPSFacadeLocal tailCommitteeEPSFacade;

    private Integer processId;
    private Process process;
    private Requeriment requeriment;

    private User loggedUser;

    private StreamedContent writtenRequestStream;
    private StreamedContent inscriptionConstancyStream;
    private StreamedContent pensumeClosureStream;
    private StreamedContent propedeuticConstancyStream;
    private StreamedContent epsPreProjectStream;
    private StreamedContent aeioSettlementStream;

    private UploadedFile writtenRequest;
    private UploadedFile inscriptionConstancy;
    private UploadedFile pensumeClosure;
    private UploadedFile propedeuticConstancy;
    private UploadedFile epsPreProject;
    private UploadedFile aeioSettlement;

    String nameWrittenRequest = "";
    String nameInscriptionConstancy = "";
    String namePensumeClosure = "";
    String namePropedeuticConstancy = "";
    String nameEpsPreProjec = "";
    String nameAeioSettlemen = "";

    private boolean flagStudent;
    private Boolean showAeioSettlement;

    private String observation = "";

    @PostConstruct
    public void init() {
        flagStudent = false;
        showAeioSettlement = false;
    }

    public void loadCurrentProcess() throws IOException {
        try {
            this.loggedUser = userFacade.getAuthenticatedUser().get(0);
            this.process = processFacade.getProcess(new Process(processId)).get(0);
            this.requeriment = process.getRequeriment();
            if (validateUser(process, loggedUser)) {
                loadDocuments();
            } else {
                externalContext.redirect(externalContext.getRequestContextPath() + "/error/error-403.xhtml");
            }
        } catch (UserException ex) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/error/error-403.xhtml");
        } catch (ArrayIndexOutOfBoundsException ex) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/error/error-404.xhtml");
        }
    }

    private boolean validateUser(Process currentProcess, User userlogged) throws IOException {
        List<Process> assignedProcesses;
        switch (userlogged.getROLid().getName()) {
            case ESTUDIANTE:
                 return process.getUserCareer().getUSERuserId().equals(this.loggedUser);                 
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
                    if (currentProcess.getSupervisor_EPS() != null) {
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

    public String getTitle() {
        switch (loggedUser.getROLid().getName()) {
            case ESTUDIANTE:
                return "Actualizar Requerimientos";
            case COORDINADOR_CARRERA:
            case COORDINADOR_EPS:
            case SUPERVISOR_EPS:
                return "Revisión Requerimientos";
            default:
                return "Requerimientos";
        }
    }

    public void save() {
        if (writtenRequest != null) {
            getRequeriment().setWrittenRequest(writtenRequest.getContents());
        }
        if (inscriptionConstancy != null) {
            getRequeriment().setInscriptionConstancy(inscriptionConstancy.getContents());
        }
        if (pensumeClosure != null) {
            getRequeriment().setPensumClosure(pensumeClosure.getContents());
        }
        if (propedeuticConstancy != null) {
            getRequeriment().setPropedeuticConstancy(propedeuticConstancy.getContents());
        }
        if (epsPreProject != null) {
            getRequeriment().setEPSpreproject(epsPreProject.getContents());
        }
        if (aeioSettlement != null) {
            getRequeriment().setAEIOsettlement(aeioSettlement.getContents());
        } else {
            getRequeriment().setAEIOsettlement(null);
        }
        processFacade.updaterequeriment(getRequeriment());
        MessageUtils.addSuccessMessage("Cambios Realizados");
    }

    private void loadDocuments() {
        this.process = processFacade.getProcess(new Process(processId)).get(0);
        this.requeriment = process.getRequeriment();
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getWrittenRequest()), "application/pdf", "Solicitud Escrita.pdf");
        inscriptionConstancyStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getInscriptionConstancy()), "application/pdf", "Constancia Inscripcion.pdf");
        pensumeClosureStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getPensumClosure()), "application/pdf", "Solicitud Escrita.pdf");
        propedeuticConstancyStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getPropedeuticConstancy()), "application/pdf", "Solicitud Escrita.pdf");
        epsPreProjectStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getEPSpreproject()), "application/pdf", "Solicitud Escrita.pdf");
        if (requeriment.getAEIOsettlement() != null) {
            showAeioSettlement = true;
            aeioSettlementStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getaEIOsettlement()), "application/pdf", "Solicitud Escrita.pdf");
        }
    }

    public void loadDocumentsMessage() {
        loadDocuments();
        MessageUtils.addSuccessMessage("Documentos Reestablecidos");
    }

    public void saveObservation() {
        try {
            Observation newObservation = new Observation();
            newObservation.setDate(LocalDate.now());
            newObservation.setText(this.observation);
            newObservation.setUser(loggedUser);
            newObservation.setRequeriment(requeriment);
            processFacade.createObservation(newObservation);
            MessageUtils.addSuccessMessage("Observación Realizada");
            this.observation = "";
        } catch (MandatoryException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    public List<Observation> getRequerimentsObservations(Integer requerimentId) {
        return processFacade.getRequerimentsObservations(requerimentId);
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Requeriment getRequeriment() {
        return requeriment;
    }

    public void setRequeriment(Requeriment requeriment) {
        this.requeriment = requeriment;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public StreamedContent getWrittenRequestStream() {
        return writtenRequestStream;
    }

    public void setWrittenRequestStream(StreamedContent writtenRequest) {
        this.writtenRequestStream = writtenRequest;
    }

    public StreamedContent getInscriptionConstancyStream() {
        return inscriptionConstancyStream;
    }

    public void setInscriptionConstancyStream(StreamedContent inscriptionConstancy) {
        this.inscriptionConstancyStream = inscriptionConstancy;
    }

    public StreamedContent getPensumeClosureStream() {
        return pensumeClosureStream;
    }

    public void setPensumeClosureStream(StreamedContent pensumeClosure) {
        this.pensumeClosureStream = pensumeClosure;
    }

    public StreamedContent getPropedeuticConstancyStream() {
        return propedeuticConstancyStream;
    }

    public void setPropedeuticConstancyStream(StreamedContent propedeuticConstancy) {
        this.propedeuticConstancyStream = propedeuticConstancy;
    }

    public StreamedContent getEpsPreProjectStream() {
        return epsPreProjectStream;
    }

    public void setEpsPreProjectStream(StreamedContent epsPreProject) {
        this.epsPreProjectStream = epsPreProject;
    }

    public StreamedContent getAeioSettlementStream() {
        return aeioSettlementStream;
    }

    public void setAeioSettlementStream(StreamedContent aeioSettlement) {
        this.aeioSettlementStream = aeioSettlement;
    }

    public void handleWrittenRequest(FileUploadEvent event) {
        writtenRequest = event.getFile();
        requeriment.setWrittenRequest(writtenRequest.getContents());
        nameWrittenRequest = event.getFile().getFileName();
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(writtenRequest.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handleInscriptionConstancy(FileUploadEvent event) {
        inscriptionConstancy = event.getFile();
        requeriment.setInscriptionConstancy(inscriptionConstancy.getContents());
        nameInscriptionConstancy = event.getFile().getFileName();
        inscriptionConstancyStream = new DefaultStreamedContent(new ByteArrayInputStream(inscriptionConstancy.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handlePensumeClosure(FileUploadEvent event) {
        pensumeClosure = event.getFile();
        requeriment.setPensumClosure(pensumeClosure.getContents());
        namePensumeClosure = event.getFile().getFileName();
        pensumeClosureStream = new DefaultStreamedContent(new ByteArrayInputStream(pensumeClosure.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handlePropedeuticConstancy(FileUploadEvent event) {
        propedeuticConstancy = event.getFile();
        requeriment.setPropedeuticConstancy(propedeuticConstancy.getContents());
        namePropedeuticConstancy = event.getFile().getFileName();
        propedeuticConstancyStream = new DefaultStreamedContent(new ByteArrayInputStream(propedeuticConstancy.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handleEpsPreProject(FileUploadEvent event) {
        epsPreProject = event.getFile();
        requeriment.setEPSpreproject(epsPreProject.getContents());
        nameEpsPreProjec = event.getFile().getFileName();
        epsPreProjectStream = new DefaultStreamedContent(new ByteArrayInputStream(epsPreProject.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handleAeioSettlement(FileUploadEvent event) {
        aeioSettlement = event.getFile();
        requeriment.setAEIOsettlement(aeioSettlement.getContents());
        nameAeioSettlemen = event.getFile().getFileName();
        aeioSettlementStream = new DefaultStreamedContent(new ByteArrayInputStream(aeioSettlement.getContents()), "application/pdf", "Solicitud Escrita.pdf");
        showAeioSettlement = true;
    }

    public void reloadWrittenRequest() {
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getWrittenRequest()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void reloadInscriptionConstancy() {
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getInscriptionConstancy()), "application/pdf", "Constancia de Inscripcion.pdf");
    }

    public void reloadPensumeClosure() {
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getPensumClosure()), "application/pdf", "Cierre de Pensum.pdf");
    }

    public void reloadPropedeuticConstancy() {
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getPropedeuticConstancy()), "application/pdf", "Constancia de Propedeutico.pdf");
    }

    public void reloadEpsPreProjec() {
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getEPSpreproject()), "application/pdf", "Preproyecto EPS.pdf");
    }

    public void reloadAeioSettlemen() {
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getAEIOsettlement()), "application/pdf", "Finiquito aeio.pdf");
    }

    public boolean isFlagStudent() {
        return flagStudent;
    }

    public void setFlagStudent(boolean flagStudent) {
        this.flagStudent = flagStudent;
    }

    public Boolean getShowAeioSettlement() {
        return showAeioSettlement;
    }

    public void setShowAeioSettlement(Boolean showAeioSettlement) {
        this.showAeioSettlement = showAeioSettlement;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public void deleteAeioSettlemen() {
        this.aeioSettlement = null;
        this.aeioSettlementStream = null;
        this.nameAeioSettlemen = "";
        this.showAeioSettlement = false;
    }

}
