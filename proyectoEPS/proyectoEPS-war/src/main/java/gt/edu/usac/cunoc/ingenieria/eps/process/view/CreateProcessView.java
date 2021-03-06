package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class CreateProcessView implements Serializable {

    @Inject
    private ExternalContext externalContext;

    @EJB
    private ProcessFacadeLocal processFacade;

    @EJB
    private UserFacadeLocal userFacade;

    private UploadedFile writtenRequest;
    private UploadedFile inscriptionConstancy;
    private UploadedFile pensumeClosure;
    private UploadedFile propedeuticConstancy;
    private UploadedFile epsPreProject;
    private UploadedFile aeioSettlement;

    private StreamedContent writtenRequestStream;
    private StreamedContent inscriptionConstancyStream;
    private StreamedContent pensumeClosureStream;
    private StreamedContent propedeuticConstancyStream;
    private StreamedContent epsPreProjectStream;
    private StreamedContent aeioSettlementStream;

    private Requeriment requeriment;

    private Process process;

    private User user;

    private List<UserCareer> userCareers;

    String nameWrittenRequest = "";
    String nameInscriptionConstancy = "";
    String namePensumeClosure = "";
    String namePropedeuticConstancy = "";
    String nameEpsPreProjec = "";
    String nameAeioSettlemen = "";

    @PostConstruct
    public void init() {
        try {
            user = userFacade.getAuthenticatedUser().get(0);
            userCareers = new ArrayList<>();
            setCarrerToList();
        } catch (UserException e) {
            System.out.println("No se pudo obtener usuario" + e);
        }
    }

    private void setCarrerToList() {
        List<UserCareer> values = userFacade.getUserCareer(user);
        for (int i = 0; i < values.size(); i++) {
            if (userCareers.isEmpty()) {
                userCareers.add(values.get(i));
            } else {
                for (int j = 0; j < userCareers.size(); j++) {
                    if (userCareers.get(j).getCAREERcodigo().equals(values.get(i).getCAREERcodigo())) {
                        break;
                    } else if (j == userCareers.size() - 1) {
                        userCareers.add(values.get(i));
                    }
                }
            }
        }
    }

    public Process getProcess() {
        if (process == null) {
            process = new Process();
        }
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Requeriment getRequeriment() {
        if (requeriment == null) {
            requeriment = new Requeriment();
        }
        return requeriment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void guardar() throws IOException {
        if (!nullFiles()) {
            try {
                getProcess().setApprovedEPSDevelopment(false);
                getProcess().setState(StateProcess.ACTIVO);
                getProcess().setProgress(0);
                getRequeriment().setEPSpreproject(epsPreProject.getContents());
                getRequeriment().setInscriptionConstancy(inscriptionConstancy.getContents());
                getRequeriment().setPensumClosure(pensumeClosure.getContents());
                getRequeriment().setPropedeuticConstancy(propedeuticConstancy.getContents());
                getRequeriment().setWrittenRequest(writtenRequest.getContents());
                getRequeriment().setpROCESSid(getProcess());
                getProcess().setRequeriment(getRequeriment());
                if (aeioSettlement != null) {
                    getRequeriment().setAEIOsettlement(aeioSettlement.getContents());
                }
                processFacade.createProcess(getProcess());
                MessageUtils.addSuccessMessage("Se ha creado el proceso");
                redirectToProcesses();
            } catch (ValidationException ex) {
                MessageUtils.addErrorMessage(ex.getMessage());
            }
        }
    }

    public UploadedFile getFileInputStream() {
        return writtenRequest;
    }

    public String getNameWrittenRequest() {
        return nameWrittenRequest;
    }

    public void setNameWrittenRequest(String nameWrittenRequest) {
        this.nameWrittenRequest = nameWrittenRequest;
    }

    public String getNameInscriptionConstancy() {
        return nameInscriptionConstancy;
    }

    public void setNameInscriptionConstancy(String nameInscriptionConstancy) {
        this.nameInscriptionConstancy = nameInscriptionConstancy;
    }

    public String getNamePensumeClosure() {
        return namePensumeClosure;
    }

    public void setNamePensumeClosure(String namePensumeClosure) {
        this.namePensumeClosure = namePensumeClosure;
    }

    public String getNamePropedeuticConstancy() {
        return namePropedeuticConstancy;
    }

    public void setNamePropedeuticConstancy(String namePropedeuticConstancy) {
        this.namePropedeuticConstancy = namePropedeuticConstancy;
    }

    public String getNameEpsPreProjec() {
        return nameEpsPreProjec;
    }

    public void setNameEpsPreProjec(String nameEpsPreProjec) {
        this.nameEpsPreProjec = nameEpsPreProjec;
    }

    public String getNameAeioSettlemen() {
        return nameAeioSettlemen;
    }

    public void setNameAeioSettlemen(String nameAeioSettlemen) {
        this.nameAeioSettlemen = nameAeioSettlemen;
    }

    public void setFileInputStream(final UploadedFile fileInputStream) {
        this.writtenRequest = fileInputStream;
    }

    public void handleWrittenRequest(FileUploadEvent event) {
        writtenRequest = event.getFile();
        nameWrittenRequest = event.getFile().getFileName();
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(writtenRequest.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handleInscriptionConstancy(FileUploadEvent event) {
        inscriptionConstancy = event.getFile();
        nameInscriptionConstancy = event.getFile().getFileName();
        inscriptionConstancyStream = new DefaultStreamedContent(new ByteArrayInputStream(inscriptionConstancy.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handlePensumeClosure(FileUploadEvent event) {
        pensumeClosure = event.getFile();
        namePensumeClosure = event.getFile().getFileName();
        pensumeClosureStream = new DefaultStreamedContent(new ByteArrayInputStream(pensumeClosure.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handlePropedeuticConstancy(FileUploadEvent event) {
        propedeuticConstancy = event.getFile();
        namePropedeuticConstancy = event.getFile().getFileName();
        propedeuticConstancyStream = new DefaultStreamedContent(new ByteArrayInputStream(propedeuticConstancy.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handleEpsPreProject(FileUploadEvent event) {
        epsPreProject = event.getFile();
        nameEpsPreProjec = event.getFile().getFileName();
        epsPreProjectStream = new DefaultStreamedContent(new ByteArrayInputStream(epsPreProject.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handleAeioSettlement(FileUploadEvent event) {
        aeioSettlement = event.getFile();
        nameAeioSettlemen = event.getFile().getFileName();
        aeioSettlementStream = new DefaultStreamedContent(new ByteArrayInputStream(aeioSettlement.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public Boolean nullFiles() {
        if (writtenRequest == null || inscriptionConstancy == null || pensumeClosure == null || propedeuticConstancy == null || epsPreProject == null) {
            MessageUtils.addErrorMessage("Ingrese los documentos obligatorios *");
            return true;
        }
        return false;
    }

    private void redirectToProcesses() throws IOException {
        externalContext.redirect(externalContext.getRequestContextPath() + "/process/myProcesses.xhtml");
    }

    public StreamedContent getWrittenRequestStream() {
        return writtenRequestStream;
    }

    public void setWrittenRequestStream(StreamedContent writtenRequestStream) {
        this.writtenRequestStream = writtenRequestStream;
    }

    public StreamedContent getInscriptionConstancyStream() {
        return inscriptionConstancyStream;
    }

    public void setInscriptionConstancyStream(StreamedContent inscriptionConstancyStream) {
        this.inscriptionConstancyStream = inscriptionConstancyStream;
    }

    public StreamedContent getPensumeClosureStream() {
        return pensumeClosureStream;
    }

    public void setPensumeClosureStream(StreamedContent pensumeClosureStream) {
        this.pensumeClosureStream = pensumeClosureStream;
    }

    public StreamedContent getPropedeuticConstancyStream() {
        return propedeuticConstancyStream;
    }

    public void setPropedeuticConstancyStream(StreamedContent propedeuticConstancyStream) {
        this.propedeuticConstancyStream = propedeuticConstancyStream;
    }

    public StreamedContent getEpsPreProjectStream() {
        return epsPreProjectStream;
    }

    public void setEpsPreProjectStream(StreamedContent epsPreProjectStream) {
        this.epsPreProjectStream = epsPreProjectStream;
    }

    public StreamedContent getAeioSettlementStream() {
        return aeioSettlementStream;
    }

    public void setAeioSettlementStream(StreamedContent aeioSettlementStream) {
        this.aeioSettlementStream = aeioSettlementStream;
    }

    public void reloadWrittenRequest() {
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(writtenRequest.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void reloadInscriptionConstancy() {
        inscriptionConstancyStream = new DefaultStreamedContent(new ByteArrayInputStream(inscriptionConstancy.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void reloadPensumeClosure() {
        pensumeClosureStream = new DefaultStreamedContent(new ByteArrayInputStream(pensumeClosure.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void reloadPropedeuticConstancy() {
        propedeuticConstancyStream = new DefaultStreamedContent(new ByteArrayInputStream(propedeuticConstancy.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void reloadEpsPreProjec() {
        epsPreProjectStream = new DefaultStreamedContent(new ByteArrayInputStream(epsPreProject.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void reloadAeioSettlemen() {
        aeioSettlementStream = new DefaultStreamedContent(new ByteArrayInputStream(aeioSettlement.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public List<UserCareer> getUserCareers() {
        return userCareers;
    }

    public void setUserCareers(List<UserCareer> userCareers) {
        this.userCareers = userCareers;
    }
    
    public void deleteAeioSettlemen(){
        this.aeioSettlement = null;
        this.aeioSettlementStream = null;
        this.nameAeioSettlemen = "";
    }

}
