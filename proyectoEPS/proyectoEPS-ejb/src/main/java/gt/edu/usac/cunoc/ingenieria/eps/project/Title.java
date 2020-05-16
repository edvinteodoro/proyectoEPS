package gt.edu.usac.cunoc.ingenieria.eps.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TITLE")
public class Title implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "text")
    private String name;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECTION_id")
    private Section section;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TITLE_parent_id", referencedColumnName = "id")
    private Title titleParent;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "titleParent", orphanRemoval = true)
    private List<Title> titles;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "title", orphanRemoval = true)
    private Texto texto;

    public Title() {
        this.titles = new ArrayList<>();
        this.texto = new Texto();
        this.texto.setTitle(this);
    }

    public Title(String name) {
        this.name = name;
        this.texto = new Texto();
        this.texto.setTitle(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Title getTitleParent() {
        return titleParent;
    }

    public void setTitleParent(Title titleParent) {
        this.titleParent = titleParent;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<Title> titles) {
        this.titles = titles;
    }

    public void addChildTitle(){
        System.out.println("======================Agregando hijo");
        Title title = new Title();
        titles.add(title);
        title.setTitleParent(this);
    }
    
    public void removeChildTitle(Integer titleIndex){
        titles.get(titleIndex).setTitleParent(null);
        titles.remove(titleIndex.intValue());
    }

    public Texto getTexto() {
        return texto;
    }

    public void setTexto(Texto texts) {
        this.texto = texts;
    }
    
    public void addText(Texto text){
        texto = text;
        texto.setTitle(this);
    }
    
    public void removeText(Texto text){
        texto = null;
        texto.setTitle(null);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Title)) {
            return false;
        }
        Title other = (Title) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.project.Title[ id=" + id + " ]";
    }
    
}
