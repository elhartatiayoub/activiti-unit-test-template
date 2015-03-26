package org.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * @author Joram Barrez
 */
public class SubProcessTest {
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    public void testSimpleSubProcess() {
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("org/activiti/test/SubProcess.bpmn20.xml")
                .deploy();

        // After staring the process, both tasks in the subprocess should be active
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("fixSystemFailure");
        TaskService taskService = activitiRule.getTaskService();
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(pi.getId())
                .orderByTaskName()
                .asc()
                .list();

        // Tasks are ordered by name (see query)
        Task investigateHardwareTask = tasks.get(0);
        Task investigateSoftwareTask = tasks.get(1);
        assertEquals("Investigate hardware", investigateHardwareTask.getName());
        assertEquals("Investigate software", investigateSoftwareTask.getName());

        // Completing both the tasks finishes the subprocess and enables the task after the subprocess
        taskService.complete(investigateHardwareTask.getId());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//      taskService.complete(investigateSoftwareTask.getId());
        Task efterTimerTask = taskService
                .createTaskQuery()
                .processInstanceId(pi.getId())
                .singleResult();
        assertEquals("Hand over to Level 2 support", efterTimerTask.getName());

        taskService.complete(efterTimerTask.getId());

        Task errorTask = taskService
                .createTaskQuery()
                .processInstanceId(pi.getId())
                .singleResult();
        assertEquals("error handling", errorTask.getName());

        HistoryService historyService = activitiRule.getHistoryService();
        List<HistoricTaskInstance> list =historyService.createHistoricTaskInstanceQuery().list();

        for (HistoricTaskInstance h:list){
            System.out.println("name: "+h.getName());
            System.out.println("   from: "+h.getStartTime().getTime());
            System.out.println("   to  : "+h.getEndTime().getTime());
        }

        // Clean up
        repositoryService.deleteDeploymentCascade(deployment.getId());
    }

}