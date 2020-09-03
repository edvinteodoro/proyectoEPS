/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.journalLogDto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author teodoro
 */
public class JournalLogDto implements Serializable{
    private Integer id;
    private String activity;
    private LocalDate dateTime;
    private String description;

    public JournalLogDto(Integer id, String activity, LocalDate dateTime, String description) {
        this.id = id;
        this.activity = activity;
        this.dateTime = dateTime;
        this.description = description;
    }

    public JournalLogDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
