package com.ccic.controller;

import com.ccic.domain.QSchedule;
import com.ccic.domain.SysInfo;
import com.ccic.domain.UserInfo;
import com.ccic.repository.QScheduleRepository;
import com.ccic.repository.UserInfoRepository;
import com.ccic.service.UserInfoService;
import com.ccic.util.PinYinUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by 8000600758 on 2018/10/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysControllerTest {
    @Autowired
    private SysController sysController;
    @Autowired
    private QScheduleRepository qScheduleRepository;
    @Autowired
    private UserInfoController userInfoController;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    public void findAll() throws Exception {

    }
//新增用户
    @Test
//    @Transactional
    public void insertUser() throws Exception {
        List<QSchedule> qSchedules = qScheduleRepository.findAll();
        Set<String> nameSet = new HashSet<>();
        for(QSchedule qSchedule:qSchedules){
            nameSet.add(qSchedule.getQuestionPerson().toUpperCase());
        }
        Iterator iterator = nameSet.iterator();
        while(iterator.hasNext()){
//            try
//            {
                String name  =iterator.next().toString();
                if(null == name || "".equals(name))
                    continue;
                UserInfo userInfo = new UserInfo();
                if(name.equals("ORIK")){
                    userInfo.setUserName(name);
                    userInfo.setPassword(name);
                    userInfo.setStatus(0);
                }else {
                    userInfo.setUserName(PinYinUtil.ToPinyin(name));
                    userInfo.setPassword(PinYinUtil.ToPinyin(name));
                    userInfo.setStatus(0);
                }

                System.out.println(userInfoController.increaseUser(userInfo));
//            }catch(Exception e){
//                System.out.println(e.toString());
//                continue;
//            }


        }

    }


    //新增用户
    @Test
    //    @Transactional
    public void inserSys() throws Exception {
        List<QSchedule> qSchedules = qScheduleRepository.findAll();
        Set<String> sysSet = new HashSet<>();
        for(QSchedule qSchedule:qSchedules){
            sysSet.add(qSchedule.getQuestionSys().toUpperCase()+","+qSchedule.getQuestionPerson());
        }
        Iterator iterator = sysSet.iterator();
        String [] params = new String[2];
        while(iterator.hasNext()){
//            try
//            {
            String param  =iterator.next().toString();
            params =param.split(",");
            if(null == params || "".equals(params))
                continue;
            SysInfo sysInfo = new SysInfo();
            sysInfo.setSysCname(params[0]);
            sysInfo.setSysEname(params[0]);
            UserInfo userInfo = userInfoRepository.findByUserName(PinYinUtil.ToPinyin(params[1].toUpperCase()));
            sysInfo.setSysDutyDevNo(userInfo.getId().toString());
            sysInfo.setSysDutyDirectNo(userInfo.getId().toString());
//            if(name.equals("ORIK")){
//                userInfo.setUserName(name);
//                userInfo.setPassword(name);
//                userInfo.setStatus(0);
//            }else {
//            }

            System.out.println(sysController.inserOne(sysInfo));
//            }catch(Exception e){
//                System.out.println(e.toString());
//                continue;
//            }

        }

    }
}