package com.ccic.service.impl;

import com.ccic.domain.CodeTable;
import com.ccic.repository.CodeTableRepository;
import com.ccic.service.CodeTableService;
import com.ccic.vo.CodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.util.List;

/**
 * Created by 8000600758 on 2018/9/29.
 */
@Service
public class CodeTableServiceImpl implements CodeTableService{
    @Autowired
    private CodeTableRepository codeTableRepository;

    @Override
    public List<CodeTable> findByCodeArea(String codeArea) {
        return codeTableRepository.findByCodeArea(codeArea);
    }
}
