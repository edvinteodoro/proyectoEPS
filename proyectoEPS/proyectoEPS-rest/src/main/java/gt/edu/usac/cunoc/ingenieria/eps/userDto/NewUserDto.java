/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.userDto;

import java.io.Serializable;

/**
 *
 * @author crystian
 */
public class NewUserDto extends CompleteUserDto implements Serializable{
    
    private String password;

    public NewUserDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
