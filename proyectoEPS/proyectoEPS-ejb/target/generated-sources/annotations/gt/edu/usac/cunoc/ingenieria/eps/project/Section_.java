package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.project.Title;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T20:34:43")
@StaticMetamodel(Section.class)
public class Section_ { 

    public static volatile SingularAttribute<Section, LocalDate> lastModificationDate;
    public static volatile CollectionAttribute<Section, Correction> correctionCollection;
    public static volatile SingularAttribute<Section, Title> tITLEid;
    public static volatile SingularAttribute<Section, String> name;
    public static volatile SingularAttribute<Section, Integer> id;
    public static volatile SingularAttribute<Section, Project> pROJECTid;

}