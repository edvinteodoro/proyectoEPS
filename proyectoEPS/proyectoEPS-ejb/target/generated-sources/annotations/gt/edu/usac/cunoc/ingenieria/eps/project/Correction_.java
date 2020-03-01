package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Bibliography;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T12:40:29")
@StaticMetamodel(Correction.class)
public class Correction_ { 

    public static volatile SingularAttribute<Correction, LocalDate> date;
    public static volatile SingularAttribute<Correction, Bibliography> bibliography;
    public static volatile SingularAttribute<Correction, Section> section;
    public static volatile SingularAttribute<Correction, Integer> id;
    public static volatile SingularAttribute<Correction, Byte[]> text;
    public static volatile SingularAttribute<Correction, User> user;
    public static volatile SingularAttribute<Correction, Objectives> objective;

}