package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.process.Appointment;
import gt.edu.usac.cunoc.ingenieria.eps.process.DeliverEpsCompletionDocument;
import gt.edu.usac.cunoc.ingenieria.eps.process.DocumentFinalEps;
import gt.edu.usac.cunoc.ingenieria.eps.process.DocumentInitialEps;
import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.Supervision;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T12:40:29")
@StaticMetamodel(Process.class)
public class Process_ { 

    public static volatile SingularAttribute<Process, Short> preProjectToEPSCommission;
    public static volatile SingularAttribute<Process, Short> approvedEPSDevelopment;
    public static volatile SingularAttribute<Process, DocumentInitialEps> dOCUMENTINITIALEPSid;
    public static volatile SingularAttribute<Process, Short> approvalEPSCommission;
    public static volatile CollectionAttribute<Process, DocumentFinalEps> documentFinalEpsCollection;
    public static volatile SingularAttribute<Process, DeliverEpsCompletionDocument> dELIVEREPSCOMPLETIONDOCUMENTid;
    public static volatile SingularAttribute<Process, Short> approvedCareerCoordinator;
    public static volatile SingularAttribute<Process, Short> documentsToEPSCareerSupervisor;
    public static volatile SingularAttribute<Process, UserCareer> uSERCAREERid;
    public static volatile SingularAttribute<Process, Appointment> aPPOINTMENTid;
    public static volatile SingularAttribute<Process, Short> documentsToEPSCommission;
    public static volatile SingularAttribute<Process, Requeriment> rEQUERIMENTid;
    public static volatile SingularAttribute<Process, byte[]> documentApprovedCareerCoordinator;
    public static volatile SingularAttribute<Process, Integer> progress;
    public static volatile CollectionAttribute<Process, Supervision> supervisionCollection;
    public static volatile SingularAttribute<Process, Integer> id;
    public static volatile SingularAttribute<Process, Short> state;

}