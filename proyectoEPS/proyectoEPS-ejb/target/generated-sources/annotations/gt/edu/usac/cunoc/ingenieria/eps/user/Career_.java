package gt.edu.usac.cunoc.ingenieria.eps.user;

import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-02-29T01:20:48")
@StaticMetamodel(Career.class)
public class Career_ { 

    public static volatile SingularAttribute<Career, Integer> codigo;
    public static volatile SingularAttribute<Career, String> name;
    public static volatile CollectionAttribute<Career, UserCareer> userCareerCollection;

}