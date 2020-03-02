package gt.edu.usac.cunoc.ingenieria.eps.user;

import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T18:49:23")
@StaticMetamodel(Career.class)
public class Career_ { 

    public static volatile SingularAttribute<Career, Integer> codigo;
    public static volatile SingularAttribute<Career, String> name;
    public static volatile CollectionAttribute<Career, UserCareer> userCareerCollection;

}