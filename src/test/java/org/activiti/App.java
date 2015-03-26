package org.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.Execution;

import java.util.List;

/**
 * Created by Iob on 24/03/2015.
 */
public class App {
    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtime = processEngine.getRuntimeService();
        Execution execution = runtime.createExecutionQuery().singleResult();
        System.out.println(runtime.createExecutionQuery().count());
        runtime.signal(execution.getId());
        List<HistoricActivityInstance> list = processEngine.getHistoryService().createHistoricActivityInstanceQuery().list();
        for (HistoricActivityInstance h:list){
            System.out.println(h.getActivityName());
        }
    }

}
