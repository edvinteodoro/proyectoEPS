package gt.edu.usac.cunoc.ingenieria.eps.process.facade;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.process.Appointment;
import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.tail.TailCoordinator;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.util.List;
import java.util.Optional;
import javax.ejb.Local;

@Local
public interface ProcessFacadeLocal {

    public List<Requeriment> getRequeriment(Requeriment requeriment);

    /**
     * Find the process base on ID
     *
     * @param id
     * @return
     * @throws UserException
     */
    public Optional<Process> findProcessById(Integer id) throws UserException;

    /**
     * This method use the Process ID, to verify and return if the process is on
     * <b>REVISION</b> and had been <b>APROVEED</b> by the Career Coordinator
     * and had <b>NOT BEEN APROVEED</b> by the EPS Committee
     *
     * if null isn't available the process
     *
     * @param id
     * @return
     * @throws UserException
     */
    public Optional<Process> ProcessAvailableToEPSCommittee(Integer id) throws UserException;

    /**
     * Send back the Process to the student and remove it from EPS tail
     *
     * TODO send email
     *
     * @param id
     * @return
     * @throws UserException
     */
    public Optional<Process> returnEPSCommitteeRevisionToStudent(Integer id) throws UserException;

    /**
     * Set the Precess as approved by EPS Committee, notify the student, remove
     * from EPS tail and send it to the next step
     *
     * TODO send email and move to next step
     *
     * @param id
     * @return
     * @throws UserException
     */
    public Optional<Process> aproveedByEPSCommittee(Integer id) throws UserException;

    /**
     * Set Process as <b>REJECTED</b> and remove from committee tail, and need
     * to add a message to the student
     *
     * TODO send email
     *
     * @param id
     * @param user
     * @param message
     * @return
     * @throws UserException
     */
    public Optional<Process> EPSCommitteeRejectProyect(Integer id, User user, String message) throws UserException;

    public List<Process> getProcess(Process process);

    public Requeriment createRequeriment(Requeriment requeriment);

    public Requeriment updaterequeriment(Requeriment requeriment);

    public Appointment createAppointment(Appointment appointment);

    public Appointment updateAppointment(Appointment appointment);

    public Optional<Appointment> findAppointmentById(Integer id);

    public Process createProcess(Process process);

    public List<Process> getProcessUser(User user);

    public Process updateProcess(Process process);

    public boolean rejectProcess(TailCoordinator tailCoordinator, String title, String msg);

    /**
     * This method assign a SUPERVISOR_EPS to the Process
     *
     * @param career Career to belong the student owning the process
     * @param process Process to Assign the SUPERVISOR_EPS
     * @throws ValidationException if not exist a SUPERVISOR_EPS for the career
     */
    public void assignEPSSUpervisorToProcess(Career career, Process process) throws ValidationException;

    /**
     * This method gets The Processes that have assign the SUPERVISOR_EPS
     *
     * @param supervisorEPS
     * @return List of processes
     */
    public List<Process> getProcessBySupervisorEPS(User supervisorEPS);

    /**
     * Allow Student to send the Advisor and Reviewer, if set users are new,
     * their are going to be create as inactive users
     *
     * @param process
     * @return
     * @throws UserException
     * @throws gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException
     */
    public Process sendAppointmentToSupervisor(Process process) throws UserException, ValidationException;

    /**
     * Apply action on Advisor and Reviewer base on Supervisor's resolution, and
     * send emails to notify the advisor and reviewer when have been assigned to
     * a Project and notify the resolution to the student.Verify if the logged
     * user as Supervisor is in charge of the process.
     *
     *
     *
     * @param process modified by the supervisor
     * @return
     * @throws UserException when problem create or removing temporalUser
     * @throws ValidationException when data is missing
     */
    public Process returnAppointmentToStudent(Process process) throws UserException, ValidationException;

    /**
     * This method is just for the Supervisor, after had read it's information
     *
     * @param process
     * @return
     * @throws ValidationException
     * @throws UserException
     */
    public Process enableCompanySupervisor(Process process) throws ValidationException, UserException;
    
    public Process sendCompanySupervisorToSupervisor(Process process) throws ValidationException, UserException;
    
    public boolean isAssignedAdvisorReviewer(Process process);
}
