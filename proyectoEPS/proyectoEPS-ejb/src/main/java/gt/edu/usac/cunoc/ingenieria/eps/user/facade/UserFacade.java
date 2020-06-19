package gt.edu.usac.cunoc.ingenieria.eps.user.facade;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.CareerRepository;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.RolRepository;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.UserCareerRepository;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.UserRepository;
import gt.edu.usac.cunoc.ingenieria.eps.user.service.CareerService;
import gt.edu.usac.cunoc.ingenieria.eps.user.service.RolService;
import gt.edu.usac.cunoc.ingenieria.eps.user.service.UserCareerService;
import gt.edu.usac.cunoc.ingenieria.eps.user.service.UserService;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author charly
 */
@Stateless
@LocalBean
public class UserFacade implements UserFacadeLocal {

    @EJB
    private UserService userService;
    @EJB
    private UserRepository userRepository;
    @EJB
    private CareerService careerService;
    @EJB
    private CareerRepository careerRepository;
    @EJB
    private UserCareerService userCareerService;
    @EJB
    private UserCareerRepository userCareerRepository;
    @EJB
    private RolService rolUserService;
    @EJB
    private RolRepository rolUserRepository;

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @EJB
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EJB
    public void setCareerService(CareerService careerService) {
        this.careerService = careerService;
    }

    @EJB
    public void setCareerRepository(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    @EJB
    public void setRolUserService(RolService rolUserService) {
        this.rolUserService = rolUserService;
    }

    @EJB
    public void setRolUserRepository(RolRepository rolUserRepository) {
        this.rolUserRepository = rolUserRepository;
    }

    @Override
    public User createUser(User user) throws UserException {
        return userService.createUser(user);
    }

    @Override
    public User updateUser(User user) throws UserException {
        return userService.updateUser(user);
    }

    @Override
    public List<User> getUser(User user) throws UserException {
        return userRepository.getUser(user);
    }

    @Override
    public Career createCareer(Career career) throws UserException {
        return careerService.createCareer(career);
    }

    public Career updateCareer(Career career, String name) throws UserException {
        return careerService.updateCareer(career, name);
    }

    @Override
    public List<Career> getCareer(Career career) throws UserException {
        return careerRepository.getCareer(career);
    }

    @Override
    public Rol createRolUser(Rol rolUser) throws UserException {
        return rolUserService.createRolUser(rolUser);
    }

    @Override
    public Rol updateRolUser(Rol rolUser) throws UserException {
        return rolUserService.updateRolUser(rolUser);
    }

    @Override
    public List<Rol> getRolUser(Rol rolUser) throws UserException {
        return rolUserRepository.getRoll(rolUser);
    }

    @Override
    public UserCareer createUserCareer(UserCareer userCareer) throws UserException {
        return userCareerService.createGroupUser(userCareer);
    }

    @Override
    public UserCareer updateUserCareer(UserCareer userCareer) throws UserException {
        return userCareerService.updateUserCareer(userCareer);
    }

    @Override
    public Optional<UserCareer> findGroupUserByIdUserCareer(UserCareer userCareer, int id) throws UserException {
        return userCareerRepository.findGroupUserById(id);
    }

    @Override
    public List<Career> findGroupOfUserCareer(UserCareer userCareer, String id) throws UserException {
        return userCareerRepository.findGroupsOfUser(id);
    }

    @Override
    public List<User> findUsersByGroupUserCareer(UserCareer userCareer, int id) throws UserException {
        return userCareerRepository.findUsersByGroup(id);
    }

    @Override
    public List<UserCareer> findAllUserCareer() throws UserException {
        return userCareerRepository.getAllGroupUser();
    }

    @Override
    public List<User> getEPSCommitteeUser(Boolean isCommitteeMember) throws UserException {
        return userRepository.getEPSCommitteeUser(isCommitteeMember);
    }

    @Override
    public List<User> getAuthenticatedUser() throws UserException {
        return userService.getAuthenticatedUser();
    }

    @Override
    public User resetPassword(User user) throws UserException {
        return userService.resetPassword(user);
    }

    @Override
    public boolean resetPassword(String userID, String userEmail) throws UserException {
        return userService.resetPassword(userID, userEmail);
    }

    @Override
    public Career updateCareer(Career career) throws UserException {
        return careerService.updateCareer(career, career.getName());
    }

    @Override
    public List<Career> getCareersOfUser(User user) {
        return userCareerRepository.getCareersOfUser(user);
    }

    @Override
    public List<UserCareer> getUserCareer(Career career) {
        return userCareerRepository.getUserCareer(career);
    }

    @Override
    public List<UserCareer> getUserCareer(UserCareer UserCareer) {
        return userCareerRepository.getUserCareer(UserCareer);
    }

    @Override
    public List<UserCareer> getUserCareer(User user) {
        return userCareerRepository.getUserCareer(user);
    }

    @Override
    public List<Rol> getAllRolUser() {
        return rolUserRepository.getAllRolUser();
    }

    @Override
    public List<Career> getAllCareer() {
        return careerRepository.getAll();
    }

    @Override
    public List<User> getCareerCoordinator(Process process) {
        return userRepository.getCareerCoordinator(process);
    }

    @Override
    public UserCareer getUserCareer(User user, String career) {
        return userCareerRepository.getUserCareer(user, career).get(0);
    }

}
