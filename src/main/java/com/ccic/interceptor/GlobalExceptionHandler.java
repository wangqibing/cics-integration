package com.ccic.interceptor;

import com.ccic.enums.ResultEnum;
import com.ccic.exception.IntegrationException;
import com.ccic.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jinbin
 * @date 2018-07-08 22:37
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理所有不可知的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResultVo handleException(Exception e){
        LOGGER.error(e.getMessage(), e);
        return ResultVo.error();
    }

    /**
     * 处理所有业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(IntegrationException.class)
    @ResponseBody
    ResultVo handleBusinessException(IntegrationException e){
        LOGGER.error(e.getMessage(), e);
        ResultVo response =ResultVo.error();
        response.setMsg(e.getMessage());
        return response;
    }

    /**
     * 处理所有接口数据验证异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ResultVo handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        LOGGER.error(e.getMessage(), e);
        ResultVo response =ResultVo.error();
        response.setMsg(e.getMessage());
        response.setMsg(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return response;
    }
}
