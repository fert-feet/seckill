package com.ling.seckill.controller;/**
 * Created by Ky2Fe on 2021/7/26 16:30
 */

import com.ling.seckill.pojo.User;
import com.ling.seckill.service.IUserService;
import com.ling.seckill.vo.LoginVo;
import com.ling.seckill.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Ky2Fe
 * @program: seckill
 * @description: 登录
 **/

@Slf4j
@RequestMapping("/login")
@Controller
public class LoginController {

    @Autowired
    private IUserService userService;

    /**
     * 登录跳转界面
     * @return
     */
    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }


    /**
     * 登录
     * @param loginVo
     * @return
     */
    @PostMapping("/doLogin")
    @ResponseBody
    public Result doLogin(@Valid LoginVo loginVo, HttpServletRequest request,HttpServletResponse response){
        log.debug(loginVo.getMobile());
        log.debug(loginVo.getPassword());
        return userService.doLogin(loginVo,request,response);
    }

}
