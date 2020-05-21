package gt.edu.usac.cunoc.ingenieria.eps.project.service;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.property.TextAlignment;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.configuration.repository.PropertyRepository;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ProjectService {

    private EntityManager entityManager;

    private Style titleStyle;
    private Style bodyStyle;
    private String bullet = "\u2022"; //Simbolo de Punto para las pestañas

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

    public InputStream createPDF(Project project) throws IOException {
        this.setFontDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //PdfDocument pdf = new PdfDocument(new PdfWriter(out));

        String destino = "/home/crystian/Escritorio/" + project.getTitle() + ".pdf";
        PdfDocument pdf = new PdfDocument(new PdfWriter(destino));

        Document document = new Document(pdf);

        //Generar el titulo del Documento
        document.add(new Paragraph(project.getTitle()).addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));

        //Generar Introducción
        document = addIntroduction(document, project);
        
        //Generar Justificación
        document = addJustification(document, project);
        
        //Generar los objetivos
        document = addObjectives(document, project);
        
        //Generar Secciones
        
        //Generar Coordenadas
        
        //Agregar calendario
        
        //Agregar Plan de Inversión
        
        //Agregar Bibliografia
        
        //Agregar Anexos

        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void setFontDocument() throws IOException {
        this.titleStyle = new Style();
        PdfFont fontTimesRoman = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        this.titleStyle.setFont(fontTimesRoman).setFontSize(14);

        this.bodyStyle = new Style();
        this.bodyStyle.setFont(fontTimesRoman).setFontSize(12);
    }

    private Document addObjectives(Document document, Project project) {
        if (!project.getObjectives().isEmpty()) {
            document.add(new Paragraph("Objetivos").addStyle(bodyStyle));
            List listGeneralObjetives = new List().setSymbolIndent(12).setListSymbol(bullet);
            for (int i = 0; i < project.getObjectives().size(); i++) {
                if (Objects.equals(project.getObjectives().get(i).getState(), Objectives.GENERAL_OBJETICVE)) {
                    listGeneralObjetives.add(project.getObjectives().get(i).getText()).addStyle(bodyStyle);
                }
            }
            if (!listGeneralObjetives.isEmpty()) {
                document.add(new Paragraph("Objetivos Generales").addStyle(bodyStyle));
                document.add(listGeneralObjetives);
            }
            List listSpecificObjetives = new List().setSymbolIndent(12).setListSymbol(bullet);
            for (int i = 0; i < project.getObjectives().size(); i++) {
                if (Objects.equals(project.getObjectives().get(i).getState(), Objectives.SPECIFIC_OBJECTIVE)) {
                    listSpecificObjetives.add(project.getObjectives().get(i).getText()).addStyle(bodyStyle);
                }
            }
            if (!listSpecificObjetives.isEmpty()) {
                document.add(new Paragraph("Objetivos Especificos"));
                document.add(listSpecificObjetives);
            }
        }
        return document;
    }
    
    private Document addIntroduction(Document document, Project project){
        document.add(new Paragraph("Introducción").addStyle(bodyStyle));
        String textIntroduction = "";
        for (int i = 0; i < project.getSections().size(); i++) {
            if (project.getSections().get(i).getTitle().getName().equals(Section.INTRODUCTION_TEXT)){
            textIntroduction = project.getSections().get(i).getTitle().getTexto().getText();
            }
        }
        document.add(new Paragraph(textIntroduction));
        return document;
    }
    
    private Document addJustification(Document document, Project project){
        document.add(new Paragraph("Justificación").addStyle(bodyStyle));
        String textIntroduction = "";
        for (int i = 0; i < project.getSections().size(); i++) {
            if (project.getSections().get(i).getTitle().getName().equals(Section.JUSTIFICATION_TEXT)){
            textIntroduction = project.getSections().get(i).getTitle().getTexto().getText();
            }
        }
        document.add(new Paragraph(textIntroduction));
        return document;
    }
}
