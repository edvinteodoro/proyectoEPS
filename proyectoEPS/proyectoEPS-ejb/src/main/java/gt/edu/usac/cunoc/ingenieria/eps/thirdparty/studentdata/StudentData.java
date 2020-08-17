package gt.edu.usac.cunoc.ingenieria.eps.thirdparty.studentdata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * proyectoEPS-ejb
 * @author jose - 10.08.2020 
 * @Title: StudentData
 * @Description: description
 *
 * Changes History
 */
@XmlRootElement(name="usuario")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentData {

    private String carne;
    private String cui;
    private String nombres;
    private String apellidos;

    public StudentData() {
    }

    public StudentData(String carne, String cui, String nombres, String apellidos) {
        this.carne = carne;
        this.cui = cui;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public String getCarne() {
        return carne;
    }

    public void setCarne(String carne) {
        this.carne = carne;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}