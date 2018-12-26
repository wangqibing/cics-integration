package com.ccic.enums;

import lombok.Getter;

/**
 * Created by 8000600758 on 2018/9/29.
 */
@Getter
public enum QScheduleEnum {
    QUESTIONSYS("系统"), QUESTIONPERSON("问题提出人"),
    RESOLVESYS("核心条线"), RESOLVEINTERFACE("接口"), QUESTIONDESCRBE("联调中的问题"), RESOLVESTATUS("核心解决情况"), RESOLVEDUTYPERSON("责任人"), RESOLVEDEPENDENPERSON("依赖人员"),
    RESOLVEPRIORITY("优先级"), RESOLVEBACK("核心反馈（反馈人：内容）"), CREATETIME("提出时间"), PREPARERESOLVETIME("预计解决时间"), REMARK("备 注");

    private String descirbe;
    QScheduleEnum(String descirbe) {
        this.descirbe = descirbe;
    }
}
