package com.lzq.jsyy.user.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzq.jsyy.common.result.Result;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.user.User;
import com.lzq.jsyy.user.service.impl.UserServiceImpl;
import com.lzq.jsyy.vo.user.BindingVo;
import com.lzq.jsyy.vo.user.LoginVo;
import com.lzq.jsyy.vo.user.RegisterVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * app端 用户
 *
 * @author lzq
 */
@RestController
@RequestMapping("/api/user")
@ApiModel(description = "用户操作API")
public class UserApiController {
    @Autowired
    private UserServiceImpl userService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{user,token}")
    })
    @ApiOperation(value = "账号、密码登录")
    @GetMapping("/loginByPassword")
    public Result loginByPassword(LoginVo loginVo) {
        Map<String, Object> map = userService.loginByPassword(loginVo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        map.remove("state");
        return Result.build(map, resultCodeEnum);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{user,token}")
    })
    @ApiOperation(value = "手机号、验证码登录")
    @GetMapping("/loginByCode")
    public Result loginByCode(LoginVo loginVo) {
        Map<String, Object> map = userService.loginByCode(loginVo);
        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        map.remove("state");
        return Result.build(map, resultCodeEnum);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{user}")
    })
    @ApiOperation(value = "用户注册")
    @GetMapping("/register")
    public Result register(RegisterVo registerVo) {
        Map<String, Object> map = userService.register(registerVo);

        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        map.remove("state");
        return Result.build(map, resultCodeEnum);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{user}")
    })
    @ApiOperation(value = "校园信息绑定")
    @GetMapping("/auth/binding")
    public Result binding(String userId, BindingVo bindingVo) {
        User user = userService.getById(userId);
        Map<String, Object> map = userService.binding(user, bindingVo);

        ResultCodeEnum resultCodeEnum = (ResultCodeEnum) map.get("state");
        map.remove("state");
        return Result.build(map, resultCodeEnum);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "data:{user}")
    })
    @ApiOperation(value = "修改密码")
    @PutMapping("/auth/updatePassword")
    public Result update(String username, String password) {
        User user = userService.updatePassword(username, password);
        if (!ObjectUtils.isEmpty(user)) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("user", user);
            return Result.ok(map);
        } else {
            return Result.fail(ResultCodeEnum.USER_REPEAT);
        }
    }

    @ApiIgnore()
    @ApiOperation("根据用户名查询权限名")
    @GetMapping("/inner/getPermissionByUsername")
    String getPermissionByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("name", username);
        User user = userService.getOne(query);
        return user == null ? null : user.getPermission();
    }
}
