package com.ling.seckill.service.impl;

import com.ling.seckill.exception.GlobalException;
import com.ling.seckill.pojo.User;
import com.ling.seckill.mapper.UserMapper;
import com.ling.seckill.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.seckill.utils.CookieUtil;
import com.ling.seckill.utils.MD5Util;
import com.ling.seckill.utils.UUIDUtil;
import com.ling.seckill.vo.LoginVo;
import com.ling.seckill.vo.Result;
import com.ling.seckill.vo.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ky2fe
 * @since 2021-07-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 登录
     * @param loginVo
     * @param request
     * @param response
     * @return
     */
    @Override
    public Result doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile=loginVo.getMobile();
        String password=loginVo.getPassword();
        User user=userMapper.selectById(mobile);
        //根据手机号获取用户
        if (null==user){
            throw new GlobalException(ResultEnum.LOGIN_ERROR);
        }
        //判断密码是否正确
        if (!MD5Util.formPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            throw new GlobalException(ResultEnum.LOGIN_ERROR);
        }
        //生成cookie
        String ticket = UUIDUtil.uuid();
        redisTemplate.opsForValue().set("user:"+ticket,user);
        CookieUtil.setCookie(request,response,"userTicket",ticket);
        return Result.success(ticket);
    }

    /**
     * 自定义用户配置
     * @param request
     * @param response
     * @param userTicket
     * @return
     */
    @Override
    public User getUserByCookie(HttpServletRequest request, HttpServletResponse response, String userTicket) {
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user!=null){
            CookieUtil.setCookie(request,response,"userTicket",userTicket);
        }
        return user;
    }

    /**
     * 更新密码
     * @param userTicket
     * @param password
     * @param request
     * @param response
     * @return
     */
    @Override
    public Result updatePassword(String userTicket, String password, HttpServletRequest request, HttpServletResponse response) {
        User user=getUserByCookie(request,response, userTicket);
        if (null==user){
            throw new GlobalException(ResultEnum.MOBILE_NOT_EXIST);
        }
        user.setPassword(MD5Util.inputPassToDBPass(password,user.getSalt()));
        int result = userMapper.updateById(user);
        if (1 == result) {
            //删除redis
            redisTemplate.delete("user:"+ userTicket);
            return Result.success();
        }
        return Result.error(ResultEnum.UPDATE_PASSWORD_FAILE);
    }
}
