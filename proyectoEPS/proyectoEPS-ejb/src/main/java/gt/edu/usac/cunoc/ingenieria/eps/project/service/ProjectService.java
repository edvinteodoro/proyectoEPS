package gt.edu.usac.cunoc.ingenieria.eps.project.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.List;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.configuration.repository.PropertyRepository;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import java.io.IOException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ProjectService {

    private EntityManager entityManager;

    @PersistenceContext(name = PERSISTENCE_UNIT_NAME)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ProjectService() {
    }

    public Project update(Project project) throws MandatoryException, LimitException {
        verifyProject(project);
        project.setState(Project.ACTIVE);
        project.setLimitReceptionDate(PropertyRepository.GENERAL_LIMIT_RECEPTION_DATE.getValueDate());
        entityManager.merge(project);
        return project;
    }

    public Project create(Project project, Process process) throws MandatoryException, LimitException {
        verifyProject(project);
        project.setState(Project.ACTIVE);
        project.setLimitReceptionDate(PropertyRepository.GENERAL_LIMIT_RECEPTION_DATE.getValueDate());
        project.setpROCESSid(process);
        process.setProject(project);
        entityManager.merge(process);
        return project;
    }

    private void verifyProject(Project project) throws MandatoryException, LimitException {
        if (project.getTitle() == null) {
            throw new MandatoryException("Atributo Titulo Obligatorio");
        }
        if (project.getTitle().length() > PropertyRepository.CHARACTER_LIMIT_TITLE.getValueInt()) {
            throw new LimitException("Titulo fuera de limites, Número de Caracteres Maximo " + PropertyRepository.CHARACTER_LIMIT_TITLE.getValueInt());
        }
        if (project.getSchedule() == null) {
            throw new MandatoryException("Archivo Calendario Obligatorio");
        }
        if (project.getInvestmentPlan() == null) {
            throw new MandatoryException("Archivo Plan de Inversiones Obligatorio");
        }
    }
    
    public void createPDF(Project project) throws IOException{
        String destino = "/home/crystian/Escritorio/" + project.getTitle() + ".pdf";
        PdfDocument pdf = new PdfDocument(new PdfWriter(destino));
        Document document = new Document(pdf);
        document.add(new Paragraph(project.getTitle()));
        document.add(new Paragraph("Objetivos"));
        document.add(new Paragraph("Objetivos Generales"));
        List listGeneralObjetives = new List().setSymbolIndent(12).setListSymbol("\u2022");
        for (int i = 0; i < project.getObjectives().size(); i++) {
            if (project.getObjectives().get(i).getState() == Objectives.GENERAL_OBJETICVE){
                listGeneralObjetives.add(project.getObjectives().get(i).getText());
            }
        }
        document.add(listGeneralObjetives);
        document.add(new Paragraph("Objetivos Especificos"));
        List listSpecificObjetives = new List().setSymbolIndent(12).setListSymbol("\u2022");
        for (int i = 0; i < project.getObjectives().size(); i++) {
            if (project.getObjectives().get(i).getState() == Objectives.SPECIFIC_OBJECTIVE){
                listSpecificObjetives.add(project.getObjectives().get(i).getText());
            }
        }
        document.add(listSpecificObjetives);
        document.close();
    }
}
