package com.ling.seckill.service;

import com.ling.seckill.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.seckill.pojo.User;
import com.ling.seckill.vo.GoodsVo;
import com.ling.seckill.vo.OrderDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ky2fe
 * @since 2021-07-26
 */
public interface IOrderService extends IService<Order> {

    /**
     * 秒杀
     * @param user
     * @param goods
     * @return
     */
    Order secKill(User user, GoodsVo goods);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderDetailVo getOrderDetail(Integer orderId);

    /**
     * 创建地址
     * @param user
     * @param goodsId
     * @return
     */
    String createPath(User user, Long goodsId);


    /**
     * 地址检查
     * @param user
     * @param goodsId
     * @param path
     * @return
     */
    boolean checkPath(User user, Long goodsId, String path);

    /**
     * 校验验证码
     * @param user
     * @param goodsId
     * @param captcha
     * @return
     */
    boolean captcha(User user, Long goodsId, String captcha);
}
