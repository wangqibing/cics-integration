package com.ccic.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by 8000600758 on 2018/10/7.
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "q_sys_info")
public class SysInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator="user_gen")
    @TableGenerator(name = "user_gen",
            table="tb_generator",
            pkColumnName="gen_name",
            valueColumnName="gen_value",
            pkColumnValue="SYS_PK",
            allocationSize=1
    )
    private Long id;
    /**
     * 系统中文名称
     */
    @NotNull(message = "系统中文名称不能为空")
    private String sysCname;
    /**
     * 系统英文名称，同系统的consumerID
     */
    @NotNull(message = "系统英文名称不能为空")
    private String sysEname;
    /**
     * 系统开发负责人
     */
    @NotNull(message = "系统开发负责人不能为空")
    private String sysDutyDevNo;

    /**
     * 系统直接负责人
     */
    @NotNull(message = "系统直接负责人不能为空")
    private String sysDutyDirectNo;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
