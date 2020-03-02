package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T18:49:23")
@StaticMetamodel(Bibliography.class)
public class Bibliography_ { 

    public static volatile SingularAttribute<Bibliography, String> editorial;
    public static volatile SingularAttribute<Bibliography, String> country;
    public static volatile ListAttribute<Bibliography, Correction> corrections;
    public static volatile SingularAttribute<Bibliography, String> city;
    public static volatile SingularAttribute<Bibliography, String> author;
    public static volatile SingularAttribute<Bibliography, Project> project;
    public static volatile SingularAttribute<Bibliography, Integer> id;
    public static volatile SingularAttribute<Bibliography, LocalDate> publicactionYear;
    public static volatile SingularAttribute<Bibliography, String> title;

}