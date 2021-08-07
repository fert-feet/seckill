package com.ling.seckill.rabbitmq;/**
 * Created by Ky2Fe on 2021/8/7 16:30
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author: Ky2Fe
 * @program: seckill
 * @description: 消息接受者
 **/

@Service
@Slf4j
public class MQReceiver {

    @RabbitListener(queues = "queue")
    public void recevier(Object msg){
        log.info("接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_fanout01")
    public void recevier01(Object msg){
        log.info("queue01接收消息："+ msg);
    }

    @RabbitListener(queues = "queue_fanout02")
    public void recevier02(Object msg){
        log.info("queue02接收消息："+ msg);
    }
}
