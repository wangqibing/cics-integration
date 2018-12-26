package com.ccic.controller;

import com.ccic.common.ControllerDep;
import com.ccic.domain.SysInfo;
import com.ccic.domain.SysUserRelation;
import com.ccic.domain.UserInfo;
import com.ccic.service.SysUserService;
import com.ccic.service.UserInfoService;
import com.ccic.service.impl.SysInfoServiceImpl;
import com.ccic.util.KeyUtil;
import com.ccic.vo.QuestionVo;
import com.ccic.vo.ResultVo;
import com.ccic.vo.SysVo;
import com.ccic.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 8000600758 on 2018/10/15.
 */
@Slf4j
@RestController
@RequestMapping(value="/sys")
public class SysController extends ControllerDep{
    @Autowired
    private SysInfoServiceImpl sysInfoServiceImpl;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 查询系统码表，返回ID，name
     * @return
     */
    @PostMapping(value="/finddistinct")
    public ResultVo findAll(){
        QuestionVo questionVo = new QuestionVo();
        try{
            List<SysInfo> sysInfos = sysInfoServiceImpl.findAllSys();
            //对返回的sysInfo做处理
            List<SysVo> sysVos = new ArrayList<>();
            for(SysInfo sysInfo:sysInfos){
                SysVo sysVo = new SysVo();
                BeanUtils.copyProperties(sysInfo,sysVo);
                sysVos.add(sysVo);
            }
            questionVo.setSysInfoVos(sysVos);
            successResponse.setData(questionVo);
        }catch(Exception e){
            log.error(e.toString());
            return errorResponse;
        }
        return successResponse;
    }

    @RequestMapping(value="/insertone")
    public ResultVo inserOne(@RequestBody @Validated SysInfo sysInfo){
        QuestionVo questionVo = new QuestionVo();
        try{
            List<SysVo> sysInfos = new ArrayList<>();
            SysInfo result = sysInfoServiceImpl.saveOne(sysInfo);
            SysVo sysVo = new SysVo();
            BeanUtils.copyProperties(result,sysVo);
            sysInfos.add(sysVo);
            questionVo.setSysInfoVos(sysInfos);
            successResponse.setData(questionVo);
        }catch(Exception e){
            log.error(e.toString());
            return errorResponse;
        }
        return successResponse;
    }

    /**
     * 通过系统id查找系统负责人
     * @param sysNum
     * @return
     */
    @PostMapping(value="/finduserbysys",produces = "application/json")
    public ResultVo findUserBySys(@RequestBody String sysNum){
        QuestionVo questionVo = new QuestionVo();
        try{
            List<SysUserRelation> sysUserRelations = sysUserService.findBySysNum(sysNum);
            //对返回的sysInfo做处理
            List<UserInfoVo> userInfoVos = new ArrayList<>();
            for(SysUserRelation sysUserRelation:sysUserRelations){
                UserInfoVo userInfoVo = new UserInfoVo();
                UserInfo userInfo = userInfoService.findById(sysUserRelation.getUserNum());
                BeanUtils.copyProperties(userInfo,userInfoVo);
                userInfoVos.add(userInfoVo);
            }
            questionVo.setUserInfoVos(userInfoVos);
            successResponse.setData(questionVo);
        }catch(Exception e){
            log.error(e.toString());
            return errorResponse;
        }
        return successResponse;
    }


}
