package gt.edu.usac.cunoc.ingenieria.eps.process;

/**
 *
 * @author angelrg
 */
public enum appointmentState {
    APPROVED, CHANGE, REVIEW, NEW;

    public String stateToText() {
        switch (this) {
            case APPROVED:
                return "Aprovado";
            case CHANGE:
                return "Cambiar";
            case REVIEW:
                return "En revisión";
            case NEW:
                return "Nuevo en Revisión";
        }
        return null;
    }
}
