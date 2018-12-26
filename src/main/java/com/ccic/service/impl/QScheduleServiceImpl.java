package com.ccic.service.impl;

import com.ccic.domain.QSchedule;
import com.ccic.dto.QSchedulePageRequestDto;
import com.ccic.repository.QScheduleRepository;
import com.ccic.service.QScheduleService;
import com.ccic.util.KeyUtil;
import com.ccic.vo.ScheduleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by 8000600758 on 2018/9/29.
 */
@Service
@Slf4j
public class QScheduleServiceImpl implements QScheduleService {

    @Autowired
    private QScheduleRepository qScheduleRepository;

    @Override
    public QSchedule insertOne(QSchedule question) {
        //生成主键
        String idKey = KeyUtil.getUniqueKey();
        question.setId(idKey);
        //保存
        QSchedule qSchedule = qScheduleRepository.save(question);

        return qSchedule;
    }

    @Override
    @Transactional
    public boolean saveList(List<QSchedule> qSchedules) {
        for (QSchedule qSchedule:qSchedules){
            if(null == insertOne(qSchedule)){
                return false;
            }
        }
        return   true;
    }

    @Override
    public Page<QSchedule> findAll(Pageable pageable) {
        return qScheduleRepository.findAll(pageable);
    }

    @Override
    public Page<QSchedule> findByPage(QSchedulePageRequestDto qSchedulePageRequestDto) {
        Pageable pageable = null;
        if(0 == qSchedulePageRequestDto.getPageFlag()){ //默认分页查询
            pageable = PageRequest.of(qSchedulePageRequestDto.getPage()-1,qSchedulePageRequestDto.getSize());
        }
        return  qScheduleRepository.findByPage(qSchedulePageRequestDto.getUserid(),qSchedulePageRequestDto.getUserSys(),qSchedulePageRequestDto.getStatus(),qSchedulePageRequestDto.getPresentTime(),qSchedulePageRequestDto.getBizNos(),pageable);
    }

    @Override
    public ScheduleVo findById(String id) {
        Optional<QSchedule> optional = qScheduleRepository.findById(id);
        if(optional.isPresent()){
            ScheduleVo scheduleVo = new ScheduleVo();
            QSchedule qSchedule = optional.get();
            BeanUtils.copyProperties(qSchedule,scheduleVo);
            return scheduleVo;
        }
        return null;
    }

    @Override
    public Page<QSchedule> findByIdPage(List bizNos, Pageable pageable) {
        return  qScheduleRepository.findByIdPage(bizNos,pageable);

    }
}
