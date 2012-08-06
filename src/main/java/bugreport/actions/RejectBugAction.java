package bugreport.actions;

import bugreport.bonita.BonitaProcessManagement;
import com.opensymphony.xwork2.ActionSupport;

public class RejectBugAction extends ActionSupport {
    private String taskId;

    public String execute() throws Exception {
        new BonitaProcessManagement().rejectBug(taskId);
        return SUCCESS;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
