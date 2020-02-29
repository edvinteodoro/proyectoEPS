package gt.edu.usac.cunoc.ingenieria.eps.user.service;


import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@Stateless
@LocalBean
public class UserService {
    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    
    @Resource
    SessionContext securityContext;
    
    @Inject
    private Pbkdf2PasswordHash pbkdf2PasswordHash;
    
    @EJB
    UserRepository userRepository;
    
    public User createUser(User user){
        user.setPassword(encryptPass(user.getPassword()));
        entityManager.persist(user);
        return user;
    }
    
    public User updateUser(User user) {
        User updateUser = entityManager.find(User.class, user.getDpi());
        if (user.getDpi()!= null) {
            updateUser.setDpi(user.getDpi());
        }
        if (user.getCodePersonal()!= null) {
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
        if (user.getPassword() != null) {
            updateUser.setPassword(user.getPassword());
        }
        if (user.getDirection() != null) {
            updateUser.setDirection(user.getDirection());
        }
        if (user.getState() != 0) {
            updateUser.setState(user.getState());
        }
        if (user.getROLid() != null) {
            updateUser.setROLid(user.getROLid());
        }

        return updateUser;
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
    public List<User> getAuthenticatedUser(){
        String dpi = securityContext.getCallerPrincipal().getName();
        return userRepository.getUser(new User(dpi));
    }

}
