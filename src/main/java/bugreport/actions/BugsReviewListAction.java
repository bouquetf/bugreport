package bugreport.actions;

import bugreport.entities.Bug;
import com.opensymphony.xwork2.ActionSupport;

import java.util.ArrayList;
import java.util.Date;

public class BugsReviewListAction extends ActionSupport {


    private ArrayList<Bug> bugs;

    public String execute() throws Exception {
        // TODO : List all review tasks in READY
        bugs = new ArrayList<Bug>();

        for (int i = 0; i < 10; i++)
            bugs.add(createBug("Bug"+i, ""+i, "", new Date()));


        return SUCCESS;
    }

    public ArrayList<Bug> getBugs() {
        return bugs;
    }

    private Bug createBug(String bugname, String id, String assigned_to, Date creationDate) {
        Bug bug = new Bug();
        bug.setExecutionId(id);
        bug.setCreationDate(creationDate);
        bug.setName(bugname);
        bug.setAssignedTo(assigned_to);
        return bug;
    }


}
