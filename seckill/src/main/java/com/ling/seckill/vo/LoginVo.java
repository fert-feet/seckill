package com.ling.seckill.vo;/**
 * Created by Ky2Fe on 2021/7/26 16:37
 */

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Ky2Fe
 * @program: seckill
 * @description: 登录
 **/

@Data
public class LoginVo {
    @NotNull
    @Pattern(regexp = "^1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{8}$",message = "请输入正确号码")
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;
}
