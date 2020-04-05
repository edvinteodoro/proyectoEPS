
package User.repository;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author charly
 */
public class UserRepositoryTest {
    
    private static String GET_USER_BY_DPI = "SELECT u FROM user u WHERE u.dpi = :dpi";
    EntityManager entityManager = Mockito.mock(EntityManager.class);

//    @Test
//    public void getUserByDPITest() {
//        String dpi = "3350461420901";
//        TypedQuery<User> typeQuery = Mockito.mock(TypedQuery.class);
//
//        Mockito.when(entityManager.createQuery(GET_USER_BY_DPI, User.class)).thenReturn(typeQuery);
//        Mockito.when(typeQuery.setParameter("dpi", dpi)).thenReturn(typeQuery);
//        User user =  new User();
//        Mockito.when(typeQuery.getSingleResult()).thenReturn(user);
//
//        UserRepository userRepository = new UserRepository();
//        userRepository.setEntityManager(entityManager);
//        Optional<User> result = userRepository.getUserByUserId(dpi);
//        Assert.assertEquals(result.get(), user);
//    }
//
//    
//
//    @Test
//    public void getUserTest() {
//        User user = new User();
//        List<User> users = new ArrayList<>();
//        users.add(new User());
//        Predicate predicate = Mockito.mock(Predicate.class);
//        CriteriaQuery<User> criteriaQuery = Mockito.mock(CriteriaQuery.class);
//        List<User> result = null;
//        try {
//            result = mockittoWhen(users, predicate, criteriaQuery, null, user).getUser(user);
//        } catch (UserException ex) {
//            Logger.getLogger(UserRepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        //asserte
//        Assert.assertEquals(result, users);
//    }
//
//    
//
//    @Test
//    public void getUserWithNameTest() {
//        try {
//            User user = new User();
//            String name = "fulanito";
//            user.setFirstName(name);
//            List<User> users = new ArrayList<>();
//            users.add(new User());
//            Predicate predicate = Mockito.mock(Predicate.class);
//            CriteriaQuery<User> criteriaQuery = Mockito.mock(CriteriaQuery.class);
//            List<User> result = mockittoWhen(users, predicate, criteriaQuery, "firstName", user).getUser(user);
//
//            //asserte
//            Assert.assertEquals(result, users);
//
//            Predicate[] predicates = new Predicate[1];
//            predicates[0] = predicate;
//
//            //verefy query
//            Mockito.verify(criteriaQuery).where(predicates);
//        } catch (UserException ex) {
//            Logger.getLogger(UserRepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    
//
//    @Test
//    public void getUserWithidRolTest() {
//        try {
//            User user = new User();
//            Rol rolUser = new Rol(1, "Admin");
//            user.setROLid(rolUser);
//            List<User> users = new ArrayList<>();
//            users.add(new User());
//            Predicate predicate = Mockito.mock(Predicate.class);
//            CriteriaQuery<User> criteriaQuery = Mockito.mock(CriteriaQuery.class);
//            List<User> result = mockittoWhen(users, predicate, criteriaQuery, "id", user).getUser(user);
//
//            //asserte
//            Assert.assertEquals(result, users);
//
//            Predicate[] predicates = new Predicate[1];
//            predicates[0] = predicate;
//
//            //verefy query
//            Mockito.verify(criteriaQuery).where(predicates);
//        } catch (UserException ex) {
//            Logger.getLogger(UserRepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    
//    private UserRepository mockittoWhen(List<User> users, Predicate predicate, CriteriaQuery<User> criteriaQuery, String atribute, User user) {
//
//        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
//        Root<User> userR = Mockito.mock(Root.class);
//        TypedQuery<User> typeQuery = Mockito.mock(TypedQuery.class);
//
//        Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
//        Mockito.when(criteriaBuilder.createQuery(User.class)).thenReturn(criteriaQuery);
//        Mockito.when(criteriaQuery.from(User.class)).thenReturn(userR);
//        if (user.getCarnet() != null) {
//            Mockito.when(criteriaBuilder.equal(userR.get(atribute), user.getCarnet())).thenReturn(predicate);
//        }
//        if (user.getFirstName() != null) {
//            Mockito.when(criteriaBuilder.like(userR.get(atribute), "%" + user.getFirstName() + "%")).thenReturn(predicate);
//        }
//        if (user.getLastName() != null) {
//            Mockito.when(criteriaBuilder.like(userR.get(atribute), "%" + user.getLastName() + "%")).thenReturn(predicate);
//        }
//        if (user.getState() != null) {
//            Mockito.when(criteriaBuilder.equal(userR.get(atribute), user.getState())).thenReturn(predicate);
//        }
//        if (user.getROLid() != null) {
//            Mockito.when(criteriaBuilder.equal(userR.get(atribute), user.getROLid().getId())).thenReturn(predicate);
//        }
//
//        Mockito.when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
//        Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typeQuery);
//        Mockito.when(typeQuery.getResultList()).thenReturn(users);
//        Mockito.when(criteriaQuery.where(Mockito.any(Predicate[].class))).thenReturn(criteriaQuery);
//
//        UserRepository userRepository = new UserRepository();
//        userRepository.setEntityManager(entityManager);
//        return userRepository;
//    }
    
    
}
