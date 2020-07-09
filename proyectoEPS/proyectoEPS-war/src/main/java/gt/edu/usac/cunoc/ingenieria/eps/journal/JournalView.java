package gt.edu.usac.cunoc.ingenieria.eps.journal;

import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import gt.edu.usac.cunoc.ingenieria.eps.journal.facade.JournalLogFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
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

    public void loadCurrentJournal() {
        this.process = processFacade.getProcess(new Process(processId)).get(0);
        this.journals = journalFacade.getJournal(processId);
        this.newJournalLog = null;
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
    
    private void convertFilesUploadedToImages(){
        Image newImage;
        for (UploadedFile uploadedFile : imagesUploadedFile) {
            newImage = new Image();
            newImage.setImage(uploadedFile.getContents());
            newImage.setJournalLog(getNewJournalLog());
            getNewJournalLog().addImage(newImage);
        }
    }
}
