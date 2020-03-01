package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T12:40:29")
@StaticMetamodel(DeliverEpsCompletionDocument.class)
public class DeliverEpsCompletionDocument_ { 

    public static volatile SingularAttribute<DeliverEpsCompletionDocument, byte[]> adviserLetter;
    public static volatile SingularAttribute<DeliverEpsCompletionDocument, byte[]> counterpartSettlement;
    public static volatile SingularAttribute<DeliverEpsCompletionDocument, Short> deliveryFinalReport;
    public static volatile SingularAttribute<DeliverEpsCompletionDocument, byte[]> reviewerLetter;
    public static volatile SingularAttribute<DeliverEpsCompletionDocument, Integer> id;
    public static volatile CollectionAttribute<DeliverEpsCompletionDocument, Process> processCollection;

}