
package gt.edu.usac.cunoc.ingenieria.eps.project;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.COORDINADOR_CARRERA;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CORRECTION")
public class Correction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "text")
    private byte[] text;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeCorrection type;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "status")
    private Boolean status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    private Section section;
    
    public Correction() {
    }
    
    public Correction(LocalDate date,User user,TypeCorrection type,Project project){
        this.date=date;
        this.user=user;
        this.type=type;
        this.project=project;
    }
    public Correction(LocalDate date,User user,TypeCorrection type,Project project,Section section){
        this.date=date;
        this.user=user;
        this.type=type;
        this.project=project;
        this.section=section;
    }

    public Correction(Integer id) {
        this.id = id;
    }

    public Correction(Integer id, byte[] text, LocalDate date) {
        this.id = id;
        this.text = text;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
    
    public Boolean getStatus(){
        return status;
    }
    
    public void setStatus(Boolean status){
        this.status=status;
    }

    public Project getProcess() {
        return project;
    }

    public void setProcess(Project project) {
        this.project = project;
    }

    public TypeCorrection getType() {
        return type;
    }

    public void setType(TypeCorrection type) {
        this.type = type;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getStyle() {
        String value="btn btn-danger btn-xs";
        if(status!=null && this.text!=null){
            if(status==false){
                value="btn btn-warning btn-xs";
            }else if(status==true){
                value="btn btn-warning btn-xs";
            }
        }else if(this.text==null){
            value="btn btn-primary btn-xs";
        }
        return value;
    }
    public String getTextHistory(){
        String value="";
        if(status!=null){
            if(status==false&&text!=null){
                value=new String(text);
            }
        }
        return value;
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
        if (!(object instanceof Correction)) {
            return false;
        }
        Correction other = (Correction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.project.Correction[ id=" + id + " ]";
    }
    
}
