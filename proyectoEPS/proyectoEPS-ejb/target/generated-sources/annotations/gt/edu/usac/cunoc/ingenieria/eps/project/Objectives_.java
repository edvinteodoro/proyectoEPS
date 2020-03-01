package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T12:40:29")
@StaticMetamodel(Objectives.class)
public class Objectives_ { 

    public static volatile SingularAttribute<Objectives, LocalDate> lastModificationDate;
    public static volatile ListAttribute<Objectives, Correction> corrections;
    public static volatile SingularAttribute<Objectives, Project> project;
    public static volatile SingularAttribute<Objectives, Integer> id;
    public static volatile SingularAttribute<Objectives, Short> state;
    public static volatile SingularAttribute<Objectives, Byte[]> text;

}