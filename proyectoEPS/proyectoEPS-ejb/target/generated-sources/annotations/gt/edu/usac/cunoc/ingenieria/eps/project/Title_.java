package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import gt.edu.usac.cunoc.ingenieria.eps.project.Texto;
import gt.edu.usac.cunoc.ingenieria.eps.project.Title;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-01T18:49:23")
@StaticMetamodel(Title.class)
public class Title_ { 

    public static volatile SingularAttribute<Title, Title> titleParent;
    public static volatile ListAttribute<Title, Texto> texts;
    public static volatile SingularAttribute<Title, Section> section;
    public static volatile SingularAttribute<Title, Integer> id;
    public static volatile SingularAttribute<Title, Byte[]> text;
    public static volatile ListAttribute<Title, Title> titles;

}