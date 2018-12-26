package com.ccic.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by 8000600758 on 2018/10/6.
 */
@Data
public class PageVo {
    private Integer page = 1;
    private Integer size = 10;
    private String userId;
    private Integer status = 2;
    private Date presentTime;
    private String sys;
}
