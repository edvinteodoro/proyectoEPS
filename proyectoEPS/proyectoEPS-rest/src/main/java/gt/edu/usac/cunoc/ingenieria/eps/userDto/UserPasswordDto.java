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
public class UserPasswordDto implements Serializable {

    private String passWordUser;

    public UserPasswordDto() {
    }

    public String getPassWordUser() {
        return passWordUser;
    }

    public void setPassWordUser(String passWordUser) {
        this.passWordUser = passWordUser;
    }

}
