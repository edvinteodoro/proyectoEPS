package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Bibliography;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T20:34:43")
@StaticMetamodel(Correction.class)
public class Correction_ { 

    public static volatile SingularAttribute<Correction, LocalDate> date;
    public static volatile SingularAttribute<Correction, Bibliography> bIBLIOGRAPHYid;
    public static volatile SingularAttribute<Correction, Section> sECTIONid;
    public static volatile SingularAttribute<Correction, Objectives> oBJECTIVESid;
    public static volatile SingularAttribute<Correction, User> uSERuserId;
    public static volatile SingularAttribute<Correction, Integer> id;
    public static volatile SingularAttribute<Correction, byte[]> text;

}