package com.ling.seckill.exception;/**
 * Created by Ky2Fe on 2021/7/26 16:50
 */

import com.ling.seckill.vo.Result;
import com.ling.seckill.vo.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * @author: Ky2Fe
 * @program: seckill
 * @description: 全局异常处理类
 **/

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getResultEnum());
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            Result respBean = Result.error(ResultEnum.BIND_ERROR);
            respBean.setMessage("参数校验异常：" + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        }
        return Result.error(ResultEnum.ERROR);
    }

}
