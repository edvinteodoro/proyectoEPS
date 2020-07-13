package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ASESOR;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_CARRERA;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ESTUDIANTE;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.REVISOR;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SUPERVISOR_EPS;
import gt.edu.usac.cunoc.ingenieria.eps.process.Appointment;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.tail.facade.TailFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@ViewScoped
public class ProcessesView implements Serializable {

    @Inject
    private ExternalContext externalContext;

    @EJB
    private ProcessFacadeLocal processFacade;

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private TailFacadeLocal tailFacade;

    private List<Process> processes;
    private Boolean careerCoordinator;

    private User user;
    private Appointment appointment;
    private StreamedContent adviserResumeStream;
    private StreamedContent reviewerResumeStream;

    @PostConstruct
    public void init() {
        try {
            careerCoordinator = false;
            user = userFacade.getAuthenticatedUser().get(0);
            switch (user.getROLid().getName()) {
                case ESTUDIANTE:
                    processes = processFacade.getProcessUser(user);
                    break;
                case COORDINADOR_CARRERA:
                    processes = tailFacade.getProcessByCoordinator(user);
                    careerCoordinator = true;
                    break;
            }
            if (processes != null) {
                if (processes.get(0) == null) {
                    processes = null;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro+-------------------------------------------\n" + e);
        }
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public void goToProcess(Process process) {

    }

    public Boolean isCareerCoordinator() {
        return careerCoordinator;
    }

    public String redirectProcessTo() {
        String value = "";
        switch (user.getROLid().getName()) {
            case ESTUDIANTE:
                value = "project";
                break;
            case COORDINADOR_CARRERA:
                value = "projectReview";
                break;
        }
        return value;
    }

    public String redirectJournalTo() {
        String value = "";
        switch (user.getROLid().getName()) {
            case ESTUDIANTE:
                value = "journal";
                break;
            case SUPERVISOR_EPS:
            case REVISOR:
            case ASESOR:
                value = "journalReview";
                break;
        }
        return value;
    }

    public Boolean getIsFirst(Integer id) {
        return (id == getProcesses().get(0).getId());
    }

    public Boolean studentAppointmentApproved(Process process) {
        return (process != null && process.getAppointmentId() != null
                && process.getAppointmentId().getAdviserState() != null
                && process.getAppointmentId().getReviewerState() != null
                && (process.getAppointmentId().getAdviserState() == appointmentState.APPROVED
                || process.getAppointmentId().getAdviserState() == appointmentState.ELECTION)
                && (process.getAppointmentId().getReviewerState() == appointmentState.APPROVED
                || process.getAppointmentId().getReviewerState() == appointmentState.ELECTION));
    }

    public void reloadAdviserResume() {
        adviserResumeStream = new DefaultStreamedContent(new ByteArrayInputStream(appointment.getUserAdviser().getPersonalResume()), "application/pdf", "Curriculum.pdf");
    }

    public void reloadReviewerResume() {
        reviewerResumeStream = new DefaultStreamedContent(new ByteArrayInputStream(appointment.getUserReviewer().getPersonalResume()), "application/pdf", "Curriculum.pdf");
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public StreamedContent getAdviserResumeStream() {
        return adviserResumeStream;
    }

    public void setAdviserResumeStream(StreamedContent adviserResumeStream) {
        this.adviserResumeStream = adviserResumeStream;
    }

    public StreamedContent getReviewerResumeStream() {
        return reviewerResumeStream;
    }

    public void setReviewerResumeStream(StreamedContent reviewerResumeStream) {
        this.reviewerResumeStream = reviewerResumeStream;
    }
    
    public void clean(){
        adviserResumeStream = null;
        reviewerResumeStream = null;
        appointment = null;
    }
}
