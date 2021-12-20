package com.lzq.jsyy.order.receiver;

import com.lzq.jsyy.order.service.OrderInfoService;
import com.lzq.jsyy.rabbit.constant.MqConst;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author lzq
 */
@Service
public class OrderInfoReceiver {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 监听每日7点预约提醒消息
     *
     * @param message
     * @param channel
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_TASK_7, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_TASK),
            key = {MqConst.ROUTING_TASK_7}
    ))
    public void orderTips(Message message, Channel channel) {
        orderInfoService.orderTips();
    }

}
