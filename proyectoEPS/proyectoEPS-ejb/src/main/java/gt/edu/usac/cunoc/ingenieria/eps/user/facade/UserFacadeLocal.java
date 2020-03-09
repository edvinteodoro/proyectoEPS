/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.user.facade;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import java.util.List;
import java.util.Optional;
import javax.ejb.Local;

/**
 *
 * @author charly
 */
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
    
    public List<UserCareer> findAllUserCareer(UserCareer userCareer) throws UserException;
    
}
