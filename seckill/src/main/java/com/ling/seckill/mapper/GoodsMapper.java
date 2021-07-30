package com.ling.seckill.mapper;

import com.ling.seckill.pojo.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ling.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ky2fe
 * @since 2021-07-26
 */
public interface GoodsMapper extends BaseMapper<Goods> {

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
    GoodsVo findGoodsVoByGoodsId(Integer goodsId);
}
