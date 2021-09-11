#### 页面优化

页面优化部分，如果项目开始即使用前后端分离模式，便不用进行缓存设计，页面优化部分基本上是将后端需要传送的数据放入redis缓存，使前端页面加载更加迅速



#### 接口优化

1. redis预减库存+内存标记减少redis访问

```java
private Map<Long,Boolean> emptyStockMap =new HashMap<>();
//内存标记，减少redis访问
if (emptyStockMap.get(goodsId)){
    return Result.error(ResultEnum.EMPTY_STOCK);
}
//预减库存操作
Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
if (stock<0){
    valueOperations.increment("seckillGoods:" + goodsId);
    emptyStockMap.put(goodsId,true);
    return Result.error(ResultEnum.EMPTY_STOCK);
}
```

2.集成rabbitmq消息队列异步下单（采用topic模式）

RabbitMqConfig.java

```java
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
```

MQSender.java

```java
@Autowired
private RabbitTemplate rabbitTemplate;

public void sendSeckillMessage(String message){
    log.info("发送消息"+message);
    rabbitTemplate.convertAndSend("secKillExchange","seckill.message",message);
}
```

MQReceiver

```java
@RabbitListener(queues = "seckillQueue")
public void receiver(String message){
    log.info("接收消息：" + message);
    SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(message, SeckillMessage.class);
    Long goodsId = seckillMessage.getGoodsId();
    User user = seckillMessage.getUser();
    GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
    if (goodsVo.getStockCount()<1){
        return;
    }
    SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
    if (seckillOrder!=null){
        return;
    }
    //下单操作
    orderService.secKill(user,goodsVo);
}
```



#### 安全优化

1.秒杀接口地址隐藏

> 基本上就是原来的秒杀post操作的地址变成获取post操作地址的地址

创建地址

```java
/**
 *  创建地址
 * @param user
 * @param goodsId
 * @return
 */
@Override
public String createPath(User user, Long goodsId) {
    String str = MD5Util.md5(UUIDUtil.uuid() + "123456");
    redisTemplate.opsForValue().set("seckillPath:"+user.getId()+":"+goodsId,str,60, TimeUnit.SECONDS);
    return str;
}
```

地址校验

```java
/**
 * 接口地址校验
 * @param user
 * @param goodsId
 * @param path
 * @return
 */
@Override
public boolean checkPath(User user, Long goodsId, String path) {
    if (null==user||goodsId<0|| !StringUtils.hasLength(path)){
        return false;
    }
    String redisPath = (String) redisTemplate.opsForValue().get("seckillPath:" + user.getId() + ":" + goodsId);
    return path.equals(redisPath);
}
```

> 以上操作的地址都存储在redis中



2. 验证码

主要是生成验证码并通过验证才能进行秒杀，验证码可以在github上或者码云上找



#### 总结

成熟的秒杀解决方案应该是高可用高并发的，其中主要解决高并发产生的延迟和库存超算的问题，数据库设计和redis缓存是一般解决中非常重要的部分，RabbitMQ消息对列可以大大提高系统应答速度，使前端更加友好，并大大提高后端性能
