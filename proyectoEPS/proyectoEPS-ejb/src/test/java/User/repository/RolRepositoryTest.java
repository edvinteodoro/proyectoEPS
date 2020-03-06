package User.repository;

import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.repository.RolRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
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
public class RolRepositoryTest {
    EntityManager entityManager = Mockito.mock(EntityManager.class);

    @Test
    public void getRolUser() {
        Rol rolUser = new Rol();
        List<Rol> rolUsers = new ArrayList<>();
        rolUsers.add(new Rol());
        Predicate predicate = Mockito.mock(Predicate.class);
        CriteriaQuery<Rol> criteriaQuery = Mockito.mock(CriteriaQuery.class);
        List<Rol> result=null;
        result = mockittoWhen(rolUsers, predicate, criteriaQuery, null, rolUser).getRoll(rolUser);

        //asserte
        Assert.assertEquals(result, rolUsers);

    }

    @Test
    public void getRolUserWithId() {
        Integer id = 1;
        Rol rolUser = new Rol();
        rolUser.setId(id);
        List<Rol> rolUsers = new ArrayList<>();
        rolUsers.add(new Rol());
        Predicate predicate = Mockito.mock(Predicate.class);
        CriteriaQuery<Rol> criteriaQuery = Mockito.mock(CriteriaQuery.class);
        List<Rol> result=null;
        result = mockittoWhen(rolUsers, predicate, criteriaQuery, "id", rolUser).getRoll(rolUser);

        //asserte
        Assert.assertEquals(result, rolUsers);

        Predicate[] predicates = new Predicate[1];
        predicates[0] = predicate;

        //verefy query
        Mockito.verify(criteriaQuery).where(predicates);
    }

    @Test
    public void getRolUserWithName() {
        String name = "charly";
        Rol rolUser = new Rol();
        rolUser.setName(name);
        List<Rol> rolUsers = new ArrayList<>();
        rolUsers.add(new Rol());
        Predicate predicate = Mockito.mock(Predicate.class);
        CriteriaQuery<Rol> criteriaQuery = Mockito.mock(CriteriaQuery.class);
        List<Rol> result=null;
        result = mockittoWhen(rolUsers, predicate, criteriaQuery, "name", rolUser).getRoll(rolUser);

        //asserte
        Assert.assertEquals(result, rolUsers);

        Predicate[] predicates = new Predicate[1];
        predicates[0] = predicate;

        //verefy query
        Mockito.verify(criteriaQuery).where(predicates);
    }

    private RolRepository mockittoWhen(List<Rol> rolUsers, Predicate predicate, CriteriaQuery<Rol> criteriaQuery, String atribute, Rol rolUser) {

        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Root<Rol> rolUserR = Mockito.mock(Root.class);
        TypedQuery<Rol> typeQuery = Mockito.mock(TypedQuery.class);

        Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        Mockito.when(criteriaBuilder.createQuery(Rol.class)).thenReturn(criteriaQuery);
        Mockito.when(criteriaQuery.from(Rol.class)).thenReturn(rolUserR);

        if (rolUser.getId() != null) {
            Mockito.when(criteriaBuilder.equal(rolUserR.get(atribute), rolUser.getId())).thenReturn(predicate);
        }
        if (rolUser.getName() != null) {
            Mockito.when(criteriaBuilder.like(rolUserR.get(atribute), "%" + rolUser.getName() + "%")).thenReturn(predicate);
        }

        Mockito.when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
        Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typeQuery);
        Mockito.when(typeQuery.getResultList()).thenReturn(rolUsers);
        Mockito.when(criteriaQuery.where(Mockito.any(Predicate[].class))).thenReturn(criteriaQuery);

        RolRepository rolUserRepository = new RolRepository();
        rolUserRepository.setEntityManager(entityManager);
        return rolUserRepository;
    }
}
