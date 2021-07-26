package com.ling.seckill.service;

import com.ling.seckill.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.seckill.vo.LoginVo;
import com.ling.seckill.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ky2fe
 * @since 2021-07-26
 */
public interface IUserService extends IService<User> {

    /**
     * 登录
     * @param loginVo
     * @param request
     * @param response
     * @return
     */
    Result doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);
}
