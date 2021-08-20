package com.ling.seckill.controller;/**
 * Created by Ky2Fe on 2021/7/27 16:29
 */

import com.ling.seckill.exception.GlobalException;
import com.ling.seckill.pojo.Order;
import com.ling.seckill.pojo.SeckillMessage;
import com.ling.seckill.pojo.SeckillOrder;
import com.ling.seckill.pojo.User;
import com.ling.seckill.rabbitmq.MQSender;
import com.ling.seckill.service.IGoodsService;
import com.ling.seckill.service.IOrderService;
import com.ling.seckill.service.ISeckillOrderService;
import com.ling.seckill.utils.JsonUtil;
import com.ling.seckill.vo.GoodsVo;
import com.ling.seckill.vo.Result;
import com.ling.seckill.vo.ResultEnum;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: Ky2Fe
 * @program: seckill
 * @description: 秒杀控制器
 **/
@Slf4j
@Controller
@RequestMapping("/seckill")
public class SecKillController implements InitializingBean {

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MQSender mqSender;

    private Map<Long,Boolean> emptyStockMap =new HashMap<>();

    /**
     * 秒杀
     * windows优化前qps:1037
     * linux优化前qps:1479
     * @param user
     * @param goodsId
     * @return
     */
    @PostMapping("/{path}/doSeckill")
    @ResponseBody
    public  Result doSecKill(User user, Long goodsId, @PathVariable String path){
        if (null==user){
            return Result.error(ResultEnum.SESSION_ERROR);
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        boolean check=orderService.checkPath(user,goodsId,path);
        if (!check){
            return Result.error(ResultEnum.MOBILE_NOT_EXIST);
        }
        SeckillOrder seckillOrder = (SeckillOrder) valueOperations.get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder!=null){
            return Result.error(ResultEnum.REPEAT_ERROR);
        }
        //内存标记，减少redis访问
        if (emptyStockMap.get(goodsId)){
            return Result.error(ResultEnum.EMPTY_STOCK);
        }
        //预减库存操作
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        if (stock<0){
            valueOperations.increment("seckillGoods:" + goodsId);
            emptyStockMap.put(goodsId,true);
            return Result.error(ResultEnum.EMPTY_STOCK);
        }
        SeckillMessage seckillMessage = new SeckillMessage(user,goodsId);
        mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(seckillMessage));
        return Result.success(0);
    }

    /**
     *
     * @param user
     * @param goodsId
     * @return orderId:成功,-1:秒杀失败,0:排队中
     */
    @GetMapping("/result")
    @ResponseBody
    public Result getResult(User user,Long goodsId){
        if (null == user) {
            return Result.error(ResultEnum.SESSION_ERROR);
        }
        Long orderId = seckillOrderService.getResult(user,goodsId);
        return Result.success(orderId);
    }

    @GetMapping("/path")
    @ResponseBody
    public Result getPath(User user,Long goodsId,String captcha){
        if (null==user){
            return Result.error(ResultEnum.SESSION_ERROR);
        }
        boolean checkCaptcha=orderService.captcha(user,goodsId,captcha);
        String path = orderService.createPath(user, goodsId);
        return Result.success(path);
    }

    @GetMapping("/captcha")
    public void captcha(User user, Long goodsId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (null==user||goodsId<0){
            throw new GlobalException(ResultEnum.SESSION_ERROR);
        }
        response.setContentType("image/jpg");
        //设置缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        SpecCaptcha specCaptcha = new SpecCaptcha(132, 32, 3);
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));
        redisTemplate.opsForValue().set("captcha:"+user.getId()+":"+goodsId,specCaptcha.text().toLowerCase(),5, TimeUnit.MINUTES);
        specCaptcha.out(response.getOutputStream());
    }


    /**
     * 系统初始化，把商品库存数量加载到Redis
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.findGoodsVo();
        if (CollectionUtils.isEmpty(goodsList)){
            return;
        }
        goodsList.forEach(goodsVo -> {
            redisTemplate.opsForValue().set("seckillGoods:"+goodsVo.getId(),goodsVo.getStockCount());
            emptyStockMap.put(goodsVo.getId(),false);
        });
    }


}
