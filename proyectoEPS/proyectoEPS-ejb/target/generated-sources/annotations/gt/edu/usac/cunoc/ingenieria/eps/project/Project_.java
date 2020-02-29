package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Bibliography;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-02-29T01:20:48")
@StaticMetamodel(Project.class)
public class Project_ { 

    public static volatile SingularAttribute<Project, byte[]> schedule;
    public static volatile SingularAttribute<Project, Bibliography> bIBLIOGRAPHYid;
    public static volatile SingularAttribute<Project, byte[]> investmentPlan;
    public static volatile SingularAttribute<Project, Integer> id;
    public static volatile SingularAttribute<Project, Short> state;
    public static volatile SingularAttribute<Project, Date> limitReceptionDate;
    public static volatile SingularAttribute<Project, byte[]> title;
    public static volatile SingularAttribute<Project, byte[]> annexed;

}