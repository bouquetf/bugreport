package bugreport.bonita;

import org.ow2.bonita.facade.RuntimeAPI;
import org.ow2.bonita.facade.exception.ProcessNotFoundException;
import org.ow2.bonita.facade.exception.VariableNotFoundException;
import org.ow2.bonita.facade.uuid.ProcessDefinitionUUID;
import org.ow2.bonita.util.AccessorUtil;
import org.ow2.bonita.util.SimpleCallbackHandler;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

public class BonitaProcessManagement {

    private LoginContext loginContext;
    private RuntimeAPI runtimeAPI;

    public BonitaProcessManagement() {
        runtimeAPI = AccessorUtil.getRuntimeAPI();
    }

    public void createBug(String project, String version, String summary) throws LoginException, ProcessNotFoundException, VariableNotFoundException {
        login();
        startProcessWithVariables(project, version, summary);
        logout();
    }

    private void logout() throws LoginException {
        loginContext.logout();
    }

    private void startProcessWithVariables(String project, String version, String summary) throws ProcessNotFoundException, VariableNotFoundException {
        ProcessDefinitionUUID processDefinitionUUID = new ProcessDefinitionUUID("Bug_report", "1.0");
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

    private void login() throws LoginException {
        SimpleCallbackHandler callbackHandler = new SimpleCallbackHandler("admin", "");
        loginContext = new LoginContext("BonitaStore", callbackHandler);
        loginContext.login();
    }
}
