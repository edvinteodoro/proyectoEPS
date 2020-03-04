package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class CreateProcessView implements Serializable {

    @Inject
    private ExternalContext externalContext;
    
    @EJB
    private ProcessFacadeLocal processFacade;

    private UploadedFile writtenRequest;
    private UploadedFile inscriptionConstancy;
    private UploadedFile pensumeClosure;
    private UploadedFile propedeuticConstancy;
    private UploadedFile epsPreProject;
    private UploadedFile aeioSettlement;

    private Requeriment requeriment;

    private Process process;
    
    String nameWrittenRequest="";
    String nameInscriptionConstancy="";
    String namePensumeClosure="";
    String namePropedeuticConstancy="";
    String nameEpsPreProjec="";
    String nameAeioSettlemen="";
    
    @PostConstruct
    public void init() {

    }

    public Process getProcess() {
        if (process == null) {
            process = new Process();
        }
        return process;
    }

    public Requeriment getRequeriment() {
        if (requeriment == null) {
            requeriment = new Requeriment();
        }
        return requeriment;
    }

    public void guardar() throws IOException {
        if (!nullFiles()) {
            getProcess().setApprovedEPSDevelopment(false);
            getProcess().setApprovedCareerCoordinator(false);
            getProcess().setState(true);
            getProcess().setProgress(0);
            getRequeriment().setEPSpreproject(epsPreProject.getContents());
            getRequeriment().setInscriptionConstancy(inscriptionConstancy.getContents());
            getRequeriment().setPensumClosure(pensumeClosure.getContents());
            getRequeriment().setPropedeuticConstancy(propedeuticConstancy.getContents());
            getRequeriment().setWrittenRequest(writtenRequest.getContents());
            getRequeriment().setpROCESSid(getProcess());
            if (aeioSettlement != null) {
                getRequeriment().setAEIOsettlement(aeioSettlement.getContents());
            }
            processFacade.createRequeriment(getRequeriment());
            MessageUtils.addSuccessMessage("Se ha creado registrado el proceso");
            redirectToProcesses();
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
        nameWrittenRequest= event.getFile().getFileName();
    }

    public void handleInscriptionConstancy(FileUploadEvent event) {
        inscriptionConstancy = event.getFile();
        nameInscriptionConstancy=event.getFile().getFileName();
    }

    public void handlePensumeClosure(FileUploadEvent event) {
        pensumeClosure = event.getFile();
        namePensumeClosure=event.getFile().getFileName();
    }

    public void handlePropedeuticConstancy(FileUploadEvent event) {
        propedeuticConstancy = event.getFile();
        namePropedeuticConstancy=event.getFile().getFileName();
    }

    public void handleEpsPreProject(FileUploadEvent event) {
        epsPreProject = event.getFile();
        nameEpsPreProjec= event.getFile().getFileName();
    }

    public void handleAeioSettlement(FileUploadEvent event) {
        aeioSettlement = event.getFile();
    }

    public Boolean nullFiles() {
        if (writtenRequest == null || inscriptionConstancy == null || pensumeClosure == null || propedeuticConstancy == null || epsPreProject == null) {
            MessageUtils.addErrorMessage("Ingrese los documentos obligatorios *");
            return true;
        }
        return false;
    }
    
    private void redirectToProcesses() throws IOException{ 
        externalContext.redirect(externalContext.getRequestContextPath() + "/process/processes.xhtml");
    }
}
