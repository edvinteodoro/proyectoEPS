package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T18:49:23")
@StaticMetamodel(Supervision.class)
public class Supervision_ { 

    public static volatile SingularAttribute<Supervision, LocalDate> date;
    public static volatile SingularAttribute<Supervision, String> annotation;
    public static volatile SingularAttribute<Supervision, Integer> id;
    public static volatile SingularAttribute<Supervision, Process> pROCESSid;

}