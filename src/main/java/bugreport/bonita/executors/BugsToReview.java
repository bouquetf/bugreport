package bugreport.bonita.executors;

import bugreport.entities.Bug;
import org.ow2.bonita.facade.QueryRuntimeAPI;
import org.ow2.bonita.facade.runtime.ActivityState;
import org.ow2.bonita.light.LightTaskInstance;
import org.ow2.bonita.util.AccessorUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BugsToReview {
    private QueryRuntimeAPI queryRuntimeAPI;

    public BugsToReview() {
        queryRuntimeAPI = AccessorUtil.getQueryRuntimeAPI();
    }

    public List<Bug> retrieve() {
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
}