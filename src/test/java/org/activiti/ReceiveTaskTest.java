package org.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;


public class ReceiveTaskTest {

    public static void main(String[] args) {
        test();
    }

    public static void test() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("org/activiti/test/receiveTask.bpmn20.xml")
                .deploy();
//
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey("receiveTask");

        Execution execution = processEngine.getRuntimeService().createExecutionQuery()
                .processInstanceId(processInstance.getId())
                .activityId("waitState")
                .singleResult();
        processEngine.getRuntimeService().signal(execution.getId());
        List<Task> list =processEngine.getTaskService().createTaskQuery().list();
        System.out.println(list.size());

    }
}