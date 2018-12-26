package com.ccic.dto;

import com.ccic.domain.QSchedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by 8000600758 on 2018/9/29.
 */
@Data
public class QSchedulePageRequestDto{
    private Integer page =1;
    private Integer size = 10;
    private String userid;
    private String userSys;
    private Integer status;
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date presentTime;
    private Integer pageFlag = 0; //默认为0开启分页
    private List<String> bizNos;
}
