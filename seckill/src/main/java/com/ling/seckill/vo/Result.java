package com.ling.seckill.vo;/**
 * Created by Ky2Fe on 2021/7/26 0:49
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: seckill
 * @description: 统一返回类
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private long code;
    private String message;
    private Object obj;


    /**
     * 成功返回结果
     * @param obj
     * @return
     */
    public static Result success(Object obj){
        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage(),obj);
    }

    /**
     * 成功返回结果
     * @param resultEnum
     * @return
     */
    public static Result success(ResultEnum resultEnum){
        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage(),null);
    }

    /**
     * 失败返回结果
     * @param obj
     * @return
     */
    public static Result error(Object obj){
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage(), obj);
    }

    /**
     * 失败返回结果
     * @param resultEnum
     * @return
     */
    public static Result error(ResultEnum resultEnum){
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage(), null);
    }
}
