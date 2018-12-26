package com.ccic.service.activiti.impl;

import com.ccic.domain.UserInfo;
import com.ccic.enums.UserEnum;
import com.ccic.service.UserInfoService;
import com.ccic.service.activiti.ResumeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 8000600758 on 2018/10/30.
 */
public class ResumeServiceImpl implements TaskListener {
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void notify(DelegateTask delegateTask) {
        //查询领导组
//        List<UserInfo> managers =  userInfoService.findAuthUser(UserEnum.MANAGE_USER.getCode());
//        List userids = managers.stream().map(e->e.getId()).collect(Collectors.toList());
                String assignee = "张三丰";
//        指定个人任务
        delegateTask.setAssignee(assignee);
//        if(null== userids){
//            delegateTask.addCandidateUser("无用户");
//        }

//        delegateTask.addCandidateUsers(userids);
    }
}
