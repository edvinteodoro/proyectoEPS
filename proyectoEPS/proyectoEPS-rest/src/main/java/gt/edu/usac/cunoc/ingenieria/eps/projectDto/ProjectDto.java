package gt.edu.usac.cunoc.ingenieria.eps.projectDto;

import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import java.io.Serializable;


public class ProjectDto implements Serializable{
    
    private Integer id;
    private String title;
    private Short status;
    
    public ProjectDto(Project project){
        this.id=project.getId();
        this.title=project.getTitle();
        this.status=project.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
    
}
