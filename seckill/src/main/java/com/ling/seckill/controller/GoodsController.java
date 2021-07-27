package com.ling.seckill.controller;


import com.ling.seckill.pojo.User;
import com.ling.seckill.service.IGoodsService;
import com.ling.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ky2fe
 * @since 2021-07-26
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    /**
     * 商品列表页
     * @param model
     * @param user
     * @return
     */
    @GetMapping("/toList")
    public String toList(Model model, User user){
        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsService.findGoodsVo());
        return "goodsList";
    }

    /**
     * 商品详情页
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @GetMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable Integer goodsId){
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goods.getStartDate();
        Date endDate = goods.getEndDate();
        Date currentDate = new Date();
        //秒杀状态
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        //秒杀还未开始
        if (currentDate.before(startDate)) {
            remainSeconds = ((int) ((startDate.getTime() - currentDate.getTime()) / 1000));
        } else if (currentDate.after(endDate)) {
            //	秒杀已结束
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀中
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("user",user);
        model.addAttribute("goods",goods);
        model.addAttribute("secKillStatus",secKillStatus);
        model.addAttribute("remainSeconds",remainSeconds);

        return "goodsDetail";
    }



}
