package gt.edu.usac.cunoc.ingenieria.eps.configuration;

import java.time.format.DateTimeFormatter;

public class Constants {

    //Application constants
    public static final String PERSISTENCE_UNIT_NAME = "persistence";
    public static final String JDBC_RESOURCE = "jdbc_poolEPS";
    public static final String JAVA_MAIL_SESSION = "mail/__EPSingenieria";

    //User rols
    public static final String SECRETARIA_COORDINACION = "Secretaria_Coordinación";
    public static final String SECRETARIA_EPS = "Secretaria_EPS";
    public static final String DIRECTOR = "Director";
    public static final String COORDINADOR_CARRERA = "Coordinador_Carrera";
    public static final String COORDINADOR_EPS = "Coordinador_EPS";
    public static final String SUPERVISOR_EPS = "Supervisor_EPS";
    public static final String ESTUDIANTE = "Estudiante";
    public static final String REVISOR = "Revisor";
    public static final String ASESOR = "Asesor";
    public static final String SUPERVISOR_EMPRESA = "Supervisor_ORG";
    
    public static final DateTimeFormatter DATE_FORMAT_1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter DATE_FORMAT_2 = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH.mm");

}
