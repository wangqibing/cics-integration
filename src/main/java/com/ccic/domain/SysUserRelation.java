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
@Table(name = "q_sys_user_relation")
public class SysUserRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator="user_gen")
    @TableGenerator(name = "user_gen",
            table="tb_generator",
            pkColumnName="gen_name",
            valueColumnName="gen_value",
            pkColumnValue="SYS_USER_PK",
            allocationSize=1
    )
    private Long id;
    /**
     * 系统number
     */
    private String sysNum;
    /**
     * 人员number
     */
    private String userNum;
    /**
     * 密码
     */
    private String sysCname;
    /**
     * 密码
     */
    private String userName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
