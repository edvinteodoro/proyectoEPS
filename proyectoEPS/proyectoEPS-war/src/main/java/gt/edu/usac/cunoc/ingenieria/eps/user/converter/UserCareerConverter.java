package gt.edu.usac.cunoc.ingenieria.eps.user.converter;

import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(value = "userCareerConverter", managed = true)
public class UserCareerConverter implements Converter {
    @EJB
    private UserFacadeLocal userFacade;
    
    @Override
    public UserCareer getAsObject(FacesContext context, UIComponent component, String careerId) {
        try {
            UserCareer userCareer= userFacade.getUserCareer(new UserCareer(Integer.parseInt(careerId))).get(0);
            return userCareer;              
        } catch (NumberFormatException e) {
            return null; 
        }
}

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object userCareer) {
        return ((UserCareer) userCareer).getId().toString();
    }

}
