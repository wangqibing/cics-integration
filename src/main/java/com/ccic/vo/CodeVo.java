package com.ccic.vo;

import lombok.Data;

/**
 * Created by 8000600758 on 2018/10/16.
 */
@Data
public class CodeVo implements ReturnInterface{
    public CodeVo(){}
    public CodeVo(String codeName,String codeValue){
        this.codeName = codeName;
        this.codeValue = codeValue;
    }
    private String codeName;
    private String codeValue;
}
