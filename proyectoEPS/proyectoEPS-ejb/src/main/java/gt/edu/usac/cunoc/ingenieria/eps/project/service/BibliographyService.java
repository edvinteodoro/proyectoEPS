
package gt.edu.usac.cunoc.ingenieria.eps.project.service;

import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Bibliography;
import java.time.LocalDate;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class BibliographyService {
    
    private EntityManager entityManager;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public BibliographyService() {
    }
    
    public Bibliography create(Bibliography bibliography) throws MandatoryException{
        if(bibliography.getAuthor() == null){
            throw new MandatoryException("Atributo Autor Obligatorio");
        }
        if(bibliography.getPublicactionYear() == null){
            throw new MandatoryException("Atributo fecha de Publicacón Obligatorio");
        }
        if (bibliography.getTitle() == null){
            throw new MandatoryException("Atributo Titulo Obligatorio");
        }
        if (bibliography.getCity() == null){
            throw new MandatoryException("Atributo Ciudad Obligatorio");
        }
        if (bibliography.getCountry() == null){
            throw new MandatoryException("Atributo País Obligatorio");
        }
        if (bibliography.getEditorial() == null){
            throw new MandatoryException("Atributo Editorial Obligatorio");
        }
        entityManager.persist(bibliography);
        return bibliography;
    }
    
    public Bibliography update(Bibliography bibliography, String newAuthor, LocalDate newPublicationYear, String newTitle, String newCity, String newCountry, String newEditorial) throws MandatoryException{
        if(newAuthor == null){
            throw new MandatoryException("Atributo Autor Obligatorio");
        }
        if(newPublicationYear == null){
            throw new MandatoryException("Atributo fecha de Publicacón Obligatorio");
        }
        if (newTitle == null){
            throw new MandatoryException("Atributo Titulo Obligatorio");
        }
        if (newCity == null){
            throw new MandatoryException("Atributo Ciudad Obligatorio");
        }
        if (newCountry == null){
            throw new MandatoryException("Atributo País Obligatorio");
        }
        if (newEditorial == null){
            throw new MandatoryException("Atributo Editorial Obligatorio");
        }
        bibliography.setAuthor(newAuthor);
        bibliography.setPublicactionYear(newPublicationYear);
        bibliography.setTitle(newTitle);
        bibliography.setCity(newCity);
        bibliography.setCountry(newCountry);
        bibliography.setEditorial(newEditorial);
        return bibliography;
    }
    
    public boolean remove(Bibliography bibliography){
        try {
            entityManager.remove(bibliography);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
