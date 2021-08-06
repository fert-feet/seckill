package com.ling.seckill.controller;/**
 * Created by Ky2Fe on 2021/7/27 16:29
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.seckill.pojo.Order;
import com.ling.seckill.pojo.SeckillOrder;
import com.ling.seckill.pojo.User;
import com.ling.seckill.service.IGoodsService;
import com.ling.seckill.service.IOrderService;
import com.ling.seckill.service.ISeckillOrderService;
import com.ling.seckill.vo.GoodsVo;
import com.ling.seckill.vo.Result;
import com.ling.seckill.vo.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Ky2Fe
 * @program: seckill
 * @description: 秒杀控制器
 **/
@Slf4j
@Controller
@RequestMapping("/seckill")
public class SecKillController {

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 秒杀
     * windows优化前qps:1037
     * linux优化前qps:199
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @PostMapping("/doSeckill2")
    public String doSecKill2(Model model, User user,Long goodsId){
        if (null==user){
            return "login";
        }
        model.addAttribute("user",user);
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存
        if (goods.getStockCount()<1){
            model.addAttribute("errmsg", ResultEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        }
        //判断重复抢购
        SeckillOrder seckillOrder=seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id",user.getId()).eq("goods_id",goodsId));
        if (seckillOrder!=null){
            model.addAttribute("errmsg", ResultEnum.REPEAT_ERROR.getMessage());
            return "secKillFail";
        }
        Order order=orderService.secKill(user,goods);
        model.addAttribute("order",order);
        model.addAttribute("goods",goods);
        return "orderDetail";
    }

    /**
     * 秒杀
     * windows优化前qps:1037
     * linux优化前qps:1479
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @PostMapping("/doSeckill")
    @ResponseBody
    public Result doSecKill(Model model, User user, Long goodsId){
        if (null==user){
            return Result.error(ResultEnum.SESSION_ERROR);
        }
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存
        if (goods.getStockCount()<1){
            return Result.error(ResultEnum.EMPTY_STOCK);
        }
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goods.getId());
        //判断重复抢购
        if (seckillOrder!=null){
            return Result.error(ResultEnum.REPEAT_ERROR);
        }
        Order order=orderService.secKill(user,goods);
        return Result.success(order);
    }
}
