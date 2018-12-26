package com.ccic.controller;

import com.alibaba.fastjson.JSON;
import com.ccic.common.ControllerDep;
import com.ccic.domain.QSchedule;
import com.ccic.domain.SysUserRelation;
import com.ccic.domain.UserInfo;
import com.ccic.dto.QSchedulePageRequestDto;
import com.ccic.dto.XML2BeanTest;
import com.ccic.service.QScheduleService;
import com.ccic.service.SysUserService;
import com.ccic.service.UserInfoService;
import com.ccic.util.DataParseUtil;
import com.ccic.vo.*;
import com.ccic.vo.QuestionVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 8000600758 on 2018/9/29.
 */
@Slf4j
@RestController
@RequestMapping(value = "/question")
public class QScheduleController extends ControllerDep {

    @Autowired
    private QScheduleService qScheduleService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SysUserService sysUserService;
    @PostMapping(value = "/pagelist")
    public ResultVo findByPage(@RequestBody QSchedulePageRequestDto qSchedulePageRequestDto) {
        Page<QSchedule> qSchedules = qScheduleService.findByPage(qSchedulePageRequestDto);
        List<ScheduleVo> questionVos = DataParseUtil.qSchedules2questionvos(qSchedules.getContent());
        Long totalElements =  qSchedules.getTotalElements();
        Integer totalPage = qSchedules.getTotalPages();
        Integer currentPage = qSchedules.getNumber();
        Map pageMap = new HashMap();
        pageMap.put("questionList",questionVos);
        pageMap.put("totalElements",totalElements);
        pageMap.put("totalPage",totalPage);
        pageMap.put("currentPage",currentPage+1);
        successResponse.setData(pageMap);
        return successResponse;
    }

    @PostMapping("/search")
    public ModelAndView login() {
        Map paramMap = new HashMap();
        paramMap.put("authurl", "/login");
        return new ModelAndView("/page/list", paramMap);
    }

    @PostMapping(value = "/test",consumes = "text/xml")
    public String test(@RequestBody XML2BeanTest testDto) {
       System.out.println(testDto);
       return "ok";
    }

    @PostMapping("/question_save")
    public Object questionEdit(@RequestBody ScheduleVo scheduleVo) {
        try {
            String question_id = scheduleVo.getId();
//            ScheduleVo scheduleVo = qScheduleService.findById(question_id);
//            UserInfo userInfo = userInfoService.findById(userid);
//            paramMap.put("dynamicURL", "/integration/auth");
//            paramMap.put("authority", userInfo.getAuthority());
//            paramMap.put("operator", operator);
//            paramMap.put("questionDetail", new ObjectMapper().writeValueAsString(scheduleVo));
        } catch (Exception e) {
            log.error("【问题详情查询错误】" + e.getMessage());
            e.printStackTrace();
            return new ModelAndView("/error");
        }
        return null;
//        return new ModelAndView("/page/question_edit", paramMap);
    }

    @PostMapping("/question_increase")
    public ResultVo questionIncrease(@RequestBody ScheduleVo scheduleVo) {
        Map paramMap = new HashMap();
        try {
            QSchedule qSchedule = new QSchedule();
            BeanUtils.copyProperties(scheduleVo,qSchedule);
            //获取提问人相关信息
            SysUserRelation questionPersonRelation = sysUserService.findByUserNum(qSchedule.getQuestionPerson());
            qSchedule.setQuestionSysCname(questionPersonRelation.getSysCname());
            qSchedule.setQuestionSys(questionPersonRelation.getSysNum());
            //获取问题解决人相关信息
            SysUserRelation resolvePersonRelation = sysUserService.findByUserNum(qSchedule.getResolveDutyPerson());
            qSchedule.setResolveSysCname(resolvePersonRelation.getSysCname());
            qSchedule.setResolveDutyPersonCname(questionPersonRelation.getUserName());


                QSchedule result =  qScheduleService.insertOne(qSchedule);
            paramMap.put("id",result.getId());
            paramMap.put("redirectUrl","question_list");
            successResponse.setData(paramMap);
//            ScheduleVo scheduleVo = qScheduleService.findById(question_id);
//            UserInfo userInfo = userInfoService.findById(userid);
//            paramMap.put("dynamicURL", "/integration/auth");
//            paramMap.put("authority", userInfo.getAuthority());
//            paramMap.put("operator", operator);
//            paramMap.put("questionDetail", new ObjectMapper().writeValueAsString(scheduleVo));
        } catch (Exception e) {
            log.error("【问题详情查询错误】" + e.getMessage());
            e.printStackTrace();
            errorResponse.setData(e.getMessage());
            return errorResponse;
        }
        return successResponse;
    }
}
