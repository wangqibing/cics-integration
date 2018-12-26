package com.ccic.vo;

import com.ccic.enums.ResultEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * Created by 8000600758 on 2018/10/7.
 */
@Data
public class ResultVo<T> {

    private Integer code = 0;
    private String msg;
    private  T data;

    public static ResultVo success(ResultEnum msg){
        ResultVo resultVo = new ResultVo();
        resultVo.setMsg(msg.getMsg());
        resultVo.setCode(msg.getCode());
        return resultVo;
    }

    public static ResultVo success(){
        return success(ResultEnum.SUCCESS);
    }
    public static ResultVo successWithData(Object data){
        ResultVo resultVo= success(ResultEnum.SUCCESS);
        resultVo.setData(data);
        return resultVo;
    }

    public static ResultVo error(ResultEnum msg){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(1);
        resultVo.setMsg(msg.getMsg());
        return resultVo;
    }

    public static ResultVo error(){
        return success(ResultEnum.ERROR);
    }
}
