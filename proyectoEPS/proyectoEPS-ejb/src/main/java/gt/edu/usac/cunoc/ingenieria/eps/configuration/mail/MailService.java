package gt.edu.usac.cunoc.ingenieria.eps.configuration.mail;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.JAVA_MAIL_SESSION;
import gt.edu.usac.cunoc.ingenieria.eps.process.Appointment;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.appointmentState;
import gt.edu.usac.cunoc.ingenieria.eps.user.service.UserService;
import java.time.LocalDate;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author angelrg
 */
@Stateless
@LocalBean
public class MailService {

    public static final String CONTENT_TYPE = "text/html; charset=utf-8";

    @Resource(name = JAVA_MAIL_SESSION)
    private Session emailSession;

    @Asynchronous
    private Future<Void> sendEmail(final String to, final String subject, final String body) {
        try {
            Message message = new MimeMessage(emailSession);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(body, CONTENT_TYPE);
            Transport.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new AsyncResult<>(null);
    }

    /**
     * Notifies the change of password by the user
     *
     * @param password
     * @param user to change password
     */
    public void resetPasswordMessage(String password, User user) {
        String message = ("<h2><strong>Se ha requerido un cambio de Contrase&ntilde;a</strong></h2>"
                + "<p>se ha reiniciado tu contrase&ntilde;a de EPS de la Division de Ciencias de la Ingenieria. Tu nueva contrase&ntilde;a es:</p>"
                + "<p><span style=\"color: #ff0000;\">" + password + "</span></p>"
                + "<p>Ahora puedes ingresar con tu ID e ingresar esta nueva contrase&ntilde;a. Se recomienda cambiarla inmediatamente luego de ingresar al portal.</p>"
                + "<p>Divisi&oacute;n de Ciencias de la Ingenieria - Centro Universitario de Occidente</p>");

        sendEmail(user.getEmail(), "Cambio de Contraseña", message);
    }

    /**
     * Message notifies the new Advisor or Reviewer that it's account is
     * available to start.Include the user ID and Password for the new user, at
     * the beginning doesn't know it's account.
     *
     *
     *
     * @param password the new password
     * @param user is Advisor or Reviewer
     * @param processName is the project title
     * @param studentName
     */
    public void emailApprovedAdvisorOrReviewer(String password, User user, String processName, String studentName) {
        String message = ("<h1><strong>Se le ha aprovado como " + user.getrOLid().getName() + "</strong></h1>"
                + "<p>Se le ha habilitado un usuario y contrase&ntilde;a dentro del Sistema de EPS de la Divisi&oacute;n de Ciencias de la Ingenier&iacute;a</p>"
                + "<p>Se le ha aprovado como Asesor del tema " + processName + ".</p>"
                + "<p>Se le habilito con el Usuario <strong>" + user.getUserId() + "</strong> y contrase&ntilde;a <strong><span style=\"color: #ff0000;\">" + password + "</span></strong>.&nbsp;</p>"
                + "<p>Se recomienda cambiar su contrase&ntilde;a inmediatamente luego de ingresar al portal, ingresando por el menu en <strong>Usuario -&gt; Mi Perfil</strong></p>"
                + "<h3><strong>Divisi&oacute;n de Ciencias de la Ingenier&iacute;a - Centro Universitario de Occidente</strong></h3>");

        sendEmail(user.getEmail(), user.getrOLid().getName() + " de EPS", message);
    }

    /**
     * Notifies the Advisor or Reviewer that had been set as part of a new EPS
     * project, including the title of the EPS project
     *
     * @param user is the Advisor or Reviewer
     * @param processName is the project title
     * @param studentName
     */
    public void emailNotifyAdvisorOrReviewer(User user, String processName, String studentName) {
        String text = ("<h1><strong>Se le ha aprovado como " + user.getrOLid().getName() + "</strong></h1>"
                + "<p>Se le ha aprovado como Asesor del tema <strong>" + processName + "</strong> del estudiante <strong>" + studentName + "</strong>.</p>"
                + "<p>Ingrese con su usuario <strong>" + user.getUserId() + "</strong></p>"
                + "<h3><strong>Divisi&oacute;n de Ciencias de la Ingenier&iacute;a - Centro Universitario de Occidente</strong></h3>");

        sendEmail(user.getEmail(), user.getrOLid().getName() + " de EPS", text);
    }

    /**
     * Notifies the student of the supervisor's decision
     *
     * @param advisor
     * @param advisorState
     * @param reviewer
     * @param reviewerState
     * @param supervisor
     * @param proccessName
     * @param student
     */
    public void emailNotifyStudent(User advisor, appointmentState advisorState, User reviewer,
            appointmentState reviewerState, User supervisor, String proccessName, User student) {
        String message = "<h1>Revisor y Asesor</h1>"
                + "<p>El supervisor <strong>" + supervisor.getFirstName() + ", " + supervisor.getLastName() + "</strong> ha completado la revisi&oacute;n del Supervisor y Asesor para el Proyecto " + proccessName + "</p>";

        switch (advisorState) {
            case APPROVED:
                message += "<p>Se ha aprovado al Asesor <strong>" + advisor.getFirstName() + ", " + advisor.getLastName() + ".</strong></p>";
                break;
            case CHANGE:
                message += "<p>Se ha <strong>ELIMINADO</strong> al Asesor, por lo que debe proponer una nuevo o elegir entre los asesores disponible en el Sistema.</p>";
                break;
            case ELECTION:
                message += "<p>El Supervisor no ha aprovado al Asesor, y ha elegido a <strong>" + advisor.getFirstName() + ", " + advisor.getLastName() + "</strong>para tomar el cargo.</p>";
                break;
        }

        switch (reviewerState) {
            case APPROVED:
                message += "<p>Se ha aprovado al Revisor <strong>" + reviewer.getFirstName() + ", " + reviewer.getLastName() + ".</strong></p>";
                break;
            case CHANGE:
                message += "<p>Se ha <strong>ELIMINADO</strong> al Revisor, por lo que debe proponer una nuevo o elegir entre los revisores disponible en el Sistema.</p>";
                break;
            case ELECTION:
                message += "<p>El Supervisor no ha aprovado al Revisor, y ha elegido a <strong>" + reviewer.getFirstName() + ", " + reviewer.getLastName() + "</strong>para tomar el cargo.</p>";
                break;
        }

        message += "<h3>Divisi&oacute;n de Ciencias de la Ingenier&iacute;a - Centro Universitario de Occidente.</h3>";

        sendEmail(student.getEmail(), "Revisión del Revisor y Asesor", message);
    }

    /**
     * Notify the Supervisor when a Student submit Advisor and Reviewer form.
     *
     * @param supervisor
     * @param appointment
     * @param projectName
     * @param student
     */
    public void emailNotifySupervisor(User supervisor, Appointment appointment, String projectName, User student) {

        String message = "<h2>Nueva Solicitud para Asesor y Revisor</h2>"
                + "<p>El estudiante <strong>" + student.getFirstName() + ", " + student.getLastName() + "</strong> con el Registro Academico <strong>" + student.getAcademicRegister() + "</strong> ha propuesto para su EPS titulado <strong>" + projectName + "</strong> a:</p>"
                + "<p>Asesor: " + appointment.getUserAdviser().getFirstName() + ", " + appointment.getUserAdviser().getLastName() + " <strong>(" + appointment.getAdviserState().stateToText() + ").</strong></p>"
                + "<p>Revisor: " + appointment.getUserReviewer().getFirstName() + ", " + appointment.getUserReviewer().getLastName() + " <strong>(" + appointment.getReviewerState().stateToText() + ").</strong></p>"
                + "<p>Ingrese al portal para evaluar la propuesta del estudiante.</p>"
                + "<h3><strong>Divisi&oacute;n de Ciencias de la Ingenier&iacute;a - Centro Universitario de Occidente</strong></h3>";

        sendEmail(supervisor.getEmail(), "Asesor y Revisor", message);
    }

    /**
     * Notify the student to remain revision visit
     *
     *
     * @param process
     * @param businessDays
     * @param lastRevision
     */
    public void emailRevisionRemainerStudent(Process process, int businessDays, boolean lastRevision) {
        String message = "<h2>EPS: " + process.getProject().getTitle() + "</h2>";

        if (lastRevision) {
            message += "<p><strong>Esta es la ultima revisión, tomar las consideraciones necesarias.</strong></p>";
        }

        message += "<p>El dia <strong>" + LocalDate.now().plusDays(businessDays).toString() + "</strong> es su cita propuesta para la revision con su Supervisor <strong>" + process.getSupervisor_EPS().getFirstName() + ", " + process.getSupervisor_EPS().getLastName() + "</strong>, debe ponerse en contacto con su Supervisor para concretar la cita.</p>"
                + "<h3>Divisi&oacute;n de Ciencias de la Ingenier&iacute;a - Centro Universitario de Occidente</h3>";

        sendEmail(process.getUserCareer().getUSERuserId().getEmail(), "Revision: " + LocalDate.now().plusDays(businessDays).toString(), message);
    }

    /**
     * Notify the Supervisor to remain revision visit
     *
     * @param process
     * @param businessDays
     * @param lastRevision
     */
    public void emailRevisionRemainerSupervisor(Process process, int businessDays, boolean lastRevision) {
        String message = "<h2>EPS: " + process.getProject().getTitle() + "</h2>";

        if (lastRevision) {
            message += "<p><strong>Esta es la ultima revisión, tomar las consideraciones necesarias.</strong></p>";
        }

        message += "<p>El dia <strong>" + LocalDate.now().plusDays(businessDays).toString() + "</strong> es su cita propuesta para la revision del estudiante <strong>" + process.getUserCareer().getUSERuserId().getFirstName() + ", " + process.getUserCareer().getUSERuserId().getLastName() + "</strong>, el estudiante debe ponerse en contacto para concretar la cita.</p>"
                + "<h3>Divisi&oacute;n de Ciencias de la Ingenier&iacute;a - Centro Universitario de Occidente</h3>";

        sendEmail(process.getSupervisor_EPS().getEmail(), "Revision: " + LocalDate.now().plusDays(businessDays).toString(), message);
    }

}
