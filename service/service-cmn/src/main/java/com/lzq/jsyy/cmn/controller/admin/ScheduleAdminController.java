package com.lzq.jsyy.cmn.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.cmn.service.ScheduleService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Schedule;
import com.lzq.jsyy.vo.cmn.add.ScheduleAddVo;
import com.lzq.jsyy.vo.cmn.query.ScheduleQueryVo;
import com.lzq.jsyy.vo.cmn.update.ScheduleUpdateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/admin//cmn/schedule")
@Api(tags = "预约排班后台管理端API")
public class ScheduleAdminController {
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
    @ApiOperation(value = "添加预约排班")
    @PostMapping("/auth/add")
    public Result add(ScheduleAddVo scheduleAddVo) {
        Map<String, Object> map = scheduleService.add(scheduleAddVo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        map.remove("state");
        return Result.build(map, resultCodeEnum);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{schedule}")
    })
    @ApiOperation(value = "修改预约排班信息")
    @PutMapping("/auth/update")
    public Result update(ScheduleUpdateVo scheduleUpdateVo) {
        Map<String, Object> map = scheduleService.change(scheduleUpdateVo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        map.remove("state");
        return Result.build(map, resultCodeEnum);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{}")
    })
    @ApiOperation(value = "删除预约信息")
    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = scheduleService.removeById(id);

        return delete ? Result.ok() : Result.fail();
    }
}
