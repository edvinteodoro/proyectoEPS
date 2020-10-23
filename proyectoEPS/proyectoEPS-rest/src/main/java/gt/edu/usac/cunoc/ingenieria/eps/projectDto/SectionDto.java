/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.projectDto;


import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author teodoro
 */
public class SectionDto implements Serializable{

    private Integer id;
    private LocalDate lastModificationDate;
    private Short type;
    private Integer position;
    private List<TitleDto> titles;

    public SectionDto(Section section) {
        this.id = section.getId();
        this.lastModificationDate = section.getLastModificationDate();
        this.type = section.getType();
        this.position = section.getPosition();
        this.titles=new ArrayList<>();
        if (section.getTitle() != null) {
            this.titles.add(new TitleDto(section.getTitle())); 
            this.titles.addAll(section.getTitle().getTitles().stream().map(title -> new TitleDto(title)).collect(Collectors.toList()));
        }
    }

    public SectionDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(LocalDate lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public List<TitleDto> getTitles() {
        return titles;
    }

    public void setTitles(List<TitleDto> titles) {
        this.titles = titles;
    }
    
    

}
