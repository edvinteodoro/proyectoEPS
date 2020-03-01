package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Bibliography;
import gt.edu.usac.cunoc.ingenieria.eps.project.DecimalCoordinate;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T12:40:29")
@StaticMetamodel(Project.class)
public class Project_ { 

    public static volatile SingularAttribute<Project, Byte[]> schedule;
    public static volatile ListAttribute<Project, DecimalCoordinate> decimalCoordinates;
    public static volatile SingularAttribute<Project, Byte[]> investmentPlan;
    public static volatile ListAttribute<Project, Objectives> objectives;
    public static volatile SingularAttribute<Project, Integer> id;
    public static volatile SingularAttribute<Project, Short> state;
    public static volatile SingularAttribute<Project, LocalDate> limitReceptionDate;
    public static volatile SingularAttribute<Project, String> title;
    public static volatile SingularAttribute<Project, Byte[]> annexed;
    public static volatile ListAttribute<Project, Bibliography> bibliographies;
    public static volatile ListAttribute<Project, Section> sections;

}