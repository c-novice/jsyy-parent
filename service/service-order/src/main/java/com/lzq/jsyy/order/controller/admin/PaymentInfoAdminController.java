package com.lzq.jsyy.order.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.order.service.PaymentInfoService;
import com.lzq.jsyy.vo.order.query.PaymentInfoQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/admin/payment")
@Api(tags = "支付记录后台管理端API")
public class PaymentInfoAdminController {
    @Autowired
    private PaymentInfoService paymentInfoService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{records,total,size,current}")
    })
    @ApiOperation(value = "分页条件查询支付记录")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, PaymentInfoQueryVo paymentInfoQuery) {
        Page<PaymentInfo> pageParam = new Page<>(page, limit);
        Page<PaymentInfo> pageModel = paymentInfoService.selectPage(pageParam, paymentInfoQuery);

        return Result.ok(pageModel);
    }
}
