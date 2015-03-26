package org.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MyUnitTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"org/activiti/test/my-process.bpmn20.xml"})
    public void test() {
        ProcessEngine processEngine = ProcessEngineConfiguration
                                        .createProcessEngineConfigurationFromResource("activiti.cfg.xml")
                                        .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                                        .buildProcessEngine();
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        assertNotNull(processInstance);

        Task task = activitiRule.getTaskService().createTaskQuery().list().get(0);
        System.out.println(activitiRule.getTaskService().createTaskQuery().list().size());
        System.out.println(task.getName());
        assertEquals("Activiti is awesome!", task.getName());
    }

}
