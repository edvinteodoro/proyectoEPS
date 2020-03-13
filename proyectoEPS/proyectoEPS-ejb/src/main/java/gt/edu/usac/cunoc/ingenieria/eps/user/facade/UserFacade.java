package gt.edu.usac.cunoc.ingenieria.eps.user.facade;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
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
public class UserFacade implements UserFacadeLocal{
    
    private UserService userService;
    private UserRepository userRepository;
    private CareerService careerService;
    private CareerRepository careerRepository;
    private UserCareerService userCareerService;
    private UserCareerRepository userCareerRepository;
    private RolService rolUserService;
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
        return careerRepository.getAll();
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
    public List<UserCareer> findAllUserCareer(UserCareer userCareer) throws UserException {
        return userCareerRepository.getAllGroupUser();
    }

    @Override
    public List<User> getAuthenticatedUser() throws UserException {
        return userService.getAuthenticatedUser();
    }

    @Override
    public Career updateCareer(Career career) throws UserException {
        return careerService.updateCareer(career, career.getName());
    }
    
}
