package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Bibliography;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T20:34:43")
@StaticMetamodel(Project.class)
public class Project_ { 

    public static volatile SingularAttribute<Project, byte[]> schedule;
    public static volatile SingularAttribute<Project, Bibliography> bIBLIOGRAPHYid;
    public static volatile SingularAttribute<Project, byte[]> investmentPlan;
    public static volatile SingularAttribute<Project, Integer> id;
    public static volatile SingularAttribute<Project, Short> state;
    public static volatile SingularAttribute<Project, LocalDate> limitReceptionDate;
    public static volatile SingularAttribute<Project, byte[]> title;
    public static volatile SingularAttribute<Project, byte[]> annexed;

}