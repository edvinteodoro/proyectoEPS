package gt.edu.usac.cunoc.ingenieria.eps.tail.repository;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.JAVA_MAIL_SESSION;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.user.service.UserService;
import static gt.edu.usac.cunoc.ingenieria.eps.user.service.UserService.CONTENT_TYPE;
import java.util.List;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class TailCoordinatorRepository {

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    
    @Resource(name = JAVA_MAIL_SESSION)
    private Session emailSession;

    public static final String GET_PROCESS_COORDIANTOR = "SELECT t.process FROM TailCoordinator t WHERE t.userCareer.uSERuserId.userId=:userId ORDER BY t.id ASC";
    public static final String ID_PARAMETER_NAME = "userId";
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Process> getProcessByCoordinator(User user) {
        Query query = entityManager.createQuery(GET_PROCESS_COORDIANTOR);
        query.setParameter(ID_PARAMETER_NAME, user.getUserId());
        return query.getResultList();    
    }
    
    @Asynchronous
    private Future<Void> sendEmailCoordinatorRefusal(final String to, final String subject, final String body) {
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
    
    

    private String emailBody(String password) {
        return ("<h2><strong>Se ha requerido un cambio de Contrase&ntilde;a</strong></h2>"
                + "<p>se ha reiniciado tu contrase&ntilde;a de EPS de la Division de Ciencias de la Ingenieria. Tu nueva contrase&ntilde;a es:</p>"
                + "<p><span style=\"color: #ff0000;\">" + password + "</span></p>"
                + "<p>Ahora puedes ingresar con tu ID e ingresar esta nueva contrase&ntilde;a. Se recomienda cambiarla inmediatamente luego de ingresar al portal.</p>"
                + "<p>Divisi&oacute;n de Ciencias de la Ingenieria - Centro Universitario de Occidente</p>");
    }

}
