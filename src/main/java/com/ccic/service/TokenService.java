package com.ccic.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ccic.domain.UserInfo;
import com.ccic.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author jinbin
 * @date 2018-07-08 21:04
 */
@Slf4j
@Service("TokenService")
public class TokenService {

    public String getToken(UserInfo user) {
        String token="";
        token= JWT.create().withClaim("expMillis",System.currentTimeMillis()+60*60*1000)
                .withAudience(user.getUserName())// 将 user id 保存到 token 里面
                .sign(Algorithm.HMAC256(user.getPassword()));// 以 password 作为 token 的密钥
        return token;
    }

    public ResultEnum verifyToken(UserInfo user, String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            if(claims.get("expMillis").asLong() - System.currentTimeMillis() < 0){
                return ResultEnum.TOKEN_INVALID;  //token失效
            }
        }catch(Exception e){
            log.error(e.getMessage());
            return ResultEnum.TOKEN_ERROR; //验证不通过
        }
        return ResultEnum.TOKEN_SUCCESS;  //验证通过
    }

//    public static void main(String[] args){
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserName("wangqibing");
//        userInfo.setPassword("wangqibing");
//        TokenService tokenService = new TokenService();
//        String token = tokenService.getToken(userInfo);
//        System.out.println(token);
//
////        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ3YW5ncWliaW5nIiwiZXhwTWlsbGlzIjoxNTM5MDc0MzQwNjU5fQ.XAf02R6Alcv5nm9I2AplFw5RIJU-gzbV0fG3MU6vXho";
//        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("wangqibing")).build();
//        DecodedJWT jwt = verifier.verify(token);
//        Map<String, Claim> claims = jwt.getClaims();
//        System.out.println(claims.get("expMillis").asLong());
//
//
//    }
}
