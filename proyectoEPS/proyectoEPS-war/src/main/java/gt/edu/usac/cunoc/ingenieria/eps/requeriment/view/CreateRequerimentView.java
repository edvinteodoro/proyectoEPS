package gt.edu.usac.cunoc.ingenieria.eps.requeriment.view;

import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.RequerimentFacadeLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class CreateRequerimentView implements Serializable {

    @EJB
    RequerimentFacadeLocal requerimentFacade;
    
    Requeriment requeriment;
    
    public Requeriment getRequeriment() {
        if (requeriment == null) {
            requeriment = new Requeriment();
        }
        return requeriment;
    }
    
    public void createRequeriment(){
    
    }
}
