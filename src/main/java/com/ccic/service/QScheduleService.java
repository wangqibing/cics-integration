package com.ccic.service;

import com.ccic.domain.QSchedule;
import com.ccic.dto.QSchedulePageRequestDto;
import com.ccic.vo.ScheduleVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by 8000600758 on 2018/9/29.
 */
public interface QScheduleService {
    /** 插入单挑记录*/
    QSchedule insertOne(QSchedule question);

    boolean saveList(List<QSchedule> qSchedules);

    /**
     * 查询问题列表
     */
    Page<QSchedule> findAll(Pageable pageable);

    /**
     * 通过问题提出人，和相关信息进行问题查询
     */
    Page<QSchedule> findByPage(QSchedulePageRequestDto qSchedulePageRequestDto);

    /**
     * 查询一条问题记录
     */
    ScheduleVo findById(String id);

    /**
     * 通过问题的id列表查询问题
     */
    Page<QSchedule> findByIdPage(List bizNos,Pageable pageable);

}
