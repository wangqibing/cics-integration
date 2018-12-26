package com.ccic.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ccic.domain.UserInfo;
import com.ccic.enums.ResultEnum;
import com.ccic.service.TokenService;
import com.ccic.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;


/**
 * @author jinbin
 * @date 2018-07-08 20:41
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        String sessionToken =null;
        try {
            sessionToken  = session.getAttribute("Token").toString();
        }catch(Exception e){
            log.info("session is null");
        }

        String headToken = httpServletRequest.getHeader("Token");
        String token;
        if(sessionToken != null && !"".equals(sessionToken)){
            token = sessionToken;
        }else{
            token = headToken;
        }
            if (token == null || "".equals(token)) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login");
                return false;
            } else {
                String userName ="";
                try {
                    userName = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login");
                    throw new RuntimeException("401");

                }
                UserInfo loginUser = new UserInfo();
                loginUser.setUserName(userName);
                UserInfo user = userInfoService.findOne(loginUser);
                if (user == null) {
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login");
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                // 验证 token

                if (tokenService.verifyToken(user, token).equals(ResultEnum.TOKEN_ERROR)) {
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login");
                    log.info(ResultEnum.ERROR.getMsg());
                    return false;
                } else if (tokenService.verifyToken(user, token).equals(ResultEnum.TOKEN_INVALID)) {
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login");
                    log.info(ResultEnum.TOKEN_INVALID.getMsg());
                    return false;
                }
            }

            return true;
        }


    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
