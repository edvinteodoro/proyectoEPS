package gt.edu.usac.cunoc.ingenieria.eps.process.view;

import gt.edu.usac.cunoc.ingenieria.eps.process.Process;
import gt.edu.usac.cunoc.ingenieria.eps.user.User;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author angelrg
 */
@Named
@ViewScoped
public class studentAdvisorReviewerView implements Serializable {

    private List<Process> processAvailable;
    private List<User> advisors;
    private List<User> reviewers;

    private User advisorSelected;
    private User reviewerSelected;

}
