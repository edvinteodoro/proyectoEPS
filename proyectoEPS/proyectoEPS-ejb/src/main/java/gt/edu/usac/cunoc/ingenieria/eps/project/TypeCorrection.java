package gt.edu.usac.cunoc.ingenieria.eps.project;

public enum TypeCorrection {
    TITLE, GENERAL_OBJETIVES, SPECIFIC_OBJETIVES, SECTION, COORDINATE,SCHEDULE, INVESTMENT_PLAN, BIBLIOGRAPHY , ANNEXED, REJECTED;

    public String toText() {
        switch (this) {
            case TITLE:
                return "Título";
            case GENERAL_OBJETIVES:
                return "Objetivos Generales";
            case SPECIFIC_OBJETIVES:
                return "Objetivos Especificos";
            case SECTION:
                return "Sección";
            case COORDINATE:
                return "Coordenada Decimal";
            case SCHEDULE:
                return "Calendario";
            case INVESTMENT_PLAN:
                return "Plan de Inversión";
            case BIBLIOGRAPHY:
                return "Bibliografáa";
            case ANNEXED:
                return "Anexo";
            case REJECTED:
                return "Rechazado";
            default:
                return null;
        }
    }
}
