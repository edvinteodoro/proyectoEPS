package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-02-29T01:20:48")
@StaticMetamodel(Process.class)
public class Process_ { 

    public static volatile SingularAttribute<Process, Boolean> preProjectToEPSCommission;
    public static volatile ListAttribute<Process, Requeriment> requeriments;
    public static volatile SingularAttribute<Process, Boolean> documentsToEPSCommission;
    public static volatile SingularAttribute<Process, Boolean> approvedEPSDevelopment;
    public static volatile SingularAttribute<Process, byte[]> documentApprovedCareerCoordinator;
    public static volatile SingularAttribute<Process, Boolean> approvalEPSCommission;
    public static volatile SingularAttribute<Process, Integer> progress;
    public static volatile SingularAttribute<Process, Integer> id;
    public static volatile SingularAttribute<Process, Boolean> state;
    public static volatile SingularAttribute<Process, Boolean> approvedCareerCoordinator;
    public static volatile SingularAttribute<Process, Boolean> documentsToEPSCareerSupervisor;

}