package gt.edu.usac.cunoc.ingenieria.eps.journalLogDto;

import java.time.LocalDate;

/**
 *
 * @author teodoro
 */
public class CommentaryDto {
    private Integer id;
    private String text;
    private LocalDate date;

    public CommentaryDto(Integer id, String text, LocalDate date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public CommentaryDto() {
        
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    
}
