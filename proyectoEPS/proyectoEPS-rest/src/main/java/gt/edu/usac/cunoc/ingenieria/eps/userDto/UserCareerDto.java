/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.userDto;

import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import java.io.Serializable;

/**
 *
 * @author crystian
 */
public class UserCareerDto implements Serializable{
    
    private Integer code;
    private String name;
    
    public UserCareerDto(){
    }

    public UserCareerDto(UserCareer userCareer) {
        this.code = userCareer.getCAREERcodigo().getCodigo();
        this.name = userCareer.getCAREERcodigo().getName();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
