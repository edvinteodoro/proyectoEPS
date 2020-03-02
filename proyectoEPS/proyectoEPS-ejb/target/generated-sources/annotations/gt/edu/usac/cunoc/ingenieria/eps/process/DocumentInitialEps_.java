package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.process.Journal;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T18:49:23")
@StaticMetamodel(DocumentInitialEps.class)
public class DocumentInitialEps_ { 

    public static volatile SingularAttribute<DocumentInitialEps, User> uSERadviserProposal;
    public static volatile SingularAttribute<DocumentInitialEps, User> uSERreviewerProposal;
    public static volatile SingularAttribute<DocumentInitialEps, byte[]> approvalAct;
    public static volatile CollectionAttribute<DocumentInitialEps, Journal> journalCollection;
    public static volatile SingularAttribute<DocumentInitialEps, Integer> id;

}