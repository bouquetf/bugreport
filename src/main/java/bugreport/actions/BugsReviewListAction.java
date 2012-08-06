package bugreport.actions;

import bugreport.bonita.BonitaProcessManagement;
import bugreport.entities.Bug;
import com.opensymphony.xwork2.ActionSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BugsReviewListAction extends ActionSupport {


    private List<Bug> bugs;

    public String execute() throws Exception {
        bugs = new BonitaProcessManagement().listBugsToReview();
        return SUCCESS;
    }

    public List<Bug> getBugs() {
        return bugs;
    }
}
