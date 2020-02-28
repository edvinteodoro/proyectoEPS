package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.process.DocumentInitialEps;
import gt.edu.usac.cunoc.ingenieria.eps.process.ExtraFile;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-02-23T22:48:15")
@StaticMetamodel(Journal.class)
public class Journal_ { 

    public static volatile SingularAttribute<Journal, Date> dateTime;
    public static volatile SingularAttribute<Journal, String> annotation;
    public static volatile SingularAttribute<Journal, String> activity;
    public static volatile SingularAttribute<Journal, DocumentInitialEps> dOCUMENTINITIALEPSid;
    public static volatile SingularAttribute<Journal, String> description;
    public static volatile SingularAttribute<Journal, Integer> id;
    public static volatile CollectionAttribute<Journal, ExtraFile> extraFileCollection;

}