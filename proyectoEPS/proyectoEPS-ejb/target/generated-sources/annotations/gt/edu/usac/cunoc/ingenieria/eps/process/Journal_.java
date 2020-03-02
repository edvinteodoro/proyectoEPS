package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.process.DocumentInitialEps;
import gt.edu.usac.cunoc.ingenieria.eps.process.ExtraFile;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T18:49:23")
@StaticMetamodel(Journal.class)
public class Journal_ { 

    public static volatile SingularAttribute<Journal, LocalDate> dateTime;
    public static volatile SingularAttribute<Journal, String> annotation;
    public static volatile SingularAttribute<Journal, String> activity;
    public static volatile SingularAttribute<Journal, DocumentInitialEps> dOCUMENTINITIALEPSid;
    public static volatile SingularAttribute<Journal, String> description;
    public static volatile SingularAttribute<Journal, Integer> id;
    public static volatile CollectionAttribute<Journal, ExtraFile> extraFileCollection;

}