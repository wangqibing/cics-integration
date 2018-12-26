package com.ccic.controller;

import com.ccic.domain.UserInfo;
import com.ccic.service.UserInfoService;
import com.ccic.util.DataParseUtil;
import com.ccic.util.KeyUtil;
import com.ccic.vo.CodeVo;
import com.ccic.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 8000600758 on 2018/10/7.
 */
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户验证
     */
    @RequestMapping(value = "/auth")
    public Object authUser(@RequestBody UserInfo user) {
        try {
            String encodeP = KeyUtil.encodePassword(user.getPassword());
            user.setPassword(encodeP);
            userInfoService.findOne(user);
        } catch (Exception e) {
            return "redirect:/integration/login";
        }
        return ResultVo.success();
    }

    /**
     * 用户新增
     */
    @RequestMapping(value = "/increase")
    public ResultVo increaseUser(@RequestBody UserInfo userInfo) {
        try {
            userInfoService.saveOne(userInfo);
        } catch (Exception e) {
            log.error("【用户新增】:新增失败,userInfo={}", userInfo);
            return ResultVo.error();
        }
        return ResultVo.success();
    }

    /**
     * 用户新增
     */
    @PostMapping(value = "/searchusercode")
    public ResultVo searchUserList() {
        List<UserInfo> userInfoList = null;
        try {
            userInfoList = userInfoService.findAllUser();
            List<CodeVo> codeVos = DataParseUtil.userInfo2CodeVo(userInfoList);
            return ResultVo.successWithData(codeVos);
        } catch (Exception e) {
            log.error("【用户查询】:新增失败,userInfoList={}", userInfoList);
            return ResultVo.error();
        }
    }

    /**
     * 通过系统id查找系统负责人
     * @param userId
     * @return
     */
    @PostMapping(value="/searchauthority",produces = "application/json")
    public ResultVo searchAuthority(@RequestBody String userId) {
        UserInfo result = null;
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(Long.valueOf(userId));
            result = userInfoService.findOne(userInfo);
            return ResultVo.successWithData(result);
        } catch (Exception e) {
            log.error("【用户查询】:新增失败,result={}", result);
            return ResultVo.error();
        }
    }
}
