package com.ling.seckill.vo;/**
 * Created by Ky2Fe on 2021/8/3 16:04
 */

import com.ling.seckill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Ky2Fe
 * @program: seckill
 * @description: 商品详情
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVo {
    private User user;

    private GoodsVo goodsVo;

    private int secKillStatus;

    private int remainSeconds;
}
