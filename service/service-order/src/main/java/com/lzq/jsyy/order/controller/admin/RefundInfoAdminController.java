package com.lzq.jsyy.order.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.model.order.RefundInfo;
import com.lzq.jsyy.order.service.RefundInfoService;
import com.lzq.jsyy.vo.order.query.RefundInfoQueryVo;
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
@RequestMapping("/admin/redund")
@Api(tags = "退单记录后台管理端API")
public class RefundInfoAdminController {
    @Autowired
    private RefundInfoService refundInfoService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{records,total,size,current}")
    })
    @ApiOperation(value = "分页条件查询退单记录")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, RefundInfoQueryVo refundInfoQuery) {
        Page<RefundInfo> pageParam = new Page<>(page, limit);
        Page<RefundInfo> pageModel = refundInfoService.selectPage(pageParam, refundInfoQuery);

        return Result.ok(pageModel);
    }
}
