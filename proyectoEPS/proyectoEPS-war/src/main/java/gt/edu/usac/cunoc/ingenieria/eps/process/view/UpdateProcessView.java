package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class UpdateProcessView implements Serializable{
    @EJB
    private ProcessFacadeLocal processFacade;
    
    
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
    
    String nameWrittenRequest="";
    String nameInscriptionConstancy="";
    String namePensumeClosure="";
    String namePropedeuticConstancy="";
    String nameEpsPreProjec="";
    String nameAeioSettlemen="";
    
    

    private Requeriment requeriment;
    private Integer processId;
    private Boolean showAeioSettlement= false;

    @PostConstruct
    public void init() {
        
    }
    
    public void save(){
        processFacade.updaterequeriment(requeriment);
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

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }
    
    public void loadCurrentProcess(){
        requeriment = processFacade.getRequeriment(new Requeriment(processId)).get(0);
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getWrittenRequest()), "application/pdf", "Solicitud Escrita.pdf");
        inscriptionConstancyStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getInscriptionConstancy()), "application/pdf", "Constancia Inscripcion.pdf");
        pensumeClosureStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getPensumClosure()), "application/pdf", "Solicitud Escrita.pdf");
        propedeuticConstancyStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getPropedeuticConstancy()), "application/pdf", "Solicitud Escrita.pdf");
        epsPreProjectStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getEPSpreproject()), "application/pdf", "Solicitud Escrita.pdf");
        if(requeriment.getAEIOsettlement()!=null){
            showAeioSettlement=true;
            aeioSettlementStream=new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getaEIOsettlement()), "application/pdf", "Solicitud Escrita.pdf");
        }
    }

    public Boolean getShowAeioSettlement() {
        return showAeioSettlement;
    }

    public void setShowAeioSettlement(Boolean showAeioSettlement) {
        this.showAeioSettlement = showAeioSettlement;
    }
    
     public void handleWrittenRequest(FileUploadEvent event) {
        writtenRequest = event.getFile();
        requeriment.setWrittenRequest(writtenRequest.getContents()); 
        nameWrittenRequest= event.getFile().getFileName();
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(writtenRequest.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handleInscriptionConstancy(FileUploadEvent event) {
        inscriptionConstancy = event.getFile();
        requeriment.setInscriptionConstancy(inscriptionConstancy.getContents());
        nameInscriptionConstancy=event.getFile().getFileName();
        inscriptionConstancyStream=new DefaultStreamedContent(new ByteArrayInputStream(inscriptionConstancy.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handlePensumeClosure(FileUploadEvent event) {
        pensumeClosure = event.getFile();
        requeriment.setPensumClosure(pensumeClosure.getContents());
        namePensumeClosure=event.getFile().getFileName();
        pensumeClosureStream=new DefaultStreamedContent(new ByteArrayInputStream(pensumeClosure.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handlePropedeuticConstancy(FileUploadEvent event) {
        propedeuticConstancy = event.getFile();
        requeriment.setPropedeuticConstancy(propedeuticConstancy.getContents());
        namePropedeuticConstancy=event.getFile().getFileName();
        propedeuticConstancyStream=new DefaultStreamedContent(new ByteArrayInputStream(propedeuticConstancy.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handleEpsPreProject(FileUploadEvent event) {
        epsPreProject = event.getFile();
        requeriment.setEPSpreproject(epsPreProject.getContents()) ;
        nameEpsPreProjec= event.getFile().getFileName();
        epsPreProjectStream=new DefaultStreamedContent(new ByteArrayInputStream(epsPreProject.getContents()), "application/pdf", "Solicitud Escrita.pdf");
    }

    public void handleAeioSettlement(FileUploadEvent event) {
        aeioSettlement = event.getFile();
        requeriment.setAEIOsettlement(aeioSettlement.getContents());
        nameAeioSettlemen= event.getFile().getFileName();
        aeioSettlementStream=new DefaultStreamedContent(new ByteArrayInputStream(aeioSettlement.getContents()), "application/pdf", "Solicitud Escrita.pdf");        
    }
    
    public void reloadWrittenRequest(){
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getWrittenRequest()), "application/pdf", "Solicitud Escrita.pdf");
    }
    public void reloadInscriptionConstancy(){
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getInscriptionConstancy()), "application/pdf", "Constancia de Inscripcion.pdf");
    }
    public void reloadPensumeClosure(){
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getPensumClosure()), "application/pdf", "Cierre de Pensum.pdf");
    }
    public void reloadPropedeuticConstancy(){
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getPropedeuticConstancy()), "application/pdf", "Constancia de Propedeutico.pdf");
    }
    public void reloadEpsPreProjec(){
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getEPSpreproject()), "application/pdf", "Preproyecto EPS.pdf");
    }
    public void reloadAeioSettlemen(){
        writtenRequestStream = new DefaultStreamedContent(new ByteArrayInputStream(requeriment.getAEIOsettlement()), "application/pdf", "Finiquito aeio.pdf");
    }
}
