package gt.edu.usac.cunoc.ingenieria.eps.projectDto;

import gt.edu.usac.cunoc.ingenieria.eps.project.Correction;
import gt.edu.usac.cunoc.ingenieria.eps.project.TypeCorrection;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author teodoro
 */
public class CorrectionDto implements Serializable {

    private Integer id;
    private String text;
    private TypeCorrection type;
    private LocalDate date;
    private Boolean status;
    
    public CorrectionDto(Correction correction){
        this.id=correction.getId();
        this.text=new String(correction.getText());
        this.type=correction.getType();
        this.date=correction.getDate();
        this.status=correction.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TypeCorrection getType() {
        return type;
    }

    public void setType(TypeCorrection type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    
}
