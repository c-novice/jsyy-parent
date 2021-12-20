package com.lzq.jsyy.cmn.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.cmn.service.RoomService;
import com.lzq.jsyy.cmn.service.ScheduleService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Room;
import com.lzq.jsyy.model.cmn.Schedule;
import com.lzq.jsyy.vo.cmn.query.RoomQueryVo;
import com.lzq.jsyy.vo.cmn.query.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/api/cmn/room")
@Api(tags = "房间操作API")
public class RoomApiController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private ScheduleService scheduleService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{records,total,size,current}")
    })
    @ApiOperation(value = "分页条件查询")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, RoomQueryVo roomQueryVo) {
        Page<Room> pageParam = new Page<>(page, limit);
        Page<Room> pageModel = roomService.selectPage(pageParam, roomQueryVo);

        return Result.ok(pageModel);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{room}")
    })
    @ApiOperation(value = "查询一个房间")
    @GetMapping("/auth/get")
    public Result get(String id) {
        Room room = roomService.getById(id);

        if (ObjectUtils.isEmpty(room)) {
            return Result.fail(ResultCodeEnum.ROOM_GET_ERROR);
        } else {
            Page<Schedule> pageParam = new Page<>(1, Integer.MAX_VALUE);
            ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
            scheduleQueryVo.setFacilityId(room.getFacilityId());
            scheduleQueryVo.setRoomId(room.getRoomId());

            room.setSchedules(scheduleService.selectPage(pageParam, scheduleQueryVo).getRecords());
            Map<String, Object> map = new HashMap<>(1);
            map.put("room", room);
            return Result.ok(map);
        }
    }

}
