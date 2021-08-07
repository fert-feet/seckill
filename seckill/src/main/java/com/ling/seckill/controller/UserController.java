package com.ling.seckill.controller;


import com.ling.seckill.pojo.User;
import com.ling.seckill.rabbitmq.MQSender;
import com.ling.seckill.vo.Result;
import com.ling.seckill.vo.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ky2fe
 * @since 2021-07-26
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MQSender mqSender;

    /**
     * 用户信息(测试用)
     * @param user
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public Result user(User user){
        return Result.success(user);
    }

    /**
     * 测试发送RabbitMQ消息
     */
    @RequestMapping("/mq")
    @ResponseBody
    public void mq(){
        mqSender.send("hello");
    }
}
