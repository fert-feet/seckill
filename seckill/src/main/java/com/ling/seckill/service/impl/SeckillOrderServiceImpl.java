package com.ling.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.seckill.pojo.SeckillOrder;
import com.ling.seckill.mapper.SeckillOrderMapper;
import com.ling.seckill.pojo.User;
import com.ling.seckill.service.ISeckillOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ky2fe
 * @since 2021-07-26
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String IS_STOCK_EMPTY="isStockEmpty";

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     */
    @Override
    public Long getResult(User user, Long goodsId) {
        SeckillOrder seckillOrder = seckillOrderMapper.selectOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_Id", goodsId));
        if (seckillOrder!=null){
            return seckillOrder.getOrderId();
        }else if(redisTemplate.hasKey(IS_STOCK_EMPTY+goodsId)){
            return -1L;
        }else {
            return 0L;
        }
    }
}
