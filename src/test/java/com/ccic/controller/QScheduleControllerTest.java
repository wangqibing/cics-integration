package com.ccic.controller;

import com.ccic.domain.QSchedule;
import com.ccic.dto.QSchedulePageRequestDto;
import com.ccic.vo.QuestionVo;
import com.ccic.vo.ResultVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 8000600758 on 2018/10/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QScheduleControllerTest {
    @Autowired
    QScheduleController qScheduleController;
    @Test
    public void findByPage() throws Exception {
        QSchedulePageRequestDto qSchedulePageRequestDto = new QSchedulePageRequestDto();
         qSchedulePageRequestDto.setUserid("1000000228");
         PageRequest pageRequest = PageRequest.of(1,5);
        qSchedulePageRequestDto.setPage(2);
        qSchedulePageRequestDto.setSize(5);
        ResultVo result = qScheduleController.findByPage(qSchedulePageRequestDto);
//        qScheduleController.findByPage();
        System.out.println(result);
        
    }

    @Test
    public void login() throws Exception {
    }

}