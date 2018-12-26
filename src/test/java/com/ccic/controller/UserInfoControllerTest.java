package com.ccic.controller;

import com.ccic.domain.SysInfo;
import com.ccic.domain.SysUserRelation;
import com.ccic.domain.UserInfo;
import com.ccic.repository.SysInfoRepository;
import com.ccic.repository.SysUserRelationRepository;
import com.ccic.repository.UserInfoRepository;
import org.apache.catalina.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 8000600758 on 2018/10/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoControllerTest {
    @Autowired
    UserInfoController userInfoController;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    SysInfoRepository sysInfoRepository;
    @Autowired
    SysUserRelationRepository sysUserRelationRepository;
    @Test
    public void authUser() throws Exception {
    }

    @Test
    public void increaseUser() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("liuzhen");
        userInfo.setPassword("liuzhen");
        userInfoController.increaseUser(userInfo);
    }

    @Test
    public void updateUserSys() throws Exception {
        List<SysUserRelation> sysUserRelations = new ArrayList<>();
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        for(UserInfo userInfo : userInfoList){
                List<SysInfo> result = sysInfoRepository.findBySysDutyDevNo(userInfo.getId().toString());
                if(null != result){
                    for(SysInfo sysInfo : result){
                        SysUserRelation sysUserRelation = new SysUserRelation();
                        sysUserRelation.setUserNum(userInfo.getId().toString());
                        sysUserRelation.setUserName(userInfo.getUserCname());
                        sysUserRelation.setSysNum(sysInfo.getId().toString());
                        sysUserRelation.setSysCname(sysInfo.getSysCname());
                        sysUserRelations.add(sysUserRelation);
                    }
                }else{
                    SysUserRelation sysUserRelation = new SysUserRelation();
                    sysUserRelation.setUserNum(userInfo.getId().toString());
                    sysUserRelation.setUserName(userInfo.getUserCname());
                    sysUserRelation.setSysNum("00000000");
                    sysUserRelation.setSysCname("自成一派");
                    sysUserRelations.add(sysUserRelation);
                }

            }
        sysUserRelationRepository.saveAll(sysUserRelations);

        }
//        userInfoRepository.saveAll(userInfoList);
    }