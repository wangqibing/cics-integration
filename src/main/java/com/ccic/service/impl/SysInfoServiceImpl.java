package com.ccic.service.impl;

import com.ccic.domain.SysInfo;
import com.ccic.domain.UserInfo;
import com.ccic.enums.ResultEnum;
import com.ccic.exception.IntegrationException;
import com.ccic.repository.SysInfoRepository;
import com.ccic.repository.UserInfoRepository;
import com.ccic.service.SysInfoService;
import com.ccic.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by 8000600758 on 2018/10/7.
 */
@Slf4j
@Service
public class SysInfoServiceImpl implements SysInfoService{

    @Autowired
    private SysInfoRepository sysInfoRepository;

    @Override
    public SysInfo findOne(SysInfo sysInfo) {
        return null;
        //TODO
    }

    @Override
    public SysInfo saveOne(SysInfo sysInfo) {
        return sysInfoRepository.save(sysInfo);
        //TODO
    }

    @Override
    public SysInfo findOneByUserName(String userName) {
        return null;
        //TODO
    }

    @Override
    public List<SysInfo> findAllSys() {
        return sysInfoRepository.findAll();
    }
}
