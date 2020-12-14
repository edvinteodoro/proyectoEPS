package gt.edu.usac.cunoc.ingenieria.eps.journal;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.journal.facade.CommentaryFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import gt.edu.usac.cunoc.ingenieria.eps.journal.facade.JournalLogFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.utils.MessageUtils;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class JournalView implements Serializable {

    @Inject
    private ExternalContext externalContext;

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private JournalLogFacadeLocal journalFacade;

    @EJB
    private ProcessFacadeLocal processFacade;

    @EJB
    private CommentaryFacadeLocal commentaryFacade;

    private Integer processId;
    private Process process;

    private JournalLog newJournalLog;
    private String linkStringNewJournalLog;
    private JournalLog selectedJournalLog;

    private List<UploadedFile> imagesUploadedFile;
    private List<JournalLog> journals;

    private User userLogged;

    @PostConstruct
    public void init() {
        this.imagesUploadedFile = new ArrayList<>();
        this.journals = new ArrayList<>();
    }

    public void loadCurrentJournal() throws IOException {
        try {
            this.userLogged = userFacade.getAuthenticatedUser().get(0);
            this.process = processFacade.getProcess(new Process(processId)).get(0);
            if (this.process.getUserCareer().getUSERuserId().equals(this.userLogged)) {
                this.journals = journalFacade.getJournal(processId);
                cleanNewJournalLog();
                this.imagesUploadedFile.clear();
            } else {
                externalContext.redirect(externalContext.getRequestContextPath() + "/error/error-403.xhtml");
            }
        } catch (UserException ex) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/error/error-403.xhtml");
        } catch (ArrayIndexOutOfBoundsException ex) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/error/error-404.xhtml");
        }
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

    public void createJournalLog(final String modalIdToClose) throws IOException {
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

    public void addLinkNewJournal() {
        if (!linkStringNewJournalLog.isEmpty()) {
            Link newLink = new Link();
            newLink.setLink(linkStringNewJournalLog.getBytes());
            getNewJournalLog().addLink(newLink);
            linkStringNewJournalLog = null;
            MessageUtils.addSuccessMessage("Cargado nuevo enlace");
        } else {
            MessageUtils.addErrorMessage("No existe enlace a cargar");
        }
    }

    public List<Commentary> getCommentariesJournal(Integer JournalId) {
        return commentaryFacade.getCommentariesByJournalId(JournalId);
    }
    
    public Boolean canEnableJournalSupervisorEPS(){
        return process.getAppointmentId() != null 
                && process.getAppointmentId().getCompanySupervisor() != null && process.getAppointmentId().getCompanySupervisor().getStatus()
                && process.getAppointmentId().getAdviserState() != appointmentState.CHANGE && process.getAppointmentId().getAdviserState() != appointmentState.REVIEW
                && process.getAppointmentId().getReviewerState()!= appointmentState.CHANGE && process.getAppointmentId().getReviewerState()!= appointmentState.REVIEW;
    }
}
