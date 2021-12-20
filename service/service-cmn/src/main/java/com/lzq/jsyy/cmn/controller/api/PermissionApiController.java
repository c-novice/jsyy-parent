package com.lzq.jsyy.cmn.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzq.jsyy.cmn.service.PermissionService;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.model.cmn.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/api/cmn/permission")
@Api(tags = "权限操作API")
public class PermissionApiController {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "获取所有权限名")
    @GetMapping("/auth/getAllName")
    public Result getAllName() {
        List<Permission> list = permissionService.list();
        List<String> result = list.stream().map(Permission::getName).collect(Collectors.toList());
        return Result.ok(result);
    }

    @ApiIgnore()
    @ApiOperation(value = "根据用户类型获取默认权限")
    @GetMapping("/inner/getByType")
    public Permission getByType(String type) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        return permissionService.getOne(wrapper);
    }

    @ApiIgnore()
    @ApiOperation(value = "根据权限名获取父权限名")
    @GetMapping("/inner/getFatherByName")
    public String getFatherByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        Permission permission = permissionService.getOne(wrapper);
        if (ObjectUtils.isEmpty(permission)) {
            return null;
        }
        return permission.getFather();
    }
}
