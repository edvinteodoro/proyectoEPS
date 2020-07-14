package gt.edu.usac.cunoc.ingenieria.eps.journal;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
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
import javax.persistence.ManyToOne;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "journal_Log", orphanRemoval = true)
    private List<Image> images;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "journal_Log", orphanRemoval = true)
    private List<Link> links;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "journal_Log", orphanRemoval = true)
    private List<Commentary> commentaries;

    @ManyToOne(fetch = FetchType.LAZY)
    private Process process;

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

    public void addImage(Image newImage) {
        images.add(newImage);
    }

    public void removeImage(Integer imageIndex) {
        images.get(imageIndex).setJournalLog(null);
        images.remove(imageIndex.intValue());
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link newLink) {
        links.add(newLink);
        newLink.setJournal_Log(this);
    }

    public void removeLink(Integer linkIndex) {
        links.get(linkIndex).setJournal_Log(null);
        links.remove(linkIndex.intValue());
    }

    public List<Commentary> getCommentaries() {
        return commentaries;
    }

    public void setCommentaries(List<Commentary> commentaries) {
        this.commentaries = commentaries;
    }

    public void addCommentay() {
        Commentary newCommentary = new Commentary();
        commentaries.add(newCommentary);
        newCommentary.setJournal_Log(this);
    }

    public void removeComentary(Integer commentaryIndex) {
        commentaries.get(commentaryIndex).setJournal_Log(null);
        commentaries.remove(commentaryIndex.intValue());
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public boolean getEmptyImages(){
        return this.images.isEmpty();
    }
    
    public boolean getEmptyLinks(){
        return this.links.isEmpty();
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
