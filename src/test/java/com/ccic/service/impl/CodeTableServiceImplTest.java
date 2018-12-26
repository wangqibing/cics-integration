package com.ccic.service.impl;

import com.ccic.domain.CodeTable;
import com.ccic.repository.CodeTableRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 8000600758 on 2018/10/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeTableServiceImplTest {
    @Autowired
    private CodeTableRepository codeTableRepository;

    @Test
//    @Transactional
    public void findByCodeArea() throws Exception {

        //0-已解决/1-解决中/2-未解决/3-待确认 question-resolve-status
        List<CodeTable> codeTables = new ArrayList<>();
        for(int i=0;i<3;i++){
            CodeTable codeTable = new CodeTable();
            codeTable.setCodeArea("questionValideStatus"); //0-不通过 1-通过 2-待验证 */
            codeTable.setCodeValue(i+"");
            if(i == 0){
                codeTable.setCodeName("不通过");
            }else if(i == 1){
                codeTable.setCodeName("通过");
            }else if(i == 2){
                codeTable.setCodeName("待验证");
            }else{
                continue;
            }
            codeTables.add(codeTable);
        }
        codeTableRepository.saveAll(codeTables);
    }

}