package com.ling.seckill.vo;/**
 * Created by Ky2Fe on 2021/8/5 17:12
 */

import com.ling.seckill.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Ky2Fe
 * @program: seckill
 * @description: 订单详情
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {
    private GoodsVo goodsVo;

    private Order order;
}
