package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import gt.edu.usac.cunoc.ingenieria.eps.project.Texto;
import gt.edu.usac.cunoc.ingenieria.eps.project.Title;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-02-23T22:48:15")
@StaticMetamodel(Title.class)
public class Title_ { 

    public static volatile CollectionAttribute<Title, Title> titleCollection;
    public static volatile SingularAttribute<Title, Title> tITLEid;
    public static volatile CollectionAttribute<Title, Section> sectionCollection;
    public static volatile CollectionAttribute<Title, Texto> textoCollection;
    public static volatile SingularAttribute<Title, Integer> id;
    public static volatile SingularAttribute<Title, byte[]> text;

}