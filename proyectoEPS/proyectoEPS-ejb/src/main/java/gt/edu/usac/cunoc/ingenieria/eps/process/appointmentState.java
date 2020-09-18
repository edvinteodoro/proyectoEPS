package gt.edu.usac.cunoc.ingenieria.eps.process;

/**
 *
 * @author angelrg
 */
public enum appointmentState {
    APPROVED, CHANGE, REVIEW, ELECTION;

    public String stateToText() {
        switch (this) {
            case APPROVED:
                return "Aprobado";
            case CHANGE:
                return "Cambiar";
            case REVIEW:
                return "En revisión";
            case ELECTION:
                return "Elección Supervisor";
        }
        return null;
    }
}
