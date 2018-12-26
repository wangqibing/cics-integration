package com.ccic.vo;

import com.ccic.domain.QSchedule;
import com.ccic.domain.SysInfo;
import com.ccic.domain.SysUserRelation;
import com.ccic.domain.UserInfo;
import lombok.Data;

import java.util.List;

/**
 * Created by 8000600758 on 2018/9/29.
 */
@Data
public class QuestionVo implements ReturnInterface{
    private List<SysVo> sysInfoVos;
    private List<QSchedule> qSchedules;
    private List<UserInfoVo> userInfoVos;
    private List<SysUserRelation> sysUserRelations;
}
