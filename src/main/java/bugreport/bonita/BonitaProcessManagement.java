package bugreport.bonita;

import bugreport.entities.Bug;
import org.ow2.bonita.facade.QueryRuntimeAPI;
import org.ow2.bonita.facade.RuntimeAPI;
import org.ow2.bonita.facade.exception.*;
import org.ow2.bonita.facade.runtime.ActivityState;
import org.ow2.bonita.facade.uuid.ActivityInstanceUUID;
import org.ow2.bonita.facade.uuid.ProcessDefinitionUUID;
import org.ow2.bonita.facade.uuid.ProcessInstanceUUID;
import org.ow2.bonita.light.LightActivityInstance;
import org.ow2.bonita.light.LightProcessInstance;
import org.ow2.bonita.light.LightTaskInstance;
import org.ow2.bonita.util.AccessorUtil;
import org.ow2.bonita.util.SimpleCallbackHandler;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.util.*;

public class BonitaProcessManagement {
    public static final String PROCESS_NAME = "Bug_report";
    public static final String PROCESS_VERSION = "1.0";
    private LoginContext loginContext;
    private RuntimeAPI runtimeAPI;
    private QueryRuntimeAPI queryRuntimeAPI;

    public BonitaProcessManagement() {
        runtimeAPI = AccessorUtil.getRuntimeAPI();
        queryRuntimeAPI = AccessorUtil.getQueryRuntimeAPI();
    }

    public void createBug(String project, String version, String summary)
            throws LoginException, ProcessNotFoundException, VariableNotFoundException {
        login();
        startProcessWithVariables(project, version, summary);
        logout();
    }

    public List<Bug> listBugsToReview()
            throws LoginException {
        login();
        List<Bug> bugs = retrieveBugsToReview();
        logout();
        return bugs;
    }

    public void acceptBug(String taskId, String priority)
            throws LoginException, TaskNotFoundException, IllegalTaskStateException, ActivityNotFoundException, InstanceNotFoundException, VariableNotFoundException {
        login();
        new BugValidator(taskId).accept(priority);
        logout();
    }

    public void rejectBug(String taskId)
            throws LoginException, TaskNotFoundException, IllegalTaskStateException, ActivityNotFoundException, InstanceNotFoundException, VariableNotFoundException {
        login();
        new BugValidator(taskId).reject();
        logout();
    }

    private List<Bug> retrieveBugsToReview() {
        List<Bug> bugs = new ArrayList<Bug>();

        Collection<LightTaskInstance> lightTaskList = queryRuntimeAPI.getLightTaskList(ActivityState.READY);
        for (LightTaskInstance lightTaskInstance : lightTaskList)
            if (lightTaskInstance.getActivityName().equals("Review_bug"))
                bugs.add(createBugForBugsToReviewList(lightTaskInstance));

        return bugs;
    }

    private Bug createBugForBugsToReviewList(LightTaskInstance lightTaskInstance) {
        Bug bug = new Bug();
        bug.setCreationDate(lightTaskInstance.getCreatedDate());
        bug.setName(lightTaskInstance.getActivityLabel());
        bug.setExecutionId(lightTaskInstance.getProcessInstanceUUID().toString());
        bug.setTaskId(lightTaskInstance.getUUID().toString());
        return bug;
    }

    private void logout() throws LoginException {
        loginContext.logout();
    }

    private void startProcessWithVariables(String project, String version, String summary)
            throws ProcessNotFoundException, VariableNotFoundException {
        new ProcessStarter(project, version, summary).start();
    }

    private void login() throws LoginException {
        SimpleCallbackHandler callbackHandler = new SimpleCallbackHandler("admin", "");
        loginContext = new LoginContext("BonitaStore", callbackHandler);
        loginContext.login();
    }

    public Bug getBug(String taskId) throws LoginException, ActivityNotFoundException, InstanceNotFoundException {
        login();
        Bug bug = new BugRetriever(taskId).retrieve();
        logout();

        return bug ;
    }

    private class ProcessStarter {
        private String project;
        private String version;
        private String summary;

        public ProcessStarter(String project, String version, String summary) {
            this.project = project;
            this.version = version;
            this.summary = summary;
        }

        public void start() throws ProcessNotFoundException, VariableNotFoundException {
            ProcessDefinitionUUID processDefinitionUUID = new ProcessDefinitionUUID(PROCESS_NAME, PROCESS_VERSION);
            Map<String, Object> parameters = initializeProcessParameters(project, version, summary);
            runtimeAPI.instantiateProcess(processDefinitionUUID, parameters);
        }

        private Map<String, Object> initializeProcessParameters(String project, String version, String summary) {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("project", project);
            parameters.put("version", version);
            parameters.put("summary", summary);
            return parameters;
        }
    }

    private class BugValidator {
        private final ActivityInstanceUUID activityInstanceUUID;
        private final LightProcessInstance lightProcessInstance;
        private ProcessInstanceUUID processInstanceUUID;

        public BugValidator(String taskId) throws InstanceNotFoundException, TaskNotFoundException {
            activityInstanceUUID = new ActivityInstanceUUID(taskId);
            processInstanceUUID = queryRuntimeAPI.getLightTaskInstance(activityInstanceUUID).getProcessInstanceUUID();
            lightProcessInstance = queryRuntimeAPI.getLightProcessInstance(processInstanceUUID);
        }

        public void accept(String priority)
                throws TaskNotFoundException, IllegalTaskStateException, VariableNotFoundException, InstanceNotFoundException {
            runtimeAPI.startTask(activityInstanceUUID, true);
            runtimeAPI.setProcessInstanceVariable(lightProcessInstance.getProcessInstanceUUID(), "priority", priority);
            runtimeAPI.setProcessInstanceVariable(lightProcessInstance.getProcessInstanceUUID(), "result", "Accepted");
            runtimeAPI.finishTask(activityInstanceUUID, true);
        }

        public void reject()
                throws TaskNotFoundException, IllegalTaskStateException, VariableNotFoundException, InstanceNotFoundException {
            runtimeAPI.startTask(activityInstanceUUID, true);
            runtimeAPI.setProcessInstanceVariable(lightProcessInstance.getProcessInstanceUUID(), "result", "Rejected");
            runtimeAPI.finishTask(activityInstanceUUID, true);
        }

    }

    private class BugRetriever {
        private String taskId;
        private final ActivityInstanceUUID activityInstanceUUID;
        private final LightActivityInstance lightActivityInstance;
        private final ProcessInstanceUUID processInstanceUUID;

        public BugRetriever(String taskId) throws ActivityNotFoundException {
            this.taskId = taskId;
            activityInstanceUUID = new ActivityInstanceUUID(taskId);
            lightActivityInstance = queryRuntimeAPI.getLightActivityInstance(activityInstanceUUID);
            processInstanceUUID = lightActivityInstance.getProcessInstanceUUID();
        }

        public Bug retrieve() throws ActivityNotFoundException, InstanceNotFoundException {
            return initializeBug();
        }

        private Bug initializeBug() throws InstanceNotFoundException {
            Bug bug = new Bug();
            Map<String, Object> variables = getCaseVariables();
            bug.setTaskId(taskId);
            bug.setProject((String) variables.get("project"));
            bug.setDescription((String) variables.get("summary"));
            bug.setVersion((String) variables.get("version"));
            return bug;
        }

        private Map<String, Object> getCaseVariables() throws InstanceNotFoundException {
            return queryRuntimeAPI.getProcessInstanceVariables(processInstanceUUID);
        }
    }
}
