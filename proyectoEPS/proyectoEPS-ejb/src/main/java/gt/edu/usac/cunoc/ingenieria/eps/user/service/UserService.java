package gt.edu.usac.cunoc.ingenieria.eps.user.service;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.JAVA_MAIL_SESSION;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.configuration.mail.MailService;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Session;
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

    @EJB
    MailService mailService;

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
                user.setEpsCommittee(Boolean.TRUE);
            } else {
                user.setEpsCommittee(Boolean.FALSE);
            }

            if (user.getROLid().getName().equals(Constants.ASESOR)
                    || user.getROLid().getName().equals(Constants.REVISOR)) {
                user.setStatus(Boolean.FALSE);
                user.setRemovable(Boolean.TRUE);
            } else {
                user.setStatus(Boolean.TRUE);
                user.setRemovable(Boolean.FALSE);
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

    /**
     * This method create a unique password and userID base on rolName+DPI, and
     * is inactive until a SUpervisor approves the new user
     *
     * This is focus on Reviewer and Advisor creation
     *
     * @param user
     * @return
     * @throws UserException
     */
    public User createTempUser(User user) throws UserException {
        if (user == null) {
            throw new UserException("User is null");
        }
        user.setPassword(newPassword());
        user.setUserId(user.getrOLid().getName().concat(user.getDpi()));
        return createUser(user);
    }

    /**
     * Allow to delete Advisor or Reviewer that are Inactive
     *
     * @param user
     * @throws UserException
     */
    public void deleteUser(User user) throws UserException {
        User removeUser = entityManager.find(User.class, user.getUserId());
        if (removeUser == null) {
            throw new UserException("User is null");
        }

        if (removeUser.getRemovable()) {
            entityManager.remove(removeUser);
        } else {
            throw new UserException("Solo se permite eliminar Asesores o Revisores nuevos");
        }
    }

    /**
     * This method enable the new user and notify with the ID and Password, to
     * access in the system
     *
     * @param userApproved is the user to enable
     * @param processName is the new of the EPS work
     * @param studentName
     * @return
     * @throws UserException
     */
    public User aproveUser(User userApproved, String processName, String studentName) throws UserException {
        User newUser = entityManager.find(User.class, userApproved.getUserId());
        if (newUser == null) {
            throw new UserException("User is null");
        }

        if (newUser.getRemovable()) {
            String pass = newPassword();
            newUser.setPassword(encryptPass(pass));
            newUser.setStatus(Boolean.TRUE);
            newUser.setRemovable(Boolean.FALSE);
            entityManager.merge(newUser);
            mailService.emailApprovedAdvisorOrReviewer(pass, newUser, processName, studentName);
            return newUser;
        } else {
            throw new UserException("El usuario ya esta activo");
        }
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
        if (user.getPhone1() != null) {
            updateUser.setPhone1(user.getPhone1());
        }
        if (user.getDirection() != null) {
            updateUser.setDirection(user.getDirection());
        }
        if (user.getStatus() != null) {
            updateUser.setStatus(user.getStatus());
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
     * Update the password
     *
     * @param user
     * @return
     * @throws UserException
     */
    public User updatePassword(User user) throws UserException {
        if (user != null) {

            User found = entityManager.find(User.class, user.getUserId());

            if (found != null) {
                if (user.getPassword() != null) {
                    found.setPassword(encryptPass(user.getPassword()));
                    entityManager.merge(found);
                }
            } else {
                throw new UserException("Debe elegir un usuario");
            }
            return found;
        } else {
            throw new UserException("Debe elegir un usuario");
        }
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

            if (found != null) {
                if (user.getPassword() != null) {
                    found.setPassword(encryptPass(user.getPassword()));
                    entityManager.merge(found);
                }
            } else {
                throw new UserException("Debe elegir un usuario");
            }

            mailService.resetPasswordMessage(pass, found);
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
}
