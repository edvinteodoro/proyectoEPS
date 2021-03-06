package gt.edu.usac.cunoc.ingenieria.eps.config;

import gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ASESOR;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_CARRERA;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_EPS;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SECRETARIA_COORDINACION;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SECRETARIA_EPS;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.DIRECTOR;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.ESTUDIANTE;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.REVISOR;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SUPERVISOR_EMPRESA;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.SUPERVISOR_EPS;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * This class return <b>user role</b> to validate permissions at in the XHTML
 *
 * @author angelrg
 */
@Named
@ViewScoped
public class ConstantsRol implements Serializable {

    /**
     * Include <b>SECRETARIA COORDINACION</b> y <b>SECRETARIA EPS</b>
     *
     * Extract the roles from <b>Constants</b> at EJB package
     *
     * @return
     */
    public String SECRETARIAS() {
        return SECRETARIA_COORDINACION + "," + SECRETARIA_EPS;
    }

    /**
     * Extract the roles from <b>Constants</b> at EJB package
     *
     * @return
     */
    public String SECRETARIA_COORDINACION() {
        return SECRETARIA_COORDINACION;
    }

    /**
     * Extract the roles from <b>Constants</b> at EJB package
     *
     * @return
     */
    public String SECRETARIA_EPS() {
        return SECRETARIA_EPS;
    }

    /**
     * Extract the roles from <b>Constants</b> at EJB package
     *
     * @return
     */
    public String COORDINACION() {
        return COORDINADOR_CARRERA + "," + COORDINADOR_EPS;
    }

    /**
     * Extract the roles from <b>Constants</b> at EJB package
     *
     * @return
     */
    public String COORDINADOR_CARRERA() {
        return COORDINADOR_CARRERA;
    }

    /**
     * Extract the roles from <b>Constants</b> at EJB package
     *
     * @return
     */
    public String COORDINADOR_EPS() {
        return COORDINADOR_EPS;
    }

    /**
     * Extract the roles from <b>Constants</b> at EJB package
     *
     * @return
     */
    public String DIRECTOR() {
        return DIRECTOR;
    }

    /**
     * Extract the roles from <b>Constants</b> at EJB package
     *
     * @return
     */
    public String ESTUDIANTE() {
        return ESTUDIANTE;
    }

    /**
     * Extract the roles from <b>Constants</b> at EJB package
     *
     * @return
     */
    public String SUPERVISOR_EPS() {
        return SUPERVISOR_EPS;
    }

    public String ASSIGNED_PROCESS_USERS(){
        return SUPERVISOR_EPS + "," + ASESOR + "," + REVISOR + "," + SUPERVISOR_EMPRESA;
    }
    
    public String COMMITTEE_EPS() {
        return COORDINADOR_EPS + "," + SUPERVISOR_EPS;
    }

    public String ADVISOR_REVIEWER_EDITORS() {
        return ESTUDIANTE + "," + SUPERVISOR_EPS;
    }

    public String PROCESS_REVIEWER(){
        return COMMITTEE_EPS() + "," + COORDINADOR_CARRERA;
    }
    
    public String CREATE_USER_PROCESS(){
        return DIRECTOR + "," + COORDINADOR_EPS + "," + SUPERVISOR_EPS + "," + SECRETARIA_EPS;
    }

}
