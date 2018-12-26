package com.ccic.Filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ccic.domain.UserInfo;
import com.ccic.enums.ResultEnum;
import com.ccic.exception.IntegrationException;
import com.ccic.service.TokenService;
import com.ccic.service.UserInfoService;
import com.ccic.util.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 8000600758 on 2018/10/11.
 */
@WebFilter(urlPatterns = "/integration/*", filterName = "myfilter") //放行地址配置
public class UserFilter implements Filter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/integration/index","/integration/login")));

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    private TokenService tokenService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = ALLOWED_PATHS.contains(path);

        if (allowedPath) {
//            System.out.println("这里是不需要处理的url进入的方法");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String redirect = httpServletRequest.getHeader("redirect");// 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("Token");// 从 http 请求头中取出 token
        if ("XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With")) && "REDIRECT".equals(redirect)) {
            ReflectUtil.reflectSetparam(httpServletRequest,"redirect","none");
            String redirectUrl = httpServletRequest.getHeader("RedirectUrl");
            if (token == null || "".equals(token)) {
                httpServletResponse.sendRedirect("/index");
            } else {
                httpServletResponse.setHeader("Token",token);
//                httpServletRequest.getRequestDispatcher(httpServletRequest.getContextPath()+httpServletRequest.getRequestURI()).forward(httpServletRequest,httpServletResponse);
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+httpServletRequest.getRequestURI());
//                filterChain.doFilter(servletRequest, servletResponse); //重定向
            return;
            }
        } else {
            if (null == token || "".equals(token)) {
                httpServletResponse.sendRedirect("/integration/index");
                return;
            }
            // 获取 token 中的 user id
            String userName;
            try {
                userName = JWT.decode(token).getAudience().get(0);
            } catch (JWTDecodeException j) {
                httpServletRequest.getRequestDispatcher("/index").forward(httpServletRequest, httpServletResponse);
                throw new RuntimeException("401");
            }
            UserInfo loginUser = new UserInfo();
            loginUser.setUserName(userName);
            UserInfo user = userInfoService.findOne(loginUser);
            if (user == null) {
                httpServletRequest.getRequestDispatcher("/index").forward(httpServletRequest, httpServletResponse);
                throw new RuntimeException("用户不存在，请重新登录");
            }
            // 验证 token

            if (tokenService.verifyToken(user, token).equals(ResultEnum.TOKEN_ERROR)) {
                throw new IntegrationException(ResultEnum.TOKEN_ERROR);
            } else if (tokenService.verifyToken(user, token).equals(ResultEnum.TOKEN_INVALID)) {
                httpServletRequest.getRequestDispatcher("/index").forward(httpServletRequest, httpServletResponse);
                throw new IntegrationException(ResultEnum.TOKEN_INVALID);
            }
        }
//        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
