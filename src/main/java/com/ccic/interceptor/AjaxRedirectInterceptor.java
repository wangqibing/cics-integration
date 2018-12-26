package com.ccic.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ccic.domain.UserInfo;
import com.ccic.enums.ResultEnum;
import com.ccic.exception.IntegrationException;
import com.ccic.service.TokenService;
import com.ccic.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author jinbin
 * @date 2018-07-08 20:41
 */
public class AjaxRedirectInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String redirect = httpServletRequest.getHeader("Redirect");// 从 http 请求头中取出 token
        return true;
    }

}
