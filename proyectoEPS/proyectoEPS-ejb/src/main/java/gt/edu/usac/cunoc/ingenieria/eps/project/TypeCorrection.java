package gt.edu.usac.cunoc.ingenieria.eps.project;

public enum TypeCorrection {
    BIBLIOGRAPHY, OBJETIVES, SPECIFIC_OBJETIVES, CALENDAR, PLAN, ANEXO, TITLE, COORDINATE, OTHER, REJECTED, ACCEPTED;

    public String toText() {
        switch (this) {
            case BIBLIOGRAPHY:
                return "Bibliografia";
            case OBJETIVES:
                return "Objetivos Generales";
            case SPECIFIC_OBJETIVES:
                return "Objetivos Especificos";
            case CALENDAR:
                return "Calendarización";
            case PLAN:
                return "Plan de Inversion";
            case ANEXO:
                return "Anexos";
            case TITLE:
                return "Titulo";
            case COORDINATE:
                return "Coordenadas";
            case OTHER:
                return "Otros";
            case ACCEPTED:
                return "Aceptado";
            case REJECTED:
                return "Rechazado";
            default:
                return null;

        }
    }
}
