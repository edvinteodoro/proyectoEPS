package gt.edu.usac.cunoc.ingenieria.eps.user.service;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.JAVA_MAIL_SESSION;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.SecurityConstants.PBKDF_ITERATIONS;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.SecurityConstants.PBKDF_SALT_SIZE;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@Stateless
@LocalBean
public class UserService {

    public static final String CONTENT_TYPE = "text/html; charset=utf-8";

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Resource(name = JAVA_MAIL_SESSION)
    private Session emailSession;

    @Resource
    SessionContext securityContext;

    @Inject
    private Pbkdf2PasswordHash pbkdf2PasswordHash;

    @EJB
    UserRepository userRepository;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User createUser(User user) throws UserException {
        if (user == null) {
            throw new UserException("User is null");
        }
        user.setPassword(encryptPass(user.getPassword()));
        try {
            if (user.getROLid().getName().equals(Constants.COORDINADOR_EPS)) {
                user.setEpsCommittee(true);
            }
            entityManager.persist(user);
        } catch (ConstraintViolationException e) {
            // Aqui tira los errores de constraint
            for (ConstraintViolation actual : e.getConstraintViolations()) {
                System.out.println(actual.toString());
            }
        }
        return user;
    }

    public User updateUser(User user) throws UserException {

        if (user == null) {
            throw new UserException("User is null");
        }

        User updateUser = entityManager.find(User.class, user.getUserId());

        if (user.getDpi() != null) {
            updateUser.setDpi(user.getDpi());
        }
        if (user.getCodePersonal() != null) {
            updateUser.setCodePersonal(user.getCodePersonal());
        }
        if (user.getCarnet() != null) {
            updateUser.setCarnet(user.getCarnet());
        }
        if (user.getAcademicRegister() != null) {
            updateUser.setAcademicRegister(user.getAcademicRegister());
        }
        if (user.getFirstName() != null) {
            updateUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            updateUser.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            updateUser.setEmail(user.getEmail());
        }
        if (user.getPhone() != null) {
            updateUser.setPhone(user.getPhone());
        }
        if (user.getDirection() != null) {
            updateUser.setDirection(user.getDirection());
        }
        if (user.getState() != null) {
            updateUser.setState(user.getState());
        }
        if (user.getROLid() != null) {
            updateUser.setROLid(user.getROLid());
        }
        if (user.getEpsCommittee() != null) {
            updateUser.setEpsCommittee(user.getEpsCommittee());
        }
        return updateUser;
    }

    /**
     * This feature is design to generate a new password
     *
     * Use UUID strategy to generate the password
     *
     * @param user
     * @return
     * @throws UserException
     */
    public User resetPassword(User user) throws UserException {
        if (user != null) {

            String pass = newPassword();
            user.setPassword(pass);
            User found = entityManager.find(User.class, user.getUserId());

            if (found == null) {
                if (user.getPassword() != null) {
                    found.setPassword(encryptPass(user.getPassword()));
                    entityManager.merge(found);
                }
            } else {
                throw new UserException("Debe elegir un usuario");
            }

            sendEmail(found.getEmail(), "Cambio de contraseña", emailBody(pass));
            return found;
        } else {
            throw new UserException("Debe elegir un usuario");
        }
    }

    /**
     * require the user ID and the Mail to validate the user, to create a new
     * password is generated with UUID strategy.Designed to work al login page
     *
     *
     * @param userID
     * @param userEmail
     * @return
     * @throws UserException
     */
    public boolean resetPassword(String userID, String userEmail) throws UserException {
        Optional<User> user = Optional.ofNullable(entityManager.find(User.class, userID));

        if (user.isPresent()) {
            if (userEmail != null && !userEmail.replaceAll(" ", "").isEmpty()
                    && user.get().getEmail().equals(userEmail)) {

                return (resetPassword(user.get()) != null);
            } else {
                throw new UserException("Usuario o Correo Incorrecto");
            }
        } else {
            throw new UserException("No existe el usuario " + userID);
        }
    }

    public List<User> getAuthenticatedUser() throws UserException {
        String userId = securityContext.getCallerPrincipal().getName();
        return userRepository.getUser(new User(userId));
    }

    private String encryptPass(String pass) {
        char passwordInput[] = pass.toCharArray();
        Map<String, String> map = new HashMap<>();
        map.put("Pbkdf2PasswordHash.Iterations", "3072");
        map.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA256");
        map.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        pbkdf2PasswordHash.initialize(map);
        return pbkdf2PasswordHash.generate(passwordInput);
    }

    /**
     * This method return a random password base o UUID, it most be unique
     *
     * @return
     */
    public String newPassword() {
        return UUID.randomUUID().toString();
    }

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

    private String emailBody(String password) {
        return ("<h2><strong>Se ha requerido un cambio de Contrase&ntilde;a</strong></h2>"
                + "<p>se ha reiniciado tu contrase&ntilde;a de EPS de la Division de Ciencias de la Ingenieria. Tu nueva contrase&ntilde;a es:</p>"
                + "<p><span style=\"color: #ff0000;\">" + password + "</span></p>"
                + "<p>Ahora puedes ingresar con tu ID e ingresar esta nueva contrase&ntilde;a. Se recomienda cambiarla inmediatamente luego de ingresar al portal.</p>"
                + "<p>Divisi&oacute;n de Ciencias de la Ingenieria - Centro Universitario de Occidente</p>");
    }

}
