package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@ViewScoped
public class RequerimentReviewView implements Serializable {

    @EJB
    private ProcessFacadeLocal processFacade;

    private StreamedContent writtenRequest;
    private StreamedContent inscriptionConstancy;
    private StreamedContent pensumeClosure;
    private StreamedContent propedeuticConstancy;
    private StreamedContent epsPreProject;
    private StreamedContent aeioSettlement;

    private Requeriment requeriment;
    private Integer processId;
    private Boolean showAeioSettlement= false;

    @PostConstruct
    public void init() {
        
    }
    
    public void aceptar(){
        
    }
    

    public StreamedContent getWrittenRequest() {
        return writtenRequest;
    }

    public void setWrittenRequest(StreamedContent writtenRequest) {
        this.writtenRequest = writtenRequest;
    }

    public StreamedContent getInscriptionConstancy() {
        return inscriptionConstancy;
    }

    public void setInscriptionConstancy(StreamedContent inscriptionConstancy) {
        this.inscriptionConstancy = inscriptionConstancy;
    }

    public StreamedContent getPensumeClosure() {
        return pensumeClosure;
    }

    public void setPensumeClosure(StreamedContent pensumeClosure) {
        this.pensumeClosure = pensumeClosure;
    }

    public StreamedContent getPropedeuticConstancy() {
        return propedeuticConstancy;
    }

    public void setPropedeuticConstancy(StreamedContent propedeuticConstancy) {
        this.propedeuticConstancy = propedeuticConstancy;
    }

    public StreamedContent getEpsPreProject() {
        return epsPreProject;
    }

    public void setEpsPreProject(StreamedContent epsPreProject) {
        this.epsPreProject = epsPreProject;
    }

    public StreamedContent getAeioSettlement() {
        return aeioSettlement;
    }

    public void setAeioSettlement(StreamedContent aeioSettlement) {
        this.aeioSettlement = aeioSettlement;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }
    
    public void loadCurrentProcess(){
        System.out.println("ejemplos");
        requeriment = processFacade.getRequeriment(new Requeriment(processId)).get(0);
        writtenRequest = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getWrittenRequest()), "application/pdf", "Solicitud Escrita.pdf");
        inscriptionConstancy = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getInscriptionConstancy()), "application/pdf", "Constancia Inscripcion.pdf");
        pensumeClosure = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getPensumClosure()), "application/pdf", "Solicitud Escrita.pdf");
        propedeuticConstancy = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getPropedeuticConstancy()), "application/pdf", "Solicitud Escrita.pdf");
        epsPreProject = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getEPSpreproject()), "application/pdf", "Solicitud Escrita.pdf");
        if(requeriment.getAEIOsettlement()!=null){
            showAeioSettlement=true;
            aeioSettlement=new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getaEIOsettlement()), "application/pdf", "Solicitud Escrita.pdf");
        }
    }

    public Boolean getShowAeioSettlement() {
        return showAeioSettlement;
    }

    public void setShowAeioSettlement(Boolean showAeioSettlement) {
        this.showAeioSettlement = showAeioSettlement;
    }
    
    

}
