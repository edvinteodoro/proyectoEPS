
package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bIBLIOGRAPHYid")
    private Collection<Project> projectCollection;
    @OneToMany(mappedBy = "bIBLIOGRAPHYid")
    private Collection<Correction> correctionCollection;

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

    public Collection<Project> getProjectCollection() {
        return projectCollection;
    }

    public void setProjectCollection(Collection<Project> projectCollection) {
        this.projectCollection = projectCollection;
    }

    public Collection<Correction> getCorrectionCollection() {
        return correctionCollection;
    }

    public void setCorrectionCollection(Collection<Correction> correctionCollection) {
        this.correctionCollection = correctionCollection;
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
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.project.Bibliography[ id=" + id + " ]";
    }
    
}
