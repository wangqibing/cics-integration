package com.ccic.service;

import com.ccic.domain.SysInfo;
import com.ccic.domain.SysUserRelation;

import java.util.List;


/**
 * Created by 8000600758 on 2018/10/7.
 */
public interface SysUserService {
    //通过系统查询负责人
    List<SysUserRelation> findBySysNum(String SysNum);
    //通过系统查询负责人
    SysUserRelation findByUserNum(String UserNum);
}
