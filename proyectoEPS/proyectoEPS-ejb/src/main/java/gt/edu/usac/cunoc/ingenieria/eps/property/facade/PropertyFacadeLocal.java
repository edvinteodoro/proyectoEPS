
package gt.edu.usac.cunoc.ingenieria.eps.property.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.property.Property;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PropertyFacadeLocal {
    
    public void loadProperties();

    public void updateProperties(List<Property> properties) throws ValidationException;
}
