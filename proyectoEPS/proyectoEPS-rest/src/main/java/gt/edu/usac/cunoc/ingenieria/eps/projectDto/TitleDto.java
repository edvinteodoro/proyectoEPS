/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.projectDto;

import gt.edu.usac.cunoc.ingenieria.eps.project.Title;

/**
 *
 * @author teodoro
 */
public class TitleDto {
    
    private Integer id;
    private String name;
    private Integer position;

    public TitleDto(Title title) {
        this.id = title.getId();
        this.name = title.getName();
        this.position = title.getPosition();
    }

    public TitleDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
    
    
    
}
