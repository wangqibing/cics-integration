package com.ccic.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * Created by 8000600758 on 2018/10/19.
 */
@Data
public class ScheduleVo {
    private String id;
    /** 问题提出系统编号-必填 */
    private String questionSys;
    /** 问题提出系统-必填 */
    private String questionSysCname;
    /** 问题提出人编号-必填 */
    private String questionPerson;
    /** 问题提出人-必填 */
    private String questionPersonCname;
    /** 问题的解决验证状态 0-不通过 1-通过 3-待验证 */
    private Integer questionValideStatus;
    /** 问题解决系统编号-必填 */
    private String resolveSys;
    /** 问题解决系统-必填 */
    private String resolveSysCname;
    /** 问题相关接口-必填*/
    private String resolveInterface;
    /** 问题简单描述 */
    private String questionDescrbe;
    /** 问题当前处理状态  0-已解决/1-解决中/2-未解决/3-待确认*/
    private Integer resolveStatus;
    /** 问题相关责任人编号*/
    private String resolveDutyPerson;
    /** 问题相关责任人 */
    private String resolveDutyPersonCname;
    /** 问题依赖人编号*/
    private String resolveDependenPerson;
    /** 问题依赖人 */
    private String resolveDepPersonCname;
    /** 问题优先级 0-低优先级，1-中优先级，2-高优先级 */
    private int resolvePriority;
    /** 问题的处理反馈 */
    private String resolveBack;
    /** 问题的提出时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /** 问题的更新时间 */
    private Date prepareResolveTime;
    /** 备注 */
    private String remark;
    /** taskID */
    private String taskId;
}
