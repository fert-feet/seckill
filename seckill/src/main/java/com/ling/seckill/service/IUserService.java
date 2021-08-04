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


    /**
     * 自定义用户配置
     * @param request
     * @param response
     * @param userTicket
     * @return
     */
    User getUserByCookie(HttpServletRequest request, HttpServletResponse response, String userTicket);

    /**
     * 更新密码
     * @param userTicket
     * @param password
     * @param request
     * @param response
     * @return
     */
    Result updatePassword(String userTicket,String password,HttpServletRequest request,HttpServletResponse response);
}
