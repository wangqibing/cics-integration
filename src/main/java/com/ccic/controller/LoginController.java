package com.ccic.controller;

import com.alibaba.fastjson.JSON;
import com.ccic.annotation.PersonalAnnotation;
import com.ccic.common.ControllerDep;
import com.ccic.config.BeanUtil;
import com.ccic.convert.pesonalargumentresolvertest.MyForm;
import com.ccic.convert.pesonalargumentresolvertest.Student;
import com.ccic.convert.pesonalargumentresolvertest.Teacher;
import com.ccic.databus.client.publish.DataBusPublishClient;
import com.ccic.databus.common.exception.DataBusException;
import com.ccic.domain.SysUserRelation;
import com.ccic.domain.UserInfo;
import com.ccic.service.QScheduleService;
import com.ccic.service.SysUserService;
import com.ccic.service.TokenService;
import com.ccic.service.UserInfoService;
import com.ccic.util.KeyUtil;
import com.ccic.vo.ResultVo;
import com.ccic.vo.ScheduleVo;
import com.ccic.vo.UserInfoVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 8000600758 on 2018/10/6.
 */
@Controller
@Slf4j
public class LoginController extends ControllerDep {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private QScheduleService qScheduleService;

    @Autowired
    private BeanUtil beanUtil;

    @Autowired
    private Environment environment;

    @Autowired
    private DataBusPublishClient dataBusPublishClient;

    @PostMapping(value = "/auth")
    @ResponseBody
//    @PersonalAnnotation("testvalue")
    public ResultVo login(@RequestBody UserInfo userInfo) {
        UserInfo userForBase = userInfoService.findOneByUserName(userInfo.getUserName());
        if (userForBase == null) {
            errorResponse.setMsg("登录失败,用户不存在");
            return errorResponse;
        } else {
            if (!userForBase.getPassword().equals(KeyUtil.encodePassword(userInfo.getPassword()))) {
                errorResponse.setMsg("登录失败,密码错误");
                return errorResponse;
            } else {
                String token = tokenService.getToken(userForBase);
                UserInfoVo userInfoVo = new UserInfoVo();
                userInfoVo.setToken(token);
                userInfoVo.setId(userForBase.getId());
                userInfoVo.setRedirectUrl("/integration/index");
                successResponse.setData(userInfoVo);
                ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes();
                HttpSession httpSession = attrs.getRequest().getSession();
                httpSession.setAttribute("Token", token);
                return successResponse;
            }
        }


    }


    @GetMapping("/login")
    public ModelAndView login() {
        Map paramMap = new HashMap();
        paramMap.put("authurl", "/integration/auth");
        return new ModelAndView("/page/login", paramMap);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam Long id) {
        if (null == id) {
            return new ModelAndView("/page/login");
        }
        Map paramMap = new HashMap();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo = userInfoService.findOne(userInfo);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        paramMap.put("dynamicURL", "/integration/auth");
        paramMap.put("user", userInfoVo);
        return new ModelAndView("/page/index", paramMap);
    }

    //introduce
    @GetMapping("/introduce")
    public ModelAndView introduce() {
        Map paramMap = new HashMap();
        paramMap.put("dynamicURL", "/integration/auth");
        return new ModelAndView("/page/introduce", paramMap);
    }

    //house_list.ftl
//introduce
    @GetMapping("/question_list")
    public ModelAndView questionList() {
        Map paramMap = new HashMap();
        paramMap.put("dynamicURL", "/integration/auth");
        return new ModelAndView("/page/question_list", paramMap);
    }

    @GetMapping("/question_edit")
    public ModelAndView questionEdit(@RequestParam String question_id, @RequestParam String operator, @RequestParam String userid) {
        Map paramMap = new HashMap();
        try {
            ScheduleVo scheduleVo = qScheduleService.findById(question_id);
            UserInfo userInfo = userInfoService.findById(userid);
            paramMap.put("dynamicURL", "/integration/auth");
            paramMap.put("authority", userInfo.getAuthority());
            paramMap.put("operator", operator);
            paramMap.put("questionDetail", new ObjectMapper().writeValueAsString(scheduleVo));
        } catch (Exception e) {
            log.error("【问题详情查询错误】" + e.getMessage());
            e.printStackTrace();
            return new ModelAndView("/error");
        }
        return new ModelAndView("/page/question_edit", paramMap);
    }

    @GetMapping("/question_workdesk")
    public ModelAndView questionWorkDesk() {
        Map paramMap = new HashMap();
        paramMap.put("dynamicURL", "/integration/auth");
        return new ModelAndView("/page/question_workdesk", paramMap);
    }

    @GetMapping("/question_increase")
    public ModelAndView questionEditBack() {
        return new ModelAndView("/page/question_increase");
    }


    @ResponseBody
    @RequestMapping("/test")
    public Map<String, Object> testValue(Teacher teacher, Student student) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("name","lg");
        map.put("age",23);
        map.put("date",new Date());
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/test1",method = RequestMethod.GET)
    public String testStringValue(@Param("name") String name) {
        try {
            dataBusPublishClient.send("Icore_paymentNotice","{}","test110","{}");
            dataBusPublishClient.close();
        } catch (DataBusException e) {
            e.printStackTrace();
        }
        return "中国";
    }

    @ResponseBody
    @RequestMapping(value = "/test2",method = RequestMethod.GET)
    public String testStringValue1(@Param("name") String name) {
        try {
            dataBusPublishClient.send("Icore_paymentNotice","{}","test110","{}");
        } catch (DataBusException e) {
            e.printStackTrace();
        }
        return "中国";
    }

    //时间格式转化绑定
    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy---MM---dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    @InitBinder("teacher")
    public void initTeacher(WebDataBinder binder) throws Exception {
        binder.setFieldDefaultPrefix("teacher.");

    }

    @InitBinder("student")
    public void initStudent(WebDataBinder binder) throws Exception {
        binder.setFieldDefaultPrefix("student.");

    }

}