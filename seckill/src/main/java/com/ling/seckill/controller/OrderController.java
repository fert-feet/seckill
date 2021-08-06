package com.ling.seckill.controller;


import com.ling.seckill.pojo.User;
import com.ling.seckill.service.IOrderService;
import com.ling.seckill.vo.OrderDetailVo;
import com.ling.seckill.vo.Result;
import com.ling.seckill.vo.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * 订单详情
     * @param user
     * @param orderId
     * @return
     */
    @GetMapping("/detail/{orderId}")
    @ResponseBody
    public Result detail(User user, @PathVariable Integer orderId){
        if (null==user){
            return Result.error(ResultEnum.SESSION_ERROR);
        }
        OrderDetailVo orderDetail = orderService.getOrderDetail(orderId);
        return Result.success(orderDetail);
    }
}
