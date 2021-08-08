package com.ling.seckill.pojo;/**
 * Created by Ky2Fe on 2021/8/8 22:48
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Ky2Fe
 * @program: seckill
 * @description: 秒杀信息
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillMessage {

    private User user;

    private Long goodsId;

}
