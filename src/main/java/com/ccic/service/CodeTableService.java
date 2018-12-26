package com.ccic.service;

import com.ccic.domain.CodeTable;
import com.ccic.domain.QSchedule;
import com.ccic.vo.CodeVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by 8000600758 on 2018/9/29.
 */
public interface CodeTableService {
    List<CodeTable> findByCodeArea(String codeArea);
}
