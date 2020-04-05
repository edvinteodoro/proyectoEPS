package gt.edu.usac.cunoc.ingenieria.eps.user.converter;

import gt.edu.usac.cunoc.ingenieria.eps.user.Career;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(value = "careerConverter", managed = true)
public class CareerConverter implements Converter {
    @EJB
    private UserFacadeLocal userFacade;
    
    @Override
    public Career getAsObject(FacesContext context, UIComponent component, String careerId) {
        try {
            Career career = userFacade.getCareer(new Career(Integer.parseInt(careerId),null)).get(0);
            return career;              
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
}

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object career) {
        return ((Career) career).getCodigo().toString();
    }

}
