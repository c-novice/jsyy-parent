package com.lzq.jsyy.order.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.order.service.OrderInfoService;
import com.lzq.jsyy.vo.order.add.OrderInfoAddVo;
import com.lzq.jsyy.vo.order.query.OrderInfoQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 预约订单信息
 *
 * @author lzq
 */
@RestController
@RequestMapping("/api/order/orderInfo")
@Api(tags = "预约订单操作API")
public class OrderInfoApiController {
    @Autowired
    private OrderInfoService orderInfoService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{records,total,size,current}")
    })
    @ApiOperation(value = "查看待处理的预约订单")
    @GetMapping("/auth/remain/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, String permissionName) {
        Page<OrderInfo> pageParam = new Page<>(page, limit);
        Page<OrderInfo> pageModel = orderInfoService.selectPendingOrder(pageParam, permissionName);

        return Result.ok(pageModel);
    }

    @ApiOperation(value = "分页条件查询预约订单")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, OrderInfoQueryVo orderInfoQuery) {
        Page<OrderInfo> pageParam = new Page<>(page, limit);
        Page<OrderInfo> pageModel = orderInfoService.selectPage(pageParam, orderInfoQuery);

        return Result.ok(pageModel);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{orderInfo}")
    })
    @ApiOperation(value = "添加订单")
    @PostMapping("/auth/order")
    public Result order(OrderInfoAddVo orderInfoAddVo) {
        Map<String, Object> map = orderInfoService.add(orderInfoAddVo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        map.remove("state");
        return Result.build(map, resultCodeEnum);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{}")
    })
    @ApiOperation(value = "删除订单记录")
    @DeleteMapping("/auth/delete")
    public Result delete(String outTradeNo) {
        boolean delete = orderInfoService.delete(outTradeNo);
        return delete ? Result.ok() : Result.fail();
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{orderInfo}")
    })
    @ApiOperation(value = "审批订单")
    @GetMapping("/auth/pending")
    public Result pending(String username, String outTradeNo, Integer status) {
        Map<String, Object> map = orderInfoService.pending(username, outTradeNo, status);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        map.remove("state");
        return Result.build(map, resultCodeEnum);
    }
}
