package bugreport.bonita;

import bugreport.bonita.executors.BugRetriever;
import bugreport.bonita.executors.BugValidator;
import bugreport.bonita.executors.BugsToReview;
import bugreport.bonita.executors.ProcessStarter;
import bugreport.entities.Bug;
import org.ow2.bonita.facade.exception.*;
import org.ow2.bonita.util.SimpleCallbackHandler;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.util.*;

public class BonitaProcessManagement {
    public static final String BONITA_STORE = "BonitaStore";
    public static final String BONITA_STORE_USERNAME = "admin";
    public static final String BONITA_STORE_PASSWORD = "";
    private LoginContext loginContext;

    public void createBug(String project, String version, String summary)
            throws LoginException, ProcessNotFoundException, VariableNotFoundException {
        passUserToBosEngine();
        new ProcessStarter(project, version, summary).start();
        releaseBosEngine();
    }

    public List<Bug> listBugsToReview()
            throws LoginException {
        passUserToBosEngine();
        List<Bug> bugs = new BugsToReview().retrieve();
        releaseBosEngine();
        return bugs;
    }

    public void acceptBug(String taskId, String priority)
            throws LoginException, TaskNotFoundException, IllegalTaskStateException, ActivityNotFoundException, InstanceNotFoundException, VariableNotFoundException {
        passUserToBosEngine();
        new BugValidator(taskId).accept(priority);
        releaseBosEngine();
    }

    public void rejectBug(String taskId)
            throws LoginException, TaskNotFoundException, IllegalTaskStateException, ActivityNotFoundException, InstanceNotFoundException, VariableNotFoundException {
        passUserToBosEngine();
        new BugValidator(taskId).reject();
        releaseBosEngine();
    }

    public Bug getBug(String taskId) throws LoginException, ActivityNotFoundException, InstanceNotFoundException {
        passUserToBosEngine();
        Bug bug = new BugRetriever(taskId).retrieve();
        releaseBosEngine();

        return bug ;
    }

    private void passUserToBosEngine() throws LoginException {
        SimpleCallbackHandler callbackHandler = new SimpleCallbackHandler(BONITA_STORE_USERNAME, BONITA_STORE_PASSWORD);
        loginContext = new LoginContext(BONITA_STORE, callbackHandler);
        loginContext.login();
    }

    private void releaseBosEngine() throws LoginException {
        loginContext.logout();
    }
}
