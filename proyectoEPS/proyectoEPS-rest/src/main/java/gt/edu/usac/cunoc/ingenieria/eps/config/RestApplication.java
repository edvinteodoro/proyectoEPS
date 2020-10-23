/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.config;

import static gt.edu.usac.cunoc.ingenieria.eps.security.Constants.ADMIN;
import static gt.edu.usac.cunoc.ingenieria.eps.security.Constants.USER;
import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


/**
 *
 * @author teodoro
 */
@DeclareRoles({ADMIN, USER})
@ApplicationPath("api/v1")
public class RestApplication extends Application{
    
}
