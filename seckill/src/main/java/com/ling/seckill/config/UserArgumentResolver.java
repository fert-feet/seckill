package com.ling.seckill.config;/**
 * Created by Ky2Fe on 2021/7/27 14:53
 */

import com.ling.seckill.pojo.User;
import com.ling.seckill.service.IUserService;
import com.ling.seckill.utils.CookieUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Ky2Fe
 * @program: seckill
 * @description: 自定义用户配置
 **/

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private IUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz=methodParameter.getParameterType();
        return clazz == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        HttpServletResponse response = (HttpServletResponse) nativeWebRequest.getNativeResponse();
        String userTicket = CookieUtil.getCookieValue(request, "userTicket");
        if (!StringUtils.hasLength(userTicket)){
            return null;
        }
        return userService.getUserByCookie(request,response,userTicket);
    }
}
