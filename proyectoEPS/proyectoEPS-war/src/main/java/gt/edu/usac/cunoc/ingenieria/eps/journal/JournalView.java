package gt.edu.usac.cunoc.ingenieria.eps.journal;

import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import gt.edu.usac.cunoc.ingenieria.eps.journal.facade.JournalLogFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class JournalView implements Serializable {

    @EJB
    private JournalLogFacadeLocal journalFacade;

    @EJB
    private ProcessFacadeLocal processFacade;

    private Integer processId;
    private Process process;

    private JournalLog newJournalLog;
    private String linkStringNewJournalLog;
    private JournalLog selectedJournalLog;

    private List<UploadedFile> imagesUploadedFile;
    private List<JournalLog> journals;

    @PostConstruct
    public void init() {
        this.imagesUploadedFile = new ArrayList<>();
        this.journals = new ArrayList<>();
    }

    public List<JournalLog> getJournals() {
        return journals;
    }

    public void setJournals(List<JournalLog> journals) {
        this.journals = journals;
    }

    public Integer getProcessId() {
        return processId;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public JournalLog getNewJournalLog() {
        if (newJournalLog == null) {
            newJournalLog = new JournalLog();
            newJournalLog.setDateTime(LocalDate.now());
        }
        return newJournalLog;
    }

    public void setNewJournalLog(JournalLog newJournalLog) {
        this.newJournalLog = newJournalLog;
    }

    public List<UploadedFile> getImagesUploadedFile() {
        return imagesUploadedFile;
    }

    public void setImagesUploadedFile(List<UploadedFile> imagesUploadedFile) {
        this.imagesUploadedFile = imagesUploadedFile;
    }

    public JournalLog getSelectedJournalLog() {
        return selectedJournalLog;
    }

    public void setSelectedJournalLog(JournalLog selectedJournalLog) {
        this.selectedJournalLog = selectedJournalLog;
    }

    public String getLinkStringNewJournalLog() {
        return linkStringNewJournalLog;
    }

    public void setLinkStringNewJournalLog(String linkStringNewJournalLog) {
        this.linkStringNewJournalLog = linkStringNewJournalLog;
    }

    public void loadCurrentJournal() {
        this.process = processFacade.getProcess(new Process(processId)).get(0);
        this.journals = journalFacade.getJournal(processId);
        cleanNewJournalLog();
        this.imagesUploadedFile.clear();
    }

    public void createJournalLog(final String modalIdToClose) {
        try {
            newJournalLog.setProcess(process);
            convertFilesUploadedToImages();
            journalFacade.createJounalLog(newJournalLog);
            MessageUtils.addSuccessMessage("Se agregó el nuevo registro");
            loadCurrentJournal();
            PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
        } catch (LimitException | MandatoryException ex) {
            MessageUtils.addErrorMessage(ex.getMessage());
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        this.imagesUploadedFile.add(event.getFile());
    }

    private void convertFilesUploadedToImages() {
        Image newImage;
        for (UploadedFile uploadedFile : imagesUploadedFile) {
            newImage = new Image();
            newImage.setImage(uploadedFile.getContents());
            newImage.setJournalLog(getNewJournalLog());
            getNewJournalLog().addImage(newImage);
        }
    }

    public void cleanSelectedJournalLog() {
        this.selectedJournalLog = null;
    }

    public void cleanNewJournalLog() {
        this.newJournalLog = null;
        this.linkStringNewJournalLog = null;
    }

    public StreamedContent getImage() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String imageId = context.getExternalContext().getRequestParameterMap().get("imageId");
            Image image = journalFacade.getImageById(Integer.valueOf(imageId));
            return new DefaultStreamedContent(new ByteArrayInputStream(image.getImage()), "image/jpg", "imagen.jpg");
        }
    }

    public void addLinkNewJournal() {
        if (!linkStringNewJournalLog.isEmpty()) {
            Link newLink = new Link();
            newLink.setJournalLog(getNewJournalLog());
            newLink.setLink(linkStringNewJournalLog.getBytes());
            getNewJournalLog().addLink(newLink);
            linkStringNewJournalLog = null;
            MessageUtils.addSuccessMessage("Cargado nuevo enlace");
        } else {
            MessageUtils.addErrorMessage("No existe enlace a cargar");
        }
    }
}
