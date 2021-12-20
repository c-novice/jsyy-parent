package com.lzq.jsyy.cmn.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.cmn.service.PermissionService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Permission;
import com.lzq.jsyy.vo.cmn.add.PermissionAddVo;
import com.lzq.jsyy.vo.cmn.query.PermissionQueryVo;
import com.lzq.jsyy.vo.cmn.update.PermissionUpdateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 权限
 *
 * @author lzq
 */
@RestController
@RequestMapping("/admin/cmn/permission")
@Api(tags = "权限后台管理端API")
public class PermissionAdminController {
    @Autowired
    private PermissionService permissionService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{records,total,size,current}")
    })
    @ApiOperation(value = "分页条件查询")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, PermissionQueryVo permissionQueryVo) {
        Page<Permission> pageParam = new Page<>(page, limit);
        Page<Permission> pageModel = permissionService.selectPage(pageParam, permissionQueryVo);

        return Result.ok(pageModel);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{permission}")
    })
    @ApiOperation(value = "添加权限")
    @PostMapping("/auth/add")
    public Result add(PermissionAddVo permissionAddVo) {
        Map<String, Object> map = permissionService.add(permissionAddVo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        map.remove("state");
        return Result.build(map, resultCodeEnum);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{permission}")
    })
    @ApiOperation(value = "修改权限信息")
    @PutMapping("/auth/update")
    public Result update(PermissionUpdateVo permissionUpdateVo) {
        Permission permission = new Permission();
        permission.setType(permissionUpdateVo.getType());
        permission.setFather(permissionUpdateVo.getFather());
        permission.setName(permissionUpdateVo.getName());
        permission.setId(permissionUpdateVo.getId());

        boolean update = permissionService.updateById(permission);
        if (update) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("permission", permission);
            return Result.ok(map);
        } else {
            return Result.fail();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{}")
    })
    @ApiOperation(value = "删除权限")
    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = permissionService.removeById(id);
        return delete ? Result.ok() : Result.fail();
    }
}
