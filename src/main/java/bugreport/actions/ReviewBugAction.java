package bugreport.actions;

import bugreport.bonita.BonitaProcessManagement;
import bugreport.entities.Bug;
import com.opensymphony.xwork2.ActionSupport;
import org.ow2.bonita.facade.exception.ActivityNotFoundException;
import org.ow2.bonita.facade.exception.InstanceNotFoundException;

import javax.security.auth.login.LoginException;

public class ReviewBugAction extends ActionSupport {
    private String taskId;

    public String execute() {
        return SUCCESS;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Bug getBug() throws InstanceNotFoundException, ActivityNotFoundException, LoginException {
        return new BonitaProcessManagement().getBug(taskId);
    }

}
