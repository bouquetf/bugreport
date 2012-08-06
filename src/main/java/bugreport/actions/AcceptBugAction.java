package bugreport.actions;

import bugreport.bonita.BonitaProcessManagement;
import com.opensymphony.xwork2.ActionSupport;

public class AcceptBugAction extends ActionSupport {
    String priority;
    String taskId;

    public String execute() throws Exception {
        new BonitaProcessManagement().acceptBug(taskId, priority);
        return SUCCESS;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
