package gt.edu.usac.cunoc.ingenieria.eps.journal;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "JOURNAL_LOG")
public class JournalLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "activity")
    private String activity;
    @Column(name = "dateTime")
    private LocalDate dateTime;
    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "journalLog", orphanRemoval = true)
    private List<Image> images;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "journalLog", orphanRemoval = true)
    private List<Link> links;
     @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "journalLog", orphanRemoval = true)
    private List<Commentary> commentaries;
    

    public JournalLog() {
        this.images = new ArrayList<>();
        this.links = new ArrayList<>();
        this.commentaries = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addImage(){
        Image newImage = new Image();
        images.add(newImage);
        newImage.setJournalLog(this);
    }
    
    public void removeImage(Integer imageIndex){
        images.get(imageIndex).setJournalLog(null);
        images.remove(imageIndex.intValue());
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
    
    public void addLink(){
        Link newLink = new Link();
        links.add(newLink);
        newLink.setJournalLog(this);
    }
    
    public void removeLink(Integer linkIndex){
        links.get(linkIndex).setJournalLog(null);
        links.remove(linkIndex.intValue());
    }

    public List<Commentary> getCommentaries() {
        return commentaries;
    }

    public void setCommentaries(List<Commentary> commentaries) {
        this.commentaries = commentaries;
    }
    
    public void addCommentay(){
        Commentary newCommentary = new Commentary();
        commentaries.add(newCommentary);
        newCommentary.setJournalLog(this);
    }
    
    public void removeComentary(Integer commentaryIndex){
        commentaries.get(commentaryIndex).setJournalLog(null);
        commentaries.remove(commentaryIndex.intValue());
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
        if (!(object instanceof JournalLog)) {
            return false;
        }
        JournalLog other = (JournalLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.journal.JournalLog[ id=" + id + " ]";
    }

}
