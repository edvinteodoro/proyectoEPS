package gt.edu.usac.cunoc.ingenieria.eps.project;

import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;

/**
 *
 * @author teodoro
 */
public class RequerimentDto {
    private Integer id;

    public RequerimentDto(Requeriment requeriment) {
        this.id = requeriment.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
