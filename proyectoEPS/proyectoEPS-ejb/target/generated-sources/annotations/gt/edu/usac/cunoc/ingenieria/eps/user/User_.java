package gt.edu.usac.cunoc.ingenieria.eps.user;

import gt.edu.usac.cunoc.ingenieria.eps.process.Appointment;
import gt.edu.usac.cunoc.ingenieria.eps.process.DocumentInitialEps;
import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T18:49:23")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, Rol> rOLid;
    public static volatile CollectionAttribute<User, DocumentInitialEps> documentInitialEpsCollection1;
    public static volatile CollectionAttribute<User, Appointment> appointmentCollection1;
    public static volatile CollectionAttribute<User, UserCareer> userCareerCollection;
    public static volatile SingularAttribute<User, String> userId;
    public static volatile CollectionAttribute<User, Appointment> appointmentCollection;
    public static volatile SingularAttribute<User, String> academicRegister;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> password;
    public static volatile CollectionAttribute<User, Correction> correctionCollection;
    public static volatile SingularAttribute<User, String> carnet;
    public static volatile SingularAttribute<User, String> phone;
    public static volatile CollectionAttribute<User, DocumentInitialEps> documentInitialEpsCollection;
    public static volatile SingularAttribute<User, Short> state;
    public static volatile SingularAttribute<User, String> dpi;
    public static volatile SingularAttribute<User, String> codePersonal;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> direction;

}