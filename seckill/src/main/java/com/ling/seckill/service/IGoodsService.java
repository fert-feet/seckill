package com.ling.seckill.service;

import com.ling.seckill.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ky2fe
 * @since 2021-07-26
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();


    /**
     * 商品详情
     * @param goodsId
     * @return
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
