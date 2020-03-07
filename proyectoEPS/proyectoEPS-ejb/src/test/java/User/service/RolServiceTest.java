package User.service;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.service.RolService;
import javax.persistence.EntityManager;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author charly
 */
public class RolServiceTest {
    
    EntityManager entityManager = Mockito.mock(EntityManager.class);
    
    @Test
    public void createCareerTest() {
        String name = "Estudiante";
        Rol rolUser = new Rol(1, name);
        RolService rolUserService = new RolService();
        rolUserService.setEntityManager(entityManager);
        Mockito.doNothing().when(entityManager).persist(Rol.class);
        Rol result = null;
        try {
            result=rolUserService.createRolUser(rolUser);
        } catch (UserException e) {
        }
        //asserte
        Assert.assertEquals(result, rolUser);
    }
    @Test
    public void createCareerNullTest() {
        Rol rolUser = null;
        RolService rolUserService = new RolService();
        rolUserService.setEntityManager(entityManager);
        Mockito.doNothing().when(entityManager).persist(Rol.class);
        Rol result = null;
        try {
            result=rolUserService.createRolUser(rolUser);
        } catch (UserException e) {
        }
        //asserte
        Assert.assertEquals(result, rolUser);
    }
    @Test
    public void updateCareerTest() {
        String name = "Secretaria";
        int id = 1;
        Rol rolUser = new Rol();
        rolUser.setId(id);
        rolUser.setName(name);
        RolService rolUserService = new RolService();
        rolUserService.setEntityManager(entityManager);
        Mockito.when(entityManager.find(Rol.class, rolUser.getId())).thenReturn(rolUser);
        Rol result = null;
        try {
            result=rolUserService.updateRolUser(rolUser);
        } catch (UserException e) {
        }
        //asserte
        Assert.assertEquals(result, rolUser);
    }
    
    
    @Test
    public void updateCareerNullTest() {
        Rol rolUser = null;
        RolService rolUserService = new RolService();
        rolUserService.setEntityManager(entityManager);
        Rol result;
        try {
            result=rolUserService.updateRolUser(rolUser); 
        } catch (UserException e) {
            result=null;
        }
        //asserte
        Assert.assertEquals(result, rolUser);
    }
    
}
