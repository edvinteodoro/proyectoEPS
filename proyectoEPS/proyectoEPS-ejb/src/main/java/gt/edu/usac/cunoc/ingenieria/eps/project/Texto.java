
package gt.edu.usac.cunoc.ingenieria.eps.project;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TEXTO")
public class Texto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "text")
    private byte[] text;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TITLE_id", referencedColumnName = "id")
    private Title title;
    
    public Texto() {
    }

    public Texto(Integer id) {
        this.id = id;
    }

    public Texto(Integer id, byte[] text) {
        this.id = id;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        if (this.text != null){
            String result = new String(this.text);
            return result;
        } else {
            return new String();
        }
    }

    public void setText(String text) {
        if (text != null){
            this.text = text.getBytes();
        }
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
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
        if (!(object instanceof Texto)) {
            return false;
        }
        Texto other = (Texto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.project.Texto[ id=" + id + " ]";
    }
    
}
