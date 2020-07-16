package gt.edu.usac.cunoc.ingenieria.eps.journal;

import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.io.Serializable;
import java.time.LocalDate;
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
import javax.persistence.Table;

@Entity
@Table(name = "COMMENTARY")
public class Commentary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "text")
    private byte[] text;
    @Column(name = "date")
    private LocalDate date;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "JOURNAL_LOG", referencedColumnName = "id")
    private JournalLog journal_Log;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    public Commentary() {
    }

    public Commentary(String text, LocalDate date, JournalLog journalLog, User user) {
        this.text = text.getBytes();
        this.date = date;
        this.journal_Log = journalLog;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getText() {
        return text;
    }

    public void setText(byte[] text) {
        this.text = text;
    }

    public String getTextValue() {
        return new String(this.text);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public JournalLog getJournal_Log() {
        return journal_Log;
    }

    public void setJournal_Log(JournalLog journal_Log) {
        this.journal_Log = journal_Log;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommentaryString() {
        return new String(text);
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
        if (!(object instanceof Commentary)) {
            return false;
        }
        Commentary other = (Commentary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.journal.Commentary[ id=" + id + " ]";
    }
}
