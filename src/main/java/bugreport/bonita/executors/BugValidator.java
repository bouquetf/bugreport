package bugreport.bonita.executors;

import org.ow2.bonita.facade.QueryRuntimeAPI;
import org.ow2.bonita.facade.RuntimeAPI;
import org.ow2.bonita.facade.exception.IllegalTaskStateException;
import org.ow2.bonita.facade.exception.InstanceNotFoundException;
import org.ow2.bonita.facade.exception.TaskNotFoundException;
import org.ow2.bonita.facade.exception.VariableNotFoundException;
import org.ow2.bonita.facade.uuid.ActivityInstanceUUID;
import org.ow2.bonita.facade.uuid.ProcessInstanceUUID;
import org.ow2.bonita.light.LightProcessInstance;
import org.ow2.bonita.util.AccessorUtil;

public class BugValidator {
    private final ActivityInstanceUUID activityInstanceUUID;
    private final LightProcessInstance lightProcessInstance;
    private ProcessInstanceUUID processInstanceUUID;
    private RuntimeAPI runtimeAPI;
    private QueryRuntimeAPI queryRuntimeAPI;

    public BugValidator(String taskId) throws InstanceNotFoundException, TaskNotFoundException {
        runtimeAPI = AccessorUtil.getRuntimeAPI();
        queryRuntimeAPI = AccessorUtil.getQueryRuntimeAPI();
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
