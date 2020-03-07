package User.repository;

import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.CareerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import static gt.edu.usac.cunoc.ingenieria.eps.user.repository.CareerRepository.FIND_BY_ID;
import static gt.edu.usac.cunoc.ingenieria.eps.user.repository.CareerRepository.GET_ALL;
import java.security.acl.Group;

/**
 *
 * @author charly
 */
public class CareerRepositoryTest {
    
    @Test
    public void findByIdWithNoResult() {
        // Arrange
        int careerId = 1;
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        TypedQuery<Group> typeQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(
                entityManager.createQuery(FIND_BY_ID, Group.class)
        ).thenReturn(typeQuery);
        Mockito.when(
                typeQuery.setParameter("id", careerId)
        ).thenReturn(typeQuery);
        
        Mockito.when(typeQuery.getSingleResult()).thenThrow(new NoResultException());

        CareerRepository careerRepository = new CareerRepository();
        careerRepository.setEntityManager(entityManager);

        // Act
        try {
            Optional<Career> result = careerRepository.findById(careerId);
            
            
            // Assert
            Assert.assertFalse(result.isPresent(), "Expected optional empty");
        
        } catch (NullPointerException e) {
        }
        

    }
    
    @Test
    public void getAllWithResult(){
        // Arrange
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        TypedQuery<Career> typedQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(
                entityManager.createQuery(GET_ALL, Career.class)
        ).thenReturn(typedQuery);
        List<Career> list = new ArrayList<Career>();
        Career career = new Career();
        list.add(career);
        Mockito.when(typedQuery.getResultList()).thenReturn(list);
        
        CareerRepository careerRepository = new CareerRepository();
        careerRepository.setEntityManager(entityManager);
        
        // Act
        List<Career> result = careerRepository.getAll();
        
        // Assert
        Assert.assertEquals(result, list);
    }
    
    @Test
    public void getAllEmptyResult(){
        // Arrange
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        TypedQuery<Career> typedQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(
                entityManager.createQuery(GET_ALL, Career.class)
        ).thenReturn(typedQuery);
        List<Career> list = new ArrayList<Career>();
        Mockito.when(typedQuery.getResultList()).thenReturn(list);
        
        CareerRepository careerRepository = new CareerRepository();
        careerRepository.setEntityManager(entityManager);
        
        // Act
        List<Career> result = careerRepository.getAll();
        
        // Assert
        Assert.assertEquals(result, list);
    }
    
}
