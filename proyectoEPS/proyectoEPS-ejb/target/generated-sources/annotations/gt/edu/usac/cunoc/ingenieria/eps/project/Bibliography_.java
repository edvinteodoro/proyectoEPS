package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-02-23T22:48:15")
@StaticMetamodel(Bibliography.class)
public class Bibliography_ { 

    public static volatile SingularAttribute<Bibliography, String> editorial;
    public static volatile SingularAttribute<Bibliography, String> country;
    public static volatile CollectionAttribute<Bibliography, Correction> correctionCollection;
    public static volatile SingularAttribute<Bibliography, String> city;
    public static volatile SingularAttribute<Bibliography, String> author;
    public static volatile SingularAttribute<Bibliography, Integer> id;
    public static volatile SingularAttribute<Bibliography, Date> publicactionYear;
    public static volatile SingularAttribute<Bibliography, String> title;
    public static volatile CollectionAttribute<Bibliography, Project> projectCollection;

}