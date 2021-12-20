package com.lzq.jsyy.cmn.client;

import com.lzq.jsyy.model.cmn.Schedule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 预约排班调用
 *
 * @author lzq
 */
@Component
@FeignClient("service-cmn")
public interface ScheduleFeignClient {
    /**
     * 根据id查询预约排班
     *
     * @param id
     * @return
     */
    @GetMapping("/api/cmn/schedule/inner/getById")
    Schedule getById(@RequestParam String id);

    /**
     * 更新预约记录：可预约->已被预约
     *
     * @param scheduleId
     */
    @GetMapping("/api/cmn/schedule/inner/updateOrdered")
    void updateOrdered(@RequestParam String scheduleId);
}
