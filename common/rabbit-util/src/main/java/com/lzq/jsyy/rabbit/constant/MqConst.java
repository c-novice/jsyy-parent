package com.lzq.jsyy.rabbit.constant;

/**
 * mq的常量定义
 *
 * @author lzq
 */
public class MqConst {
    /**
     * 发送短信
     */
    public static final String EXCHANGE_DIRECT_MSM = "exchange.direct.msm";
    public static final String ROUTING_MSM_ITEM = "msm.item";
    public static final String QUEUE_MSM_ITEM = "queue.msm.item";

    /**
     * 预约提醒
     */
    public static final String EXCHANGE_DIRECT_TASK = "exchange.direct.task";
    public static final String ROUTING_TASK_7 = "task.7";
    public static final String QUEUE_TASK_7 = "queue.task.7";

}
