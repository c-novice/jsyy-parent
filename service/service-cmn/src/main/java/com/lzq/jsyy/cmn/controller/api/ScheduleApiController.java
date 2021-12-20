package com.lzq.jsyy.cmn.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.cmn.service.ScheduleService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Schedule;
import com.lzq.jsyy.vo.cmn.query.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/api/cmn/schedule")
@Api(tags = "预约排班操作API")
public class ScheduleApiController {
    @Autowired
    private ScheduleService scheduleService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{records,total,size,current}")
    })
    @ApiOperation(value = "分页条件查询")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, ScheduleQueryVo scheduleQueryVo) {
        Page<Schedule> pageParam = new Page<>(page, limit);
        Page<Schedule> pageModel = scheduleService.selectPage(pageParam, scheduleQueryVo);

        return Result.ok(pageModel);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{schedule}")
    })
    @ApiOperation(value = "查询一个预约排班")
    @GetMapping("/auth/get")
    public Result get(String id) {
        Schedule schedule = scheduleService.getById(id);

        if (ObjectUtils.isEmpty(schedule)) {
            return Result.fail(ResultCodeEnum.SCHEDULE_GET_ERROR);
        } else {
            Map<String, Object> map = new HashMap<>(1);
            map.put("schedule", schedule);
            return Result.ok(map);
        }
    }

    @ApiIgnore()
    @ApiOperation("根据id查询一个预约排班")
    @GetMapping("/inner/getById")
    public Schedule getById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        return scheduleService.getById(id);
    }

    @ApiIgnore()
    @ApiOperation("更新预约记录：可预约->已被预约")
    @GetMapping("/inner/updateOrdered")
    public void updateOrdered(String scheduleId) {
        Schedule schedule = scheduleService.getById(scheduleId);
        if (!ObjectUtils.isEmpty(schedule)) {
            schedule.setIsOrdered(1);
            scheduleService.updateById(schedule);
        }
    }
}