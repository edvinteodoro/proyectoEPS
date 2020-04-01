package gt.edu.usac.cunoc.ingenieria.eps.user.converter;

import gt.edu.usac.cunoc.ingenieria.eps.user.Rol;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "rolUserConverter", managed = true)
public class RolUserConverter implements Converter {
    @EJB
    private UserFacadeLocal userFacade;
    
    @Override
    public Rol getAsObject(FacesContext context, UIComponent component, String rolUserId) {
        try {
            Rol rolUser=userFacade.getRolUser(new Rol(Integer.parseInt(rolUserId))).get(0);
            return rolUser;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object rolUser) {
        return ((Rol) rolUser).getId().toString();
    }

}
