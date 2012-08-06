package bugreport.actions;

import bugreport.bonita.BonitaProcessManagement;
import com.opensymphony.xwork2.ActionSupport;

public class SaveBugAction extends ActionSupport {
    private String project;
    private String version;
    private String summary;

    public String execute() throws Exception {
        new BonitaProcessManagement().createBug(project, version, summary);
        return SUCCESS;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


}
