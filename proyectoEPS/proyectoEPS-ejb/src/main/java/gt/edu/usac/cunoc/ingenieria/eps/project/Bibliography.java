
package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BIBLIOGRAPHY")
public class Bibliography implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "author")
    private String author;
    @Column(name = "publicactionYear")
    private LocalDate publicactionYear;
    @Column(name = "title")
    private String title;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "editorial")
    private String editorial;
    @Column(name = "position")
    private Integer position;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    public Bibliography() {
    }

    public Bibliography(Integer id) {
        this.id = id;
    }

    public Bibliography(Integer id, String author, LocalDate publicactionYear, String title, String city, String country, String editorial) {
        this.id = id;
        this.author = author;
        this.publicactionYear = publicactionYear;
        this.title = title;
        this.city = city;
        this.country = country;
        this.editorial = editorial;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublicactionYear() {
        return publicactionYear;
    }
    
    public String getPublicationYearText(){
        return getPublicactionYear().format(Constants.DATE_FORMAT_1);
    }

    public void setPublicactionYear(LocalDate publicactionYear) {
        this.publicactionYear = publicactionYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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
        if (!(object instanceof Bibliography)) {
            return false;
        }
        Bibliography other = (Bibliography) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.project.Bibliography[ id=" + id + " ]";
    }
    
}
