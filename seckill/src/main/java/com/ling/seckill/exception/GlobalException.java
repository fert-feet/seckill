package com.ling.seckill.exception;/**
 * Created by Ky2Fe on 2021/7/26 16:49
 */

import com.ling.seckill.vo.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Ky2Fe
 * @program: seckill
 * @description: 全局异常
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException {
    private ResultEnum resultEnum;
}
