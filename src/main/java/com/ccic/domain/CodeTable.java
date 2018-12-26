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
@Table(name = "q_code_table")
public class CodeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator="user_gen")
    @TableGenerator(name = "user_gen",
            table="tb_generator",
            pkColumnName="gen_name",
            valueColumnName="gen_value",
            pkColumnValue="CODE_TABLE_PK",
            allocationSize=1
    )
    private Long id;
    /**
     * Code所描述的区域
     */
    private String codeArea;
    /**
     * code的码值
     */
    private String codeValue;
    /**
     * code中文名称
     */
    private String codeName;
    /**
     * 备注
     */
    private String remark;

}
