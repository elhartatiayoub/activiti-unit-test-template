package org.activiti;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class MyUnitTestWithEngine {

    public static void main(String[] args) {
        test();
    }


    public static void test() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                        .createDeployment()
                        .addClasspathResource("org/activiti/test/VacationProcess2.bpmn20.xml")
                        .deploy();
//        ProcessInstance processInstance = processEngine
//                                            .getRuntimeService()
//                                            .startProcessInstanceByKey("vacationRequest");
//        assertNotNull(processInstance);

        assertTrue(processEngine
                .getRepositoryService()
                .createProcessDefinitionQuery()
                .count()>0);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("vacationRequest2", variables);

        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskCandidateGroup("management").taskNameLike("Handle vacation request").singleResult();
        System.out.println("task.getName() = " + task.getName());
        Map<String, String> taskVariables = new HashMap<String, String>();
        taskVariables.put("vacationApproved", "false");
        taskVariables.put("managerMotivation", "We have a tight deadline!");
        FormService formService = processEngine.getFormService();
        formService.submitTaskFormData(task.getId(),taskVariables);

//        taskService.complete(task.getId());
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricIdentityLink> list = historyService.getHistoricIdentityLinksForProcessInstance("vacationRequest");
        for (HistoricIdentityLink l:list){
            System.out.println(l);
        }
        
    }
}
