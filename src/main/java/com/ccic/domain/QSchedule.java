package com.ccic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by 8000600758 on 2018/9/29.
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "q_schedule")
public class QSchedule{
    @Id
    private String id;
    /** 问题提出系统编号-必填 */
    private String questionSys; 
    /** 问题提出系统中文-必填 */
    private String questionSysCname;
    /** 问题提出人编号-必填 */
    private String questionPerson;
    /** 问题提出人姓名-必填 */
    private String questionPersonCname;
    /** 问题的解决验证状态 0-不通过 1-通过 2-待验证 */
    private Integer questionValideStatus;
    /** 问题解决系统编号-必填 */
    private String resolveSys;
    /** 问题解决系统中文名称-必填 */
    private String resolveSysCname;
    /** 问题相关接口-必填*/
    private String resolveInterface;
    /** 问题简单描述 */
    private String questionDescrbe;
    /** 问题当前处理状态  0-已解决/1-解决中/2-未解决/3-待确认*/
    private Integer resolveStatus;
    /** 问题相关责任人编号*/
    private String resolveDutyPerson;
    /** 问题相关责任人中文名称*/
    private String resolveDutyPersonCname;
    /** 问题依赖人编号*/
    private String resolveDependenPerson;
    /** 问题依赖人中文名称*/
    private String resolveDepPersonCname;
    /** 问题优先级 0-低优先级，1-中优先级，2-高优先级 */
    private int resolvePriority;
    /** 问题的处理反馈 */
    private String resolveBack;
    /** 问题的提出时间 */
    private Date createTime;
    /** 问题的更新时间 */
    private Date updateTime;
    /** 问题预计解决时间 */
    private Date prepareResolveTime;
    /** 备注 */
    private String remark;
    /** 备注 */
    private String taskId;
}
