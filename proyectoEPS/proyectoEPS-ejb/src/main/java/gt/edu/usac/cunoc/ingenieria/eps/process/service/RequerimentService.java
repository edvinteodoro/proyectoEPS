package gt.edu.usac.cunoc.ingenieria.eps.process.service;

import static gt.edu.usac.cunoc.ingenieria.eps.config.Constans.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class RequerimentService {

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public Requeriment createRequeriment(Requeriment requeriment) {
        entityManager.persist(requeriment);
        return requeriment;
    }
    public Requeriment updateRequeriment(Requeriment requeriment) {
        Requeriment updateRequeriment = entityManager.find(Requeriment.class, requeriment.getId());
        if(requeriment.getAEIOsettlement()!=null){
            updateRequeriment.setAEIOsettlement(requeriment.getAEIOsettlement());
        }
        if(requeriment.getEPSpreproject()!=null){
            updateRequeriment.setEPSpreproject(requeriment.getEPSpreproject());
        }
        if(requeriment.getInscriptionConstancy()!=null){
            updateRequeriment.setInscriptionConstancy(requeriment.getInscriptionConstancy());
        }
        if(requeriment.getPREPROJECTid()!=null){
            updateRequeriment.setPREPROJECTid(requeriment.getPREPROJECTid());
        }
        if(requeriment.getPensumClosure()!=null){
            updateRequeriment.setPensumClosure(requeriment.getPensumClosure());
        }
        if(requeriment.getPropedeuticConstancy()!=null){
            updateRequeriment.setPropedeuticConstancy(requeriment.getPropedeuticConstancy());
        }
        if(requeriment.getWrittenRequest()!=null){
            updateRequeriment.setWrittenRequest(requeriment.getWrittenRequest());
        }
        
        return requeriment;
    }
}
