package com.ccic.util;

import com.ccic.domain.QSchedule;
import com.ccic.domain.UserInfo;
import com.ccic.vo.CodeVo;
import com.ccic.vo.QuestionVo;
import com.ccic.vo.ScheduleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 8000600758 on 2018/10/18.
 */
public class DataParseUtil {
    private DataParseUtil(){}

    public static List<ScheduleVo> qSchedules2questionvos(List<QSchedule> qSchedules){
        return qSchedules.stream().map(e->qSchedule2questionvo(e)).collect(Collectors.toList());
    }

    public static ScheduleVo qSchedule2questionvo(QSchedule qSchedule){
        ScheduleVo scheduleVo = new ScheduleVo();
        BeanUtils.copyProperties(qSchedule,scheduleVo);
        return scheduleVo;
    }

    public static List<CodeVo> userInfo2CodeVo(List<UserInfo> userInfos){
        return userInfos.stream().map(e->new CodeVo(e.getUserCname(),e.getId().toString())).collect(Collectors.toList());
    }
}
