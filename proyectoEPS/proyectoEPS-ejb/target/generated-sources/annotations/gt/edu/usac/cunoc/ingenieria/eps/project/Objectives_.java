package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-02-23T22:48:15")
@StaticMetamodel(Objectives.class)
public class Objectives_ { 

    public static volatile SingularAttribute<Objectives, Date> lastModificationDate;
    public static volatile CollectionAttribute<Objectives, Correction> correctionCollection;
    public static volatile SingularAttribute<Objectives, Integer> id;
    public static volatile SingularAttribute<Objectives, Short> state;
    public static volatile SingularAttribute<Objectives, byte[]> text;
    public static volatile SingularAttribute<Objectives, Project> pROJECTid;

}