package com.ccic.controller;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 8000600758 on 2018/10/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiControllerTest {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;


    @Test
    public void test(String bizNo) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2 启动流程
        ProcessInstance pi = processEngine.getRuntimeService()//
                .startProcessInstanceByKey("myProcess_1");
        System.out.println("pid:" + pi.getId());
    }

    @Test
    public void depolyProcesses() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .addClasspathResource("processes/myProcess.bpmn")//从classpath的资源中加载，一次只能加载一个文件
                .deploy();//完成部署
        System.out.println(deployment.getId());

    }

    //指定个人任务查询
    @Test
    public void findMyTaskList() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        String userId = "张三丰";
        List<Task> list = processEngine.getTaskService()//
                .createTaskQuery()//
                .taskAssignee(userId)//指定个人任务查询
                .list();
        for (Task task : list) {
            System.out.println("id=" + task.getId());
            System.out.println("name=" + task.getName());
            System.out.println("assinee=" + task.getAssignee());
            System.out.println("createTime=" + task.getCreateTime());
            System.out.println("executionId=" + task.getExecutionId());

        }
    }

    //指定组任务查询
    @Test
    public void findGroupTaskList() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        String userId = "张三丰";
        List<Task> list = processEngine.getTaskService()//
                .createTaskQuery()          //
                .taskCandidateOrAssigned(userId)//指定个人任务查询
                .list();
        for (Task task : list) {
            Map tmap = task.getProcessVariables();
            Iterator iterator;
            while ((iterator = tmap.keySet().iterator()).hasNext()){
                System.out.println(tmap.get(iterator.next()));
            }
            System.out.println("id=" + task.getId());
            System.out.println("name=" + task.getName());
            System.out.println("assinee=" + task.getAssignee());
            System.out.println("createTime=" + task.getCreateTime());
            System.out.println("executionId=" + task.getExecutionId());
        }
    }

    @Test
    public void completeTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        String taskId = "57504";
        Map<String,Object> variables = new HashMap<>();
        variables.put("userID","张铁林");
        variables.put("status","1");
        variables.put("level","heigh");
        processEngine.getTaskService()//
                .complete(taskId,variables);//
        System.out.println("完成任务");
    }

    //可以分配个人任务从一个人到另一个人（认领任务）
    @Test
    public void setAssigneeTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //任务ID
        String taskId = "15005";
        //指定认领的办理者
        String userId = "周芷若";
        processEngine.getTaskService()//
                .setAssignee(taskId, userId);
    }

    /**
     * 查询正在执行的任务办理人表
     */
    @Test
    public void findRunPersonTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        String taskId = "15005";
        List<IdentityLink> list = processEngine.getTaskService()
                .getIdentityLinksForTask(taskId);
        if (list != null && list.size() > 0) {
            for (IdentityLink identityLink : list) {
                System.out.println(identityLink.getUserId() + " "
                        + identityLink.getProcessInstanceId() + " "
                        + identityLink.getType());
            }
        }
    }

    /**
     * 查询历史任务的办理人
     */
    @Test
    public void findHistoryPersonTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        String processInstanceId = "15001";
        List<HistoricIdentityLink> list = processEngine.getHistoryService()
                .getHistoricIdentityLinksForProcessInstance(processInstanceId);
        if (list!=null&&list.size()>0) {
            for (HistoricIdentityLink historicIdentityLink : list) {
                System.out.println(historicIdentityLink.getTaskId()+" "
                        +historicIdentityLink.getType()+" "
                        +historicIdentityLink.getProcessInstanceId());
            }
        }
    }

    /**
     * 拾取任务 组任务分配给个人任务 指定任务的办理人字段
     */
    @Test
    public void claim() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //个人Id
        String userId = "大大";
        String taskId = "9905";
        //可以是组任务的成员 也可以是其他人
        processEngine.getTaskService()
                .claim(taskId, userId);
    }

    /**
     * 将个人任务回退到组任务 前提是之前一定是组任务
     */
    @Test
    public void setAssignee() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        String taskId = "8905";
        processEngine.getTaskService()
                .setAssignee(taskId, null);
    }

    /**
     * 向组任务中添加成员
     */
    @Test
    public void addGroupUser() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        String taskId = "8905";
        String userId = "大S";
        processEngine.getTaskService()
                .addCandidateUser(taskId, userId);
    }

    /**
     * 从组任务中删除成员
     */
    @Test
    public void deleteGroupUser() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        String taskId = "8905";
        String userId = "大S";
        processEngine.getTaskService()
                .deleteCandidateUser(taskId, userId);
    }
}
