package com.ccic.service;

import com.ccic.domain.UserInfo;

import java.util.List;

/**
 * Created by 8000600758 on 2018/10/7.
 */
public interface UserInfoService {
    //查询用户
    UserInfo findOne(UserInfo userInfo);
    //新增用户
    UserInfo saveOne(UserInfo userInfo);
    //通过用户名查询用户
    UserInfo findOneByUserName(String userName);
    //通过id查询用户查询用户
    UserInfo findById(String Id);
    //查询所有用户信息
    List<UserInfo> findAllUser();
    //通过权限查询组用户
    List<UserInfo> findAuthUser(String authCode);
}
