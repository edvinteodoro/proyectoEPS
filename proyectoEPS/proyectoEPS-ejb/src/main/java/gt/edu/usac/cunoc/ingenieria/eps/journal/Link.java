package gt.edu.usac.cunoc.ingenieria.eps.journal;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LINK")
public class Link implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "link")
    private byte[] link;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private JournalLog journal_Log;

    public Link() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getLink() {
        return link;
    }
    
    public String getLinkText(){
        return new String(link);
    }

    public void setLink(byte[] link) {
        this.link = link;
    }

    public JournalLog getJournal_Log() {
        return journal_Log;
    }

    public void setJournal_Log(JournalLog journal_Log) {
        this.journal_Log = journal_Log;
    }

    public String getLinkString(){
        return new String(link);
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
        if (!(object instanceof Link)) {
            return false;
        }
        Link other = (Link) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.journal.Link[ id=" + id + " ]";
    }
}
