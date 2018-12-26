package com.ccic.service;

import com.ccic.domain.SysInfo;
import com.ccic.domain.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Created by 8000600758 on 2018/10/7.
 */
public interface SysInfoService {
    //查询用户
    SysInfo findOne(SysInfo userInfo);
    //新增系统
    SysInfo saveOne(SysInfo userInfo);
    //通过用户名查询用户
    SysInfo findOneByUserName(String userName);
    //查询所有系统
    List<SysInfo> findAllSys();
}
