package com.ccic.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by 8000600758 on 2018/10/7.
 */
@Data
public class UserInfoVo implements ReturnInterface{
    private Long id;
    private String token;
    private String userName;
    private String userCname;
    private String redirectUrl;
    private List<String> sysIds;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户权限 0-管理员 1-普通用户
     */
    private Integer authority;
}
