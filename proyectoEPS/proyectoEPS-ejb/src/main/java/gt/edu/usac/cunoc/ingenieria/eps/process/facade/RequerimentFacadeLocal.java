package gt.edu.usac.cunoc.ingenieria.eps.process.facade;

import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import java.util.List;
import javax.ejb.Local;

@Local
public interface RequerimentFacadeLocal {
    
    public List<Requeriment> getRequeriment(Requeriment requeriment);
 
    public Requeriment createRequeriment(Requeriment requeriment);
    
    public Requeriment updaterequeriment(Requeriment requeriment);
}
