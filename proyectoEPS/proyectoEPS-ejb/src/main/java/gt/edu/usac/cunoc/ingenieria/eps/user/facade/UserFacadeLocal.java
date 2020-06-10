package gt.edu.usac.cunoc.ingenieria.eps.user.facade;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import java.util.List;
import java.util.Optional;
import javax.ejb.Local;

@Local
public interface UserFacadeLocal {

    public List<User> getAuthenticatedUser() throws UserException;

    public User createUser(User user) throws UserException;

    public User updateUser(User user) throws UserException;

    public List<User> getUser(User user) throws UserException;

    public Career createCareer(Career career) throws UserException;

    public Career updateCareer(Career career) throws UserException;

    public List<Career> getCareer(Career career) throws UserException;

    public Rol createRolUser(Rol rolUser) throws UserException;

    public Rol updateRolUser(Rol rolUser) throws UserException;

    public List<Rol> getRolUser(Rol rolUser) throws UserException;

    public UserCareer createUserCareer(UserCareer userCareer) throws UserException;

    public UserCareer updateUserCareer(UserCareer userCareer) throws UserException;

    public Optional<UserCareer> findGroupUserByIdUserCareer(UserCareer userCareer, int id) throws UserException;

    public List<Career> findGroupOfUserCareer(UserCareer userCareer, String id) throws UserException;

    public List<User> findUsersByGroupUserCareer(UserCareer userCareer, int id) throws UserException;

    public List<UserCareer> findAllUserCareer() throws UserException;

    /**
     * This feature is design to generate a new password
     *
     * Use UUID strategy to generate the password
     *
     * @param user
     * @return
     * @throws UserException
     */
    public User resetPassword(User user) throws UserException;

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
    public boolean resetPassword(String userID, String userEmail) throws UserException;

    public List<Career> getCareersOfUser(User user);

    public List<UserCareer> getUserCareer(Career career);

    public List<UserCareer> getUserCareer(UserCareer UserCareer);

    public List<UserCareer> getUserCareer(User user);

    public List<Rol> getAllRolUser();

    public List<Career> getAllCareer();

    public List<User> getCareerCoordinator(Process process);

    public UserCareer getUserCareer(User user, String career);
}
