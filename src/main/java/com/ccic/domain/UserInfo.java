package com.ccic.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 8000600758 on 2018/10/7.
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "q_user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator="user_gen")
    @TableGenerator(name = "user_gen",
            table="tb_generator",
            pkColumnName="gen_name",
            valueColumnName="gen_value",
            pkColumnValue="USER_PK",
            allocationSize=1
    )
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String userCname;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户权限 0-管理员 1-普通用户
     */
    private Integer authority;
    /**
     * 用户状态 0-有效 1-无效
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
