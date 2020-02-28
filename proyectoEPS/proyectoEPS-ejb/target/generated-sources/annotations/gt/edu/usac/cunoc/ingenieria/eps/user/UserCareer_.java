package gt.edu.usac.cunoc.ingenieria.eps.user;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-02-23T22:48:15")
@StaticMetamodel(UserCareer.class)
public class UserCareer_ { 

    public static volatile SingularAttribute<UserCareer, Career> cAREERcodigo;
    public static volatile SingularAttribute<UserCareer, User> uSERuserId;
    public static volatile SingularAttribute<UserCareer, Integer> id;
    public static volatile CollectionAttribute<UserCareer, Process> processCollection;

}