package com.ling.seckill.rabbitmq;/**
 * Created by Ky2Fe on 2021/8/7 16:28
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Ky2Fe
 * @program: seckill
 * @description: 消息发送者
 **/

@Service
@Slf4j
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * fanout模式发送消息
     * @param msg
     */
    public void send(Object msg){
        log.info("发送消息："+msg);
        rabbitTemplate.convertAndSend("fanoutExchange","",msg);
    }
}
