package User.service;

import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.service.CareerService;
import java.security.acl.Group;
import javax.persistence.EntityManager;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author charly
 */
public class CareerServiceTest {
    
    @Test
    public void createGroupTest(){
        // Arrange
        Career career = new Career();
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        
        Mockito.doNothing().when(entityManager).persist(career);
                
        CareerService careerService = new CareerService();
        careerService.setEntityManager(entityManager);
        
        // Act
        Career result = careerService.createCareer(career);
        
        // Assert
        Assert.assertEquals(result, career);
    }
    
    @Test
    public void updateGroupInformationTest(){
        // Arrange
        String name = "name";
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        Career career = Mockito.mock(Career.class);
        
        Mockito.when(
                entityManager.merge(career)
        ).thenReturn(career);
        
        CareerService careerService = new CareerService();
        careerService.setEntityManager(entityManager);
        
        // Act
        Career result = careerService.updateCareer(career, name);
        
        //Assert
        Assert.assertEquals(result, career);
        
    }
    
    @Test
    public void updateGroupSectionTest(){
        // Arrange
        String name = "name";
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        Career career = Mockito.mock(Career.class);
        
        Mockito.when(
                entityManager.merge(career)
        ).thenReturn(career);
        
        CareerService careerService = new CareerService();
        careerService.setEntityManager(entityManager);
        
        // Act
        Career result = careerService.updateCareer(career, name);
        
        //Assert
        Assert.assertEquals(result, career);
        
    }
    
    @Test
    public void updateGroupSectionAndInformationTest(){
        // Arrange
        String name = "name";
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        Career career = Mockito.mock(Career.class);
        
        Mockito.when(
                entityManager.merge(career)
        ).thenReturn(career);
        
        CareerService careerService = new CareerService();
        careerService.setEntityManager(entityManager);
        
        // Act
        Career result = careerService.updateCareer(career, name);
        
        //Assert
        Assert.assertEquals(result, career);
        
    }
    
}
