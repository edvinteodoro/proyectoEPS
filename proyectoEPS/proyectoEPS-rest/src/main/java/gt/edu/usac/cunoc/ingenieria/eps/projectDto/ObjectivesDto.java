package gt.edu.usac.cunoc.ingenieria.eps.projectDto;

import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;

/**
 *
 * @author teodoro
 */
public class ObjectivesDto implements Serializable {

    private Integer id;
    private Short type;
    private String text;
    private LocalDate lastModificationDate;
    private Integer position;

    public ObjectivesDto(Objectives objetives) {
        this.id = objetives.getId();
        this.type = objetives.getType();
        this.text = objetives.getText();
        this.lastModificationDate = objetives.getLastModificationDate();
        this.position = objetives.getPosition();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(LocalDate lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
    
    
    
}
