
package gt.edu.usac.cunoc.ingenieria.eps.property.facade;

import gt.edu.usac.cunoc.ingenieria.eps.exception.ValidationException;
import gt.edu.usac.cunoc.ingenieria.eps.property.Property;
import gt.edu.usac.cunoc.ingenieria.eps.property.repository.PropertyRepository;
import gt.edu.usac.cunoc.ingenieria.eps.property.service.PropertyService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class PropertyFacade implements PropertyFacadeLocal{

    @EJB
    private PropertyRepository propertyRepository;
    
    @EJB
    private PropertyService propertyService;
    
    @Override
    public void loadProperties() {
        propertyRepository.loadProperties();
    }

    @Override
    public void updateProperties(List<Property> properties) throws ValidationException{
        propertyService.updateProperties(properties);
    }
    
    
}
