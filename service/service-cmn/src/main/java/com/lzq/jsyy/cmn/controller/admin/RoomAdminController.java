package com.lzq.jsyy.cmn.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.cmn.service.RoomService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Room;
import com.lzq.jsyy.vo.cmn.add.RoomAddVo;
import com.lzq.jsyy.vo.cmn.query.RoomQueryVo;
import com.lzq.jsyy.vo.cmn.update.RoomUpdateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 房间
 *
 * @author lzq
 */
@RestController
@RequestMapping("/admin/cmn/room")
@Api(tags = "房间后台管理端API")
public class RoomAdminController {
    @Autowired
    private RoomService roomService;

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
    @ApiOperation(value = "添加房间")
    @PostMapping("/auth/add")
    public Result add(RoomAddVo roomAddVo) {
        Map<String, Object> map = roomService.add(roomAddVo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        map.remove("state");
        return Result.build(map, resultCodeEnum);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{room}")
    })
    @ApiOperation(value = "修改房间信息")
    @PutMapping("/auth/update")
    public Result update(RoomUpdateVo roomUpdateVo) {
        Map<String, Object> map = roomService.change(roomUpdateVo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        map.remove("state");
        return Result.build(map, resultCodeEnum);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{}")
    })
    @ApiOperation(value = "删除房间")
    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = roomService.removeById(id);

        return delete ? Result.ok() : Result.fail();
    }

}
