package com.ling.seckill.config;/**
 * Created by Ky2Fe on 2021/8/7 16:25
 */

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author: Ky2Fe
 * @program: seckill
 * @description: 消息队列配置
 **/

@Configuration
public class RabbitMqConfig {

    private static final String QUEUE="seckillQueue";
    private static final String EXCHANGE="secKillExchange";
    private static final String ROUTING_KEY="seckill.#";


    @Bean
    public Queue queue(){
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange seckillExchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(seckillExchange()).with(ROUTING_KEY);
    }


}
