package com.ling.seckill.vo;/**
 * Created by Ky2Fe on 2021/7/27 14:46
 */

import com.ling.seckill.pojo.Goods;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Ky2Fe
 * @program: seckill
 * @description: 商品列表
 **/

@Data
public class GoodsVo extends Goods {
    private BigDecimal seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
