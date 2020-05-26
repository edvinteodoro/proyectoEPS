package gt.edu.usac.cunoc.ingenieria.eps.project.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import static gt.edu.usac.cunoc.ingenieria.eps.configuration.Constants.PERSISTENCE_UNIT_NAME;
import gt.edu.usac.cunoc.ingenieria.eps.configuration.repository.PropertyRepository;
import gt.edu.usac.cunoc.ingenieria.eps.exception.LimitException;
import gt.edu.usac.cunoc.ingenieria.eps.exception.MandatoryException;
import gt.edu.usac.cunoc.ingenieria.eps.project.Project;
import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.project.Bibliography;
import gt.edu.usac.cunoc.ingenieria.eps.project.DecimalCoordinate;
import gt.edu.usac.cunoc.ingenieria.eps.project.Objectives;
import gt.edu.usac.cunoc.ingenieria.eps.project.Section;
import gt.edu.usac.cunoc.ingenieria.eps.user.UserCareer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.Month;
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
    private Style bodyStyleItalic;
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

    public InputStream createPDF(Project project, UserCareer userCareer) throws IOException {
        this.setFontDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //PdfDocument pdf = new PdfDocument(new PdfWriter(out));

        String destino = "/home/crystian/Escritorio/" + project.getTitle() + ".pdf";
        PdfDocument pdf = new PdfDocument(new PdfWriter(destino));
        
        Document document = new Document(pdf);
        
        //Generar portada del Documento
        document = addCoverPage(document, project, userCareer);

        //Generar Introducción
        document = addIntroduction(document, project);

        //Generar Justificación
        document = addJustification(document, project);

        //Generar los objetivos
        document = addObjectives(document, project);

        //Generar Secciones
        document = addSections(document, project);

        //Generar Coordenadas
        document = addCoordinates(document, project);

        //Agregar calendario
        document = addSchedule(document, project);

        //Agregar Plan de Inversión
        document = addInvestmentPlan(document, project);

        //Agregar Bibliografia
        document = addBibliography(document, project);

        //Agregar Anexos
        document = addAnnexes(document, project);

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void setFontDocument() throws IOException {
        this.titleStyle = new Style();
        PdfFont fontTimesRoman = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        this.titleStyle.setFont(fontTimesRoman).setFontSize(14);

        this.bodyStyle = new Style();
        this.bodyStyle.setFont(fontTimesRoman).setFontSize(12);

        this.bodyStyleItalic = new Style();
        PdfFont fontTimesItalic = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        this.bodyStyleItalic.setFont(fontTimesItalic).setFontSize(12);
    }

    private Document addCoverPage(Document document, Project project, UserCareer userCareer) throws MalformedURLException {
        document.add(new Paragraph("Universidad de San Carlos de Guatemala").addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Centro Universitario de Occidente").addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph(userCareer.getCAREERcodigo().getName()).addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));
        //ImageData logoCunoc = ImageDataFactory.create("usac.png");
        //Image logoCunocImage = new Image(logoCunoc);
        //document.add(logoCunocImage);
        document.add(new Paragraph().add(new Text("\n")));
        document.add(new Paragraph(project.getTitle()).addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph().add(new Text("\n")));
        document.add(new Paragraph("Por").addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph(userCareer.getUSERuserId().getFirstName() + " " + userCareer.getUSERuserId().getLastName()).addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));
        //document.add(new Paragraph(userCareer.getUSERuserId().getCarnet()).addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph().add(new Text("\n")));
        document.add(new Paragraph().add(new Text("\n")));
        document.add(new Paragraph("Quetzaltenango Quetzaltenango," + getDateCover()).addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));
        return document;
    }

    private Document addObjectives(Document document, Project project) {
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        if (!project.getObjectives().isEmpty()) {
            document.add(new Paragraph("Objetivos").addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));
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

    private Document addIntroduction(Document document, Project project) {
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        document.add(new Paragraph("Introducción").addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));
        String textIntroduction = "";
        for (int i = 0; i < project.getSections().size(); i++) {
            Section temp = project.getSections().get(i);
            if (Objects.equals(Section.INTRODUCTION, temp.getType())) {
                textIntroduction = project.getSections().get(i).getTitle().getTexto().getText();
            }
        }
        java.util.List<IElement> list = HtmlConverter.convertToElements(textIntroduction);
        for (IElement element : list) {
            document.add((IBlockElement) element);
        }
        return document;
    }

    private Document addJustification(Document document, Project project) {
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        document.add(new Paragraph("Justificación").addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));
        String textJustification = "";
        for (int i = 0; i < project.getSections().size(); i++) {
            Section temp = project.getSections().get(i);
            if (Objects.equals(temp.getType(), Section.JUSTIFICATION)) {
                textJustification = project.getSections().get(i).getTitle().getTexto().getText();
            }
        }
        java.util.List<IElement> list = HtmlConverter.convertToElements(textJustification);
        for (IElement element : list) {
            document.add((IBlockElement) element);
        }
        return document;
    }

    private Document addSections(Document document, Project project) {
        for (int i = 0; i < project.getSections().size(); i++) {
            Section temp = project.getSections().get(i);
            if (!Objects.equals(temp.getType(), Section.INTRODUCTION) && !Objects.equals(temp.getType(), Section.JUSTIFICATION)) {
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                document.add(new Paragraph(temp.getTitle().getName()).addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));
                String text = "";
                text = project.getSections().get(i).getTitle().getTexto().getText();
                java.util.List<IElement> list = HtmlConverter.convertToElements(text);
                for (IElement element : list) {
                    document.add((IBlockElement) element);
                }
            }
        }
        return document;
    }

    private Document addCoordinates(Document document, Project project) {
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        document.add(new Paragraph("Coordenadas Decimales").addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));
        for (int i = 0; i < project.getDecimalCoordinates().size(); i++) {
            DecimalCoordinate temp = project.getDecimalCoordinates().get(i);
            document.add(new Paragraph("Latitud: " + temp.getLatitude() + "   Longitud: " + temp.getLongitude()));
        }
        return document;
    }

    private Document addSchedule(Document document, Project project) throws IOException {
        try (PdfDocument schedulePDF = new PdfDocument(new PdfReader(new ByteArrayInputStream(project.getSchedule())))) {
            schedulePDF.copyPagesTo(1, schedulePDF.getNumberOfPages(), document.getPdfDocument());
            schedulePDF.close();
        }
        return document;
    }

    private Document addInvestmentPlan(Document document, Project project) throws IOException {
        try (PdfDocument investmentPlanPDF = new PdfDocument(new PdfReader(new ByteArrayInputStream(project.getInvestmentPlan())))) {
            investmentPlanPDF.copyPagesTo(1, investmentPlanPDF.getNumberOfPages(), document.getPdfDocument());
            investmentPlanPDF.close();
        }
        return document;
    }

    private Document addBibliography(Document document, Project project) {
        document.getPdfDocument().addNewPage();
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        document.add(new Paragraph("Bibliografía").addStyle(titleStyle).setTextAlignment(TextAlignment.CENTER));
        Text first;
        Text second;
        Text third;
        for (int i = 0; i < project.getBibliographies().size(); i++) {
            Bibliography temp = project.getBibliographies().get(i);
            first = new Text(temp.getAuthor() + ",(" + temp.getPublicactionYear() + "),").addStyle(bodyStyle);
            second = new Text(temp.getTitle()).addStyle(bodyStyleItalic);
            third = new Text("," + temp.getCity() + "," + temp.getCountry() + "," + temp.getEditorial()).addStyle(bodyStyle);
            document.add(new Paragraph().add(first).add(second).add(third));
        }
        return document;
    }

    private Document addAnnexes(Document document, Project project) throws IOException {
        if (project.getAnnexed().length != 0) {
            try (PdfDocument investmentPlanPDF = new PdfDocument(new PdfReader(new ByteArrayInputStream(project.getAnnexed())))) {
                investmentPlanPDF.copyPagesTo(1, investmentPlanPDF.getNumberOfPages(), document.getPdfDocument());
                investmentPlanPDF.close();
            }
        }
        return document;
    }

    private String getDateCover() {
        String date = "";
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
        switch (currentMonth) {
            case JANUARY:
                date = "Enero";
                break;
            case FEBRUARY:
                date = "Febrero";
                break;
            case MARCH:
                date = "Marzo";
                break;
            case APRIL:
                date = "Abril";
                break;
            case MAY:
                date = "Mayo";
                break;
            case JUNE:
                date = "Junio";
                break;
            case JULY:
                date = "Julio";
                break;
            case AUGUST:
                date = "Agosto";
                break;
            case SEPTEMBER:
                date = "Septiembre";
                break;
            case OCTOBER:
                date = "Octubre";
                break;
            case NOVEMBER:
                date = "Noviembre";
                break;
            case DECEMBER:
                date = "Diciembre";
                break;
        }
        return date + " de " + currentDate.getYear();
    }
}
