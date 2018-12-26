package com.ccic.service.impl;

import com.ccic.domain.UserInfo;
import com.ccic.enums.ResultEnum;
import com.ccic.enums.UserEnum;
import com.ccic.exception.IntegrationException;
import com.ccic.repository.UserInfoRepository;
import com.ccic.service.UserInfoService;
import com.ccic.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by 8000600758 on 2018/10/7.
 */
@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    private UserInfoRepository userInfoRepository;
    /**
     * 查询一个用户
     * @param userInfo
     * @return
     */
    @Override
    public UserInfo findOne(UserInfo userInfo) {
        Example<UserInfo> example = Example.of(userInfo);
        Optional<UserInfo> optional = userInfoRepository.findOne(example);
        if(!optional.isPresent()){
            log.error("【用户查询】:用户不存在,userInfo={}",userInfo);
            throw new IntegrationException(ResultEnum.USER_LOG_ERROR);
        }
        return optional.get();
    }

    /** log.error("【用户查询】该用户不存在,userInfo={}",optional.get());
     throw new IntegrationException(ResultEnum.USER_LOG_ERROR);
     * 新增一个用户
     * @param userInfo
     * @return
     */
    @Override
    public UserInfo saveOne(UserInfo userInfo) {
        String encodeP = KeyUtil.encodePassword(userInfo.getPassword());
        userInfo.setPassword(encodeP);
        UserInfo result = userInfoRepository.save(userInfo);
        if(null == result){
            log.error("【用户新增】:新增用户失败,userInfo={}",userInfo);
            throw new IntegrationException(ResultEnum.USER_LOG_ERROR);
        }
        return result;
    }

    @Override
    public UserInfo findOneByUserName(String userName) {
        UserInfo result = userInfoRepository.findByUserName(userName);
        if(null == result){
            log.error("【用户查询】:用户不存在,userName={}",userName);
            throw new IntegrationException(ResultEnum.USER_LOG_ERROR);
        }
        return result;
    }

    @Override
    public UserInfo findById(String id) {
        return userInfoRepository.findById(Long.valueOf(id)).get();
    }

    @Override
    public List<UserInfo> findAllUser() {
        return userInfoRepository.findAll();
    }

    //通过用户权限查询分组
    @Override
    public List<UserInfo> findAuthUser(String authCode) {
        return userInfoRepository.findByAuthority(authCode);
    }


}
