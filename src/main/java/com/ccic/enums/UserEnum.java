package com.ccic.enums;

import lombok.Getter;

/**
 * Created by 8000600758 on 2018/10/7.
 */

/**
 * 描述用户类型
 */
@Getter
public enum  UserEnum {
    INNER_USER("100","内部用户"),
    OUT_USER("200","外部用户"),
    MANAGE_USER("0","管理组用户"),
    NALMAL_USER("1","普通用户");

    private String code;
    private String descirbe;

    UserEnum(String code ,String descirbe) {
        this.code = code;
        this.descirbe = descirbe;
    }
}
