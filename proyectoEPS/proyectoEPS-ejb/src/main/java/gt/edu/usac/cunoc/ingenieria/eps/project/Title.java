
package gt.edu.usac.cunoc.ingenieria.eps.project;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TITLE")
public class Title implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "text")
    private Byte[] text;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Section section;
   
    @ManyToOne(fetch = FetchType.LAZY)
    private Title titleParent;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "titleParent", orphanRemoval = true)
    private ArrayList<Title> titles = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "title", orphanRemoval = true)
    private ArrayList<Texto> texts = new ArrayList<>();

    public Title() {
    }

    public Title(Integer id) {
        this.id = id;
    }

    public Title(Integer id, Byte[] text) {
        this.id = id;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte[] getText() {
        return text;
    }

    public void setText(Byte[] text) {
        this.text = text;
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

    public ArrayList<Title> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<Title> titles) {
        this.titles = titles;
    }

    public void addChildTitle(Title title){
        titles.add(title);
        title.setTitleParent(this);
    }
    
    public void removeChildTitle(Title title){
        titles.remove(title);
        title.setTitleParent(null);
    }

    public ArrayList<Texto> getTexts() {
        return texts;
    }

    public void setTexts(ArrayList<Texto> texts) {
        this.texts = texts;
    }
    
    public void addText(Texto text){
        texts.add(text);
        text.setTitle(this);
    }
    
    public void removeText(Texto text){
        texts.remove(text);
        text.setTitle(null);
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
