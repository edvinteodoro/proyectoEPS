
package project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProjectTest {
    
    Project testProject = new Project();
    
    @Test
    public void testSetAndGetId() throws Exception{
        //Arrange
        Integer testCode = 100;
        
        //Act
        testProject.setId(testCode);
        Integer result = testProject.getId();
        
        //Assert
        Assert.assertEquals(result, testCode);
    }
    
    @Test
    public void testSetAndGetTitle() throws Exception{
        //Arrange
        String testTitle = "Titulo de Prueba";
        
        //Act
        testProject.setTitle(testTitle);
        String result = testProject.getTitle();
        
        //Assert
        Assert.assertEquals(result, testTitle);
    }
    
    @Test
    public void testSetAndGetState() throws Exception{
        //Arrange
        Short testState = 1;
        
        //Act
        testProject.setState(testState);
        Short result = testProject.getState();
        
        //Assert
        Assert.assertEquals(result, testState);
    }
    
    @Test
    public void testSetAndGeSchedule() throws Exception{
        //Arrange
        Path fileTestPath = Paths.get("src/test/java/testFile/testFile.pdf");
        byte[] testScheduleByte = Files.readAllBytes(fileTestPath);
        Byte[] testSchedule = new Byte[testScheduleByte.length] ;
        testSchedule = testProject.convertByteToObject(testScheduleByte);
        
        //Act
        testProject.setSchedule(testSchedule);
        Byte[] result = testProject.getSchedule();
        
        //Assert
        Assert.assertEquals(result, testSchedule);
    }
    
    @Test
    public void testSetAndGetInvestmentPlan() throws Exception{
        //Arrange
        Path fileTestPath = Paths.get("src/test/java/testFille/testFile.pdf");
        byte[] testInvestmentPlanByte = Files.readAllBytes(fileTestPath);
        Byte[] testInvestmentPlan = new Byte[testInvestmentPlanByte.length];
        testInvestmentPlan = testProject.convertByteToObject(testInvestmentPlanByte);
        
        //Act
        testProject.setInvestmentPlan(testInvestmentPlan);
        Byte[] result = testProject.getInvestmentPlan();
        
        ////Assert
        Assert.assertEquals(result, testInvestmentPlan);
    }
    
    @Test
    public void testSetAndGetAnnexed() throws Exception{
        //Arrange
        Path fileTestPath = Paths.get("src/test/java/testFille/testFile.pdf");
        byte[] testAnnexedByte = Files.readAllBytes(fileTestPath);
        Byte[] testAnnexed = new Byte[testAnnexedByte.length];
        testAnnexed = testProject.convertByteToObject(testAnnexedByte);
        
        //Act
        testProject.setAnnexed(testAnnexed);
        Byte[] result = testProject.getAnnexed();
        
        //Assert
        Assert.assertEquals(result, testAnnexed);
    }
    
    @Test
    public void testSetAndGetLimitReceptionDate() throws Exception{
        //Arrange
        LocalDate testLimitReceptionDate = LocalDate.now();
        
        //Act
        testProject.setLimitReceptionDate(testLimitReceptionDate);
        LocalDate result = testProject.getLimitReceptionDate();
        
        //Assert
        Assert.assertEquals(result, testLimitReceptionDate);
    }
}
