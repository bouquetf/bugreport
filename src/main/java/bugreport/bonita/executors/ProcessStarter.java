package bugreport.bonita.executors;

import org.ow2.bonita.facade.RuntimeAPI;
import org.ow2.bonita.facade.exception.ProcessNotFoundException;
import org.ow2.bonita.facade.exception.VariableNotFoundException;
import org.ow2.bonita.facade.uuid.ProcessDefinitionUUID;
import org.ow2.bonita.util.AccessorUtil;

import java.util.HashMap;
import java.util.Map;

public class ProcessStarter {
    public static final String PROCESS_NAME = "Bug_report";
    public static final String PROCESS_VERSION = "1.0";
    private String project;
    private String version;
    private String summary;
    private RuntimeAPI runtimeAPI;

    public ProcessStarter(String project, String version, String summary) {
        runtimeAPI = AccessorUtil.getRuntimeAPI();
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
