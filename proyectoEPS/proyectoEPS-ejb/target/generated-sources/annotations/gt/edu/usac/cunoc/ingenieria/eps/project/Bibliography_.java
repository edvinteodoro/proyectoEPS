package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T20:34:43")
@StaticMetamodel(Bibliography.class)
public class Bibliography_ { 

    public static volatile SingularAttribute<Bibliography, String> editorial;
    public static volatile SingularAttribute<Bibliography, String> country;
    public static volatile CollectionAttribute<Bibliography, Correction> correctionCollection;
    public static volatile SingularAttribute<Bibliography, String> city;
    public static volatile SingularAttribute<Bibliography, String> author;
    public static volatile SingularAttribute<Bibliography, Integer> id;
    public static volatile SingularAttribute<Bibliography, LocalDate> publicactionYear;
    public static volatile SingularAttribute<Bibliography, String> title;
    public static volatile CollectionAttribute<Bibliography, Project> projectCollection;

}