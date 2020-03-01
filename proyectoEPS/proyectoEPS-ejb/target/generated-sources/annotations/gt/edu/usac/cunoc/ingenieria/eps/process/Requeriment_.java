package gt.edu.usac.cunoc.ingenieria.eps.process;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T12:40:29")
@StaticMetamodel(Requeriment.class)
public class Requeriment_ { 

    public static volatile SingularAttribute<Requeriment, byte[]> ePSpreproject;
    public static volatile SingularAttribute<Requeriment, Project> pREPROJECTid;
    public static volatile SingularAttribute<Requeriment, byte[]> writtenRequest;
    public static volatile SingularAttribute<Requeriment, byte[]> propedeuticConstancy;
    public static volatile SingularAttribute<Requeriment, byte[]> inscriptionConstancy;
    public static volatile SingularAttribute<Requeriment, byte[]> pensumClosure;
    public static volatile SingularAttribute<Requeriment, Integer> id;
    public static volatile CollectionAttribute<Requeriment, Process> processCollection;
    public static volatile SingularAttribute<Requeriment, byte[]> aEIOsettlement;

}