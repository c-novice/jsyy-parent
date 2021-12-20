package com.lzq.yygh.task.scheduled;


import com.lzq.jsyy.rabbit.constant.MqConst;
import com.lzq.jsyy.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * @author lzq
 */
@Component
@EnableScheduling
public class ScheduledTask {

    @Autowired
    private RabbitService rabbitService;

    /**
     * 预约日期7点发送
     */
    @Scheduled(cron = "0 0 7 * * ?")
    public void taskPatient() {
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK, MqConst.ROUTING_TASK_7, "");
    }

}
