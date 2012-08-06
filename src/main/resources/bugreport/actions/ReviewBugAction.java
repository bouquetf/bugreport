package bugreport.actions;

import bugreport.entities.Bug;
import com.opensymphony.xwork2.ActionSupport;

public class ReviewBugAction extends ActionSupport {
    private String id;

    public String execute() {
        return SUCCESS;
    }

    public void setBugId(String id) {
        this.id = id;
    }

    public Bug getBug() {
        // TODO : retrieve bug information from process
        Bug bug = new Bug();
        bug.setName("Name"+id);
        bug.setDescription("Description "+id);
        bug.setProject("Project "+id);
        bug.setVersion("Version "+id);
        bug.setPriority("Priority "+id);

        return bug;
    }

}
