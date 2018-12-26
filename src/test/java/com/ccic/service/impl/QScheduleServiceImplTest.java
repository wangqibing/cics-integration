package com.ccic.service.impl;

import com.ccic.controller.UserInfoController;
import com.ccic.domain.QSchedule;
import com.ccic.domain.SysInfo;
import com.ccic.domain.UserInfo;
import com.ccic.enums.QScheduleEnum;
import com.ccic.repository.QScheduleRepository;
import com.ccic.repository.SysInfoRepository;
import com.ccic.repository.UserInfoRepository;
import com.ccic.service.QScheduleService;
import com.ccic.service.UserInfoService;
import com.ccic.util.ExcelUtil;
import com.ccic.util.PinYinUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by 8000600758 on 2018/9/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QScheduleServiceImplTest {
    @Autowired
    private QScheduleService qScheduleService;
    @Autowired
    QScheduleRepository qScheduleRepository;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserInfoController userInfoController;
    @Autowired
    SysInfoRepository sysInfoRepository;
    @Test
    public void insertOne() throws Exception {
        //excel的解析成List
        List<Map<String, String>> list = ExcelUtil.readExcelByStream("周边系统联调问题汇总20180928-2.xlsx");
        List<QSchedule> qSchedules = parse2BizObject(list);
        Assert.isTrue(qScheduleService.saveList(qSchedules));
    }

    public List<QSchedule> parse2BizObject(List<Map<String, String>> resource) throws ParseException {
        List<QSchedule> scheduleList = new ArrayList<QSchedule>();
        for (int i = 0; i < resource.size(); i++) {
            QSchedule qSchedule = new QSchedule();
            Map tMap = resource.get(i);
            Set keys = tMap.keySet();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next().toString();
                String value = tMap.get(key) == null ? "" : tMap.get(key).toString();
                if ("系 统".equals(key)) {
                    qSchedule.setQuestionSys(value);
                } else if ("问题提出人".equals(key)) {
                    qSchedule.setQuestionPerson(value);
                } else if ("核心解决情况".equals(key)) {
                    Integer code = 3;  //0-已解决/1-解决中/2-未解决/3-待确认  默认为待确认
                    if (value.contains("已解决") || value.contains("不需要解决")) {
                        code = 0;
                    } else if (value.contains("未解决") || value.contains("待解决") || value.contains("待解决")) {
                        code = 2;
                    } else if (value.contains("解决中")) {
                        code = 1;
                    } else {
                        code = 3;
                    }
                    qSchedule.setResolveStatus(code);
                } else if ("周边验证结果".equals(key)) {
                    Integer code = 2;  //0-不通过/1-通过/2-待确认 默认为待确认
                    if (value.equals("不通过")) {
                        code = 0;
                    } else if (value.equals("通过")) {
                        code = 1;
                    } else {
                        code = 2;
                    }
                    qSchedule.setQuestionValideStatus(code);
                } else if ("核心条线".equals(key)) {
                    qSchedule.setResolveSys(value);
                } else if ("接 口".equals(key)) {
                    qSchedule.setResolveInterface(value);
                } else if ("联调中的问题".equals(key)) {
                    qSchedule.setQuestionDescrbe(value);
                } else if ("责任人".equals(key)) {
                    qSchedule.setResolveDutyPerson(value);
                } else if ("依赖人员".equals(key)) {
                    qSchedule.setResolveDependenPerson(value);
                } else if ("优先级".equals(key)) {
                    //0-低优先级，1-中优先级，2-高优先级',默认为低优先级
                    Integer code = 0;
                    if (value.contains("低")) {
                        code = 0;
                    } else if (value.contains("中")) {
                        code = 1;
                    } else if (value.contains("高")) {
                        code = 2;
                    }
                    qSchedule.setResolvePriority(code);
                } else if ("核心反馈（反馈人：内容）".equals(key)) {
                    qSchedule.setResolveBack(value);
                } else if ("提出时间".equals(key)) {
                    qSchedule.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").parse(value == "" ? null : value));
                } else if ("预计解决时间）".equals(key)) {
                    qSchedule.setPrepareResolveTime(new SimpleDateFormat("yyyy-MM-dd").parse(value == "" ? null : value));
                } else if ("备 注".equals(key)) {
                    qSchedule.setRemark(value);
                }
            }
            scheduleList.add(qSchedule);
        }
        return scheduleList;
    }

    @Test
    public void search(){
        List<QSchedule> qSchedules = qScheduleRepository.findAll();
        List<UserInfo> userInfos = userInfoRepository.findAll();
        List<SysInfo> sysInfos =  sysInfoRepository.findAll();
        for (QSchedule qSchedule : qSchedules){
                String userName = qSchedule.getQuestionPerson();
                String userSys = qSchedule.getQuestionSys();
                String userResloveSys = qSchedule.getResolveSys();
                String depUser = qSchedule.getResolveDependenPerson();
                String resolveDutyUser = qSchedule.getResolveDutyPerson();
            userInfos.stream().filter(e->e.getUserCname().equals(userName)).forEach(e->parsePrsentP(e.getId().toString(),qSchedule));
            userInfos.stream().filter(e->e.getUserCname().equals(resolveDutyUser)).forEach(e->parseResolveDutyUser(e.getId().toString(),qSchedule));
            userInfos.stream().filter(e->e.getUserCname().equals(depUser)).forEach(e->parseDepUser(e.getId().toString(),qSchedule));
//            userInfos.stream().filter(e->e.get().equals(userSys)).forEach(e->parseDerectUser(e.getUserCname(),qSchedule));
            sysInfos.stream().filter(e->e.getSysCname().equals(userSys)).forEach(e->parseQuestionUserSys(e.getId().toString(),qSchedule));
            sysInfos.stream().filter(e->e.getSysCname().equals(userResloveSys)).forEach(e->parseResolveUserSys(e.getId().toString(),qSchedule));
            System.out.println(123);
        }
        List<QSchedule> qSchedule1 = qScheduleRepository.saveAll(qSchedules);
    }
    public void parseResolveDutyUser(String userName  ,  QSchedule qSchedule){
            qSchedule.setResolveDutyPersonCname(userName);
    }
    public void parseDepUser(String userName  ,  QSchedule qSchedule){
        qSchedule.setResolveDepPersonCname(userName);
    }
    public void parseQuestionUserSys(String userName  ,  QSchedule qSchedule){
        qSchedule.setQuestionSysCname(userName);
    }
    public void parseResolveUserSys(String userName  ,  QSchedule qSchedule){
        qSchedule.setResolveSysCname(userName);
    }
    public void parsePrsentP(String userName  ,  QSchedule qSchedule){
        qSchedule.setQuestionPersonCname(userName);
    }

    @Test
    public void testsearchDistinct() throws Exception {
//        List<String> qSchedules =  qScheduleRepository.findDistinctResolveName();
//        for(String name : qSchedules){
//            if(null == name || "".equals(name.trim())){
//                continue;
//            }
//            if(name.trim().contains("/")){
//                String[] names = name.split("/");
//                for (String name1 : names){
//                    UserInfo userInfo = new UserInfo();
//                    userInfo.setUserName(PinYinUtil.ToPinyin(name1));
//                    userInfo.setUserCname(name1);
//                    userInfo.setStatus(0);
//                    userInfo.setPassword(PinYinUtil.ToPinyin(name1));
//                    userInfoController.increaseUser(userInfo);
//                }
//            }else{
//                UserInfo userInfo = new UserInfo();
//                userInfo.setUserName(PinYinUtil.ToPinyin(name));
//                userInfo.setUserCname(name);
//                userInfo.setStatus(0);
//                userInfo.setPassword(PinYinUtil.ToPinyin(name));
//                userInfoController.increaseUser(userInfo);
//            }
//
//
//        }
//        System.out.println(qScheduleRepository.findBySomething("周铁传","呼叫中心系统",1,new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2018-07-16 00:00:00"),null));
        List<String> tlist = new ArrayList<>();
        tlist.add("1538208959411190529");
        System.out.println(qScheduleRepository.findByPage("1000000228",null,null,null,tlist,PageRequest.of(0,10)).getTotalElements());
    }


    @Test
    public void updateCname(){
        List<QSchedule> qSchedules = qScheduleRepository.findAll();
        List<UserInfo> userInfos = userInfoRepository.findAll();
        List<SysInfo> sysInfos =  sysInfoRepository.findAll();
        for (QSchedule qSchedule : qSchedules){
            String questionName = qSchedule.getQuestionPerson();
            String userSys = qSchedule.getQuestionSys();
            String userResloveSys = qSchedule.getResolveSys();
            String depUser = qSchedule.getResolveDependenPerson();
            String resolveDutyUser = qSchedule.getResolveDutyPerson();
            userInfos.stream().filter(e->e.getId().toString().equals(questionName)).forEach(e->parsePrsentP(e.getUserCname().toString(),qSchedule)); //问题提出人的中文名称
            userInfos.stream().filter(e->e.getId().toString().equals(resolveDutyUser)).forEach(e->parseResolveDutyUser(e.getUserCname().toString(),qSchedule)); //问题解决负责人
            userInfos.stream().filter(e->e.getId().toString().equals(depUser)).forEach(e->parseDepUser(e.getUserCname().toString(),qSchedule));
//            userInfos.stream().filter(e->e.get().equals(userSys)).forEach(e->parseDerectUser(e.getUserCname(),qSchedule));
            sysInfos.stream().filter(e->e.getId().toString().equals(userSys)).forEach(e->parseQuestionUserSys(e.getSysCname().toString(),qSchedule));
            sysInfos.stream().filter(e->e.getId().toString().equals(userResloveSys)).forEach(e->parseResolveUserSys(e.getSysCname().toString(),qSchedule));
            System.out.println(123);
        }
        List<QSchedule> qSchedule1 = qScheduleRepository.saveAll(qSchedules);
    }
}