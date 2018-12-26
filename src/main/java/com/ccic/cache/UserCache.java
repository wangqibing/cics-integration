package com.ccic.cache;

import com.ccic.domain.UserInfo;
import com.ccic.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Created by 8000600758 on 2018/10/9.
 */
@Component
public class UserCache {
    @Autowired
    private TokenService tokenService;

    @Cacheable(value ="userToken", key = "#userInfo.getUserName()")
    public String getUserTokenCache(UserInfo userInfo){
        return null;
    }

    @CachePut(value ="userToken#${select.cache.timeout:1000}", key = "#userInfo.getUserName()")
    public String setUserTokenCache(UserInfo userInfo){
        return tokenService.getToken(userInfo);
    }
}
