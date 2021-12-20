package com.lzq.jsyy.user.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.user.User;
import com.lzq.jsyy.user.service.UserService;
import com.lzq.jsyy.vo.user.LoginVo;
import com.lzq.jsyy.vo.user.add.UserAddVo;
import com.lzq.jsyy.vo.user.query.UserQueryVo;
import com.lzq.jsyy.vo.user.update.UserUpdateVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * admin端 用户
 *
 * @author lzq
 */
@RestController
@RequestMapping("/admin/user")
@ApiModel(description = "用户后台管理端API")
public class UserAdminController {
    @Autowired
    private UserService userService;


    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{user,token}")
    })
    @ApiOperation(value = "登录")
    @GetMapping("/login")
    public Result login(LoginVo loginVo) {
        Map<String, Object> map = userService.loginByPassword(loginVo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        map.remove("state");
        return Result.build(map, resultCodeEnum);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{records,total,size,current}")
    })
    @ApiOperation(value = "分页条件查询用户")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, UserQueryVo userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        Page<User> pageModel = userService.selectPage(pageParam, userQueryVo);

        return Result.ok(pageModel);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{}")
    })
    @ApiOperation(value = "添加用户")
    @PostMapping("/auth/add")
    public Result add(UserAddVo userAddVo) {
        Map<String, Object> map = userService.add(userAddVo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        map.remove("state");
        return Result.build(map, resultCodeEnum);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{user}")
    })
    @ApiOperation(value = "修改用户信息")
    @PutMapping("/auth/update")
    public Result update(UserUpdateVo userUpdateVo) {
        if (ObjectUtils.isEmpty(userUpdateVo)) {
            return Result.fail(ResultCodeEnum.USER_REPEAT);
        }
        User user = new User();
        user.setId(userUpdateVo.getId());
        user.setUsername(userUpdateVo.getUsername());
        user.setPassword(userUpdateVo.getPassword());
        user.setPermission(userUpdateVo.getPermission());
        user.setStudentNumber(userUpdateVo.getStudentNumber());
        user.setName(userUpdateVo.getName());
        user.setType(userUpdateVo.getType());

        boolean update = userService.updateById(user);
        if (update) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("user", user);
            return Result.ok(map);
        } else {
            return Result.fail(ResultCodeEnum.USER_REPEAT);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{}")
    })
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/auth/delete")
    public Result delete(String id) {
        boolean delete = userService.removeById(id);

        return delete ? Result.ok() : Result.fail(ResultCodeEnum.DELETE_FAIL);
    }

}
