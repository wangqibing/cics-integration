package com.ccic.service.impl;

import com.ccic.domain.SysUserRelation;
import com.ccic.repository.SysUserRelationRepository;
import com.ccic.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by 8000600758 on 2018/10/7.
 */
@Service
public class SysUserServiceImpl  implements SysUserService{
    @Autowired
    private SysUserRelationRepository  sysUserRelationRepository;


    @Override
    public List<SysUserRelation> findBySysNum(String SysNum) {
        return sysUserRelationRepository.findBySysNum(SysNum);
    }

    @Override
    public SysUserRelation findByUserNum(String UserNum) {
        return sysUserRelationRepository.findByUserNum(UserNum);
    }
}
