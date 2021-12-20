package com.lzq.jsyy.cmn.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.cmn.service.FacilityService;
import com.lzq.jsyy.cmn.service.RoomService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Facility;
import com.lzq.jsyy.model.cmn.Room;
import com.lzq.jsyy.vo.cmn.query.FacilityQueryVo;
import com.lzq.jsyy.vo.cmn.query.RoomQueryVo;
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
@RequestMapping("/api/cmn/facility")
@Api(tags = "设施操作API")
public class FacilityApiController {
    @Autowired
    private FacilityService facilityService;

    @Autowired
    private RoomService roomService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{records,total,size,current}")
    })
    @ApiOperation(value = "分页条件查询")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, FacilityQueryVo facilityQueryVo) {
        Page<Facility> pageParam = new Page<>(page, limit);
        Page<Facility> pageModel = facilityService.selectPage(pageParam, facilityQueryVo);

        return Result.ok(pageModel);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{facility}")
    })
    @ApiOperation(value = "查询一个设施")
    @GetMapping("/auth/get")
    public Result get(String id) {
        Facility facility = facilityService.getById(id);

        if (ObjectUtils.isEmpty(facility)) {
            return Result.fail(ResultCodeEnum.FACILITY_GET_ERROR);
        } else {
            Page<Room> pageParam = new Page<>(1, Integer.MAX_VALUE);
            RoomQueryVo roomQueryVo = new RoomQueryVo();
            roomQueryVo.setFacilityId(facility.getId());

            facility.setRooms(roomService.selectPage(pageParam, roomQueryVo).getRecords());
            Map<String, Object> map = new HashMap<>(1);
            map.put("facility", facility);
            return Result.ok(map);
        }
    }
}
