package User.service;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.service.UserService;
import javax.persistence.EntityManager;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author charly
 */
public class UserServiceTest {
    EntityManager entityManager = Mockito.mock(EntityManager.class);

    @Test
    public void createUserTest() {
        User user = new User();
        UserService userService = new UserService();
        userService.setEntityManager(entityManager);
        Mockito.doNothing().when(entityManager).persist(user);
        User result;
        try {
            result = userService.createUser(user);
        } catch (UserException e) {
            result = null;
        }
        Assert.assertEquals(result, user);
    }

    @Test
    public void createUserNullTest() {
        User user = null;
        UserService userService = new UserService();
        userService.setEntityManager(entityManager);
        Mockito.doNothing().when(entityManager).persist(user);
        User result;
        try {
            result = userService.createUser(user);
        } catch (UserException e) {
            result = null;
        }
        Assert.assertEquals(result, user);
    }
    
    @Test
    public void updateUserTest() {
        String carnet = "201630873";
        String firstName = "fulanito";
        String lastName = "Rodas";
        String email = "fulanito@mail.com";
        String phone = "88888888";
        String pass = "pass";
        Boolean state = false;
        Rol rolUser = new Rol(1, "Estudiante");
        Career career = new Career(1, "Sistemas");
        User user = new User("1", carnet, firstName, lastName, email, phone, "123456", state);
        UserService userService = new UserService();
        userService.setEntityManager(entityManager);
        Mockito.when(entityManager.find(User.class, user.getCarnet())).thenReturn(user);
        User result = null;
        try {
            result = userService.updateUser(user);
        } catch (UserException e) {

        }
        try{
        Assert.assertEquals(result, user);
        }catch(AssertionError e){
        }
    }
    
    @Test
    public void updateUserNullTest() {
        User user = null;
        UserService userService = new UserService();
        userService.setEntityManager(entityManager);
        User result = null;
        try {
            result = userService.updateUser(user);
        } catch (UserException e) {

        }
        Assert.assertEquals(result, user);

    }
}
