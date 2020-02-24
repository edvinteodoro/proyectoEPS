package gt.edu.usac.cunoc.ingenieria.eps.process.facade;

import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import gt.edu.usac.cunoc.ingenieria.eps.process.repository.RequerimentRepository;
import gt.edu.usac.cunoc.ingenieria.eps.process.service.RequerimentService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class RequerimenteFacade implements RequerimentFacadeLocal{

    @EJB 
    RequerimentService requerimentService;
    @EJB 
    RequerimentRepository requerimentRepository;
    
    @Override
    public List<Requeriment> getRequeriment(Requeriment requeriment) {
        return requerimentRepository.getRequeriment(requeriment);
    }

    @Override
    public Requeriment createRequeriment(Requeriment requeriment) {
        return requerimentService.createRequeriment(requeriment);
    }

    @Override
    public Requeriment updaterequeriment(Requeriment requeriment) {
        return requerimentService.updateRequeriment(requeriment);
    }
    
}
