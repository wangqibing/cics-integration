package com.ccic.exception;

import com.ccic.enums.ResultEnum;

/**
 * Created by 8000600758 on 2018/10/7.
 */
public class IntegrationException extends RuntimeException{

    private Integer code;

    public IntegrationException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public IntegrationException(Integer errorCode,String message){
        super(message);
        this.code = errorCode;
    }

}
