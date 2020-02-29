package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class CreateProcessView implements Serializable {

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

    @PostConstruct
    public void init() {
    
    }
    public Process getProcess() {
        if (process == null) {
            process=new Process();
        }
        return process;
    }

    public Requeriment getRequeriment() {
        if (requeriment == null) {
            requeriment=new Requeriment();
        }
        return requeriment;
    }

    public void guardar() {
        getProcess().setApprovedEPSDevelopment(false);
        getRequeriment().setAEIOsettlement(getFileInputStream().getContents());
        getRequeriment().setEPSpreproject(getFileInputStream().getContents());
        getRequeriment().setInscriptionConstancy(getFileInputStream().getContents());
        getRequeriment().setPensumClosure(getFileInputStream().getContents());
        getRequeriment().setPropedeuticConstancy(getFileInputStream().getContents());
        getRequeriment().setWrittenRequest(getFileInputStream().getContents());
        getRequeriment().setpROCESSid(getProcess());
        processFacade.createRequeriment(getRequeriment());
    }
    
    public UploadedFile getFileInputStream() {
        return writtenRequest;
    }

    public void setFileInputStream(final UploadedFile fileInputStream) {
        this.writtenRequest = fileInputStream;
    }
    public void handleFileUpload(FileUploadEvent event) {
        writtenRequest = event.getFile();
        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
