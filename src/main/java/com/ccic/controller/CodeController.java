package com.ccic.controller;

import com.ccic.common.ControllerDep;
import com.ccic.domain.CodeTable;
import com.ccic.service.CodeTableService;
import com.ccic.vo.CodeVo;
import com.ccic.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 8000600758 on 2018/10/16.
 */
@RestController
@RequestMapping(value = "/codetable")
public class CodeController extends ControllerDep{
    @Autowired
    private CodeTableService codeTableService;

    @RequestMapping(value = "/findcodebycodearea")
    public ResultVo findCodeByCodeArea(@RequestBody String codeArea){
        List<CodeTable> codeTables = codeTableService.findByCodeArea(codeArea);
        List<CodeVo> codeVos = new ArrayList<>();
        for (CodeTable codeTable : codeTables){
            CodeVo codeVo = new CodeVo();
            BeanUtils.copyProperties(codeTable,codeVo);
            codeVos.add(codeVo);
        }
        successResponse.setData(codeVos);
        return successResponse;
    }
}
