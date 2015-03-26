package org.activiti;


import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;


public class CompensateEventTest {

    public static void main(String[] args) {
        test();
    }

    public static void test() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("org/activiti/test/CompensateTest.bpmn20.xml")
                .deploy();
//
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey("compensateProcess");

        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskName("usertask").singleResult();
        System.out.println("task.getName() = " + task.getName());
        taskService.complete(task.getId());


    }
}