package gt.edu.usac.cunoc.ingenieria.eps.user.repository;

import User.exception.UserException;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_CARRERA;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SUPERVISOR_EPS;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.process.StateProcess;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
@LocalBean
public class UserRepository {

    public static final String GET_USER_CAREER_ROL = "SELECT u.uSERuserId FROM UserCareer u WHERE u.uSERuserId.status=TRUE AND u.cAREERcodigo.codigo=:codigo AND u.uSERuserId.rOLid.name=:rolName";
    public static final String GET_NUMBER_PROCESSES_SUPERVISOR_EPS = "SELECT COUNT(c.id) FROM Process c WHERE c.supervisor_EPS.userId=:userIdSupervisorEPS AND (c.state != :RECHAZADO AND c.state != :INACTIVO)";

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<User> getUserByUserId(String userId) {
        TypedQuery<User> typeQuerry = entityManager.createQuery("SELECT u FROM USER u WHERE u.userId = :userId", User.class);
        typeQuerry.setParameter("userId", userId);
        try {
            return Optional.of(typeQuerry.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<User> getCareerCoordinator(Process process) {
        Query query = entityManager.createQuery(GET_USER_CAREER_ROL);
        query.setParameter("codigo", process.getUserCareer().getCAREERcodigo().getCodigo());
        query.setParameter("rolName", COORDINADOR_CARRERA);
        return query.getResultList();
    }

    /**
     * This method allows return 3 different result
     *
     * - if isCommitteeMember is null return all users with <b>rolID</b> as
     * <b>SUPERVISOR_EPS</b>
     *
     * - if isCommitteeMember is true return all users <b>SUPERVISOR_EPS</b> and
     * that are part of the EPS Committee
     *
     * - if isCommitteeMember is true return all users <b>SUPERVISOR_EPS</b> and
     * that are not part of the EPS Committee
     *
     * @param isCommitteeMember
     * @return
     * @throws UserException
     */
    public List<User> getEPSCommitteeUser(Boolean isCommitteeMember) throws UserException {
        if (isCommitteeMember == null) {
            return getUser(new User(new Rol(null, SUPERVISOR_EPS), null));
        } else {
            return getUser(new User(new Rol(null, SUPERVISOR_EPS), isCommitteeMember));
        }
    }

    /**
     * This method use almost all possible parameters to do a search
     *
     * @param user
     * @return
     * @throws UserException
     */
    public List<User> getUser(User user) throws UserException {
        if (user == null) {
            throw new UserException("User is null");
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userR = criteriaQuery.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        if (user.getUserId() != null && !user.getUserId().isEmpty()) {
            predicates.add(criteriaBuilder.equal(userR.get("userId"), user.getUserId()));
        }
        if (user.getDpi() != null && !user.getDpi().isEmpty()) {
            predicates.add(criteriaBuilder.equal(userR.get("dpi"), user.getDpi()));
        }
        if (user.getCodePersonal() != null) {
            predicates.add(criteriaBuilder.equal(userR.get("codePersonal"), user.getCodePersonal()));
        }
        if (user.getAcademicRegister() != null && !user.getAcademicRegister().isEmpty()) {
            predicates.add(criteriaBuilder.equal(userR.get("academicRegister"), user.getAcademicRegister()));
        }
        if (user.getFirstName() != null) {
            predicates.add(criteriaBuilder.like(userR.get("firstName"), "%" + user.getFirstName() + "%"));
        }
        if (user.getLastName() != null) {
            predicates.add(criteriaBuilder.like(userR.get("lastName"), "%" + user.getLastName() + "%"));
        }
        if (user.getEmail() != null) {
            predicates.add(criteriaBuilder.like(userR.get("email"), "%" + user.getEmail() + "%"));
        }
        if (user.getPhone1() != null && !user.getPhone1().isEmpty()) {
            predicates.add(criteriaBuilder.like(userR.get("phone"), "%" + user.getPhone1() + "%"));
        }
        if (user.getDirection() != null) {
            predicates.add(criteriaBuilder.like(userR.get("direction"), "%" + user.getDirection() + "%"));
        }
        if (user.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(userR.get("status"), user.getStatus()));
        }
        if (user.getEpsCommittee() != null) {
            predicates.add(criteriaBuilder.equal(userR.get("epsCommittee"), user.getEpsCommittee()));
        }
        if (user.getROLid()!= null && user.getROLid().getName() != null && !user.getROLid().getName().replace(" ", "").isEmpty()) {
            predicates.add(criteriaBuilder.equal(userR.get("rOLid").get("name"), user.getROLid().getName()));
        }

        criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    /**
     * This method gets a List of USER with SUPERVISOR_EPS role that belong to a
     * certain Career
     *
     * @param careerToSearch career to which SUPERVISOR_EPS belong
     * @return List of USER with SUPERVISOR_EPS role that belong to
     * careerToSearch
     */
    public List<User> getSupervisorEPSbyCareer(Career careerToSearch) {
        Query query = entityManager.createQuery(GET_USER_CAREER_ROL);
        query.setParameter("codigo", careerToSearch.getCodigo());
        query.setParameter("rolName", SUPERVISOR_EPS);
        return query.getResultList();
    }

    /**
     * This method gets the number of processes with status != RECHAZADO or
     * INACTIVO that are assigned to a SUPERVISOR_EPS
     *
     * @param supervisorEPS
     * @return
     */
    public Long getNumberProcessesBySupervisorEPS(User supervisorEPS) {
        Query query = entityManager.createQuery(GET_NUMBER_PROCESSES_SUPERVISOR_EPS);
        query.setParameter("userIdSupervisorEPS", supervisorEPS.getUserId());
        query.setParameter("RECHAZADO", StateProcess.RECHAZADO);
        query.setParameter("INACTIVO", StateProcess.INACTIVO);
        return (Long) query.getSingleResult();
    }
}
