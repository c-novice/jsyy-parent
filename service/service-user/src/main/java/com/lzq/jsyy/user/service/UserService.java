package com.lzq.jsyy.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.user.User;
import com.lzq.jsyy.vo.user.BindingVo;
import com.lzq.jsyy.vo.user.LoginVo;
import com.lzq.jsyy.vo.user.RegisterVo;
import com.lzq.jsyy.vo.user.add.UserAddVo;
import com.lzq.jsyy.vo.user.query.UserQueryVo;

import java.util.Map;

/**
 * @author lzq
 */
public interface UserService extends IService<User> {
    /**
     * 根据账号、密码登录
     *
     * @param loginVo
     * @return
     */
    Map<String, Object> loginByPassword(LoginVo loginVo);

    /**
     * 根据手机号、验证码登录
     *
     * @param loginVo
     * @return
     */
    Map<String, Object> loginByCode(LoginVo loginVo);

    /**
     * 条件分页查询用户
     *
     * @param pageParam
     * @param userQueryVo
     * @return
     */
    Page<User> selectPage(Page<User> pageParam, UserQueryVo userQueryVo);

    /**
     * 添加用户
     *
     * @param userAddVo
     * @return
     */
    Map<String, Object> add(UserAddVo userAddVo);

    /**
     * 注册
     *
     * @param register
     * @return
     */
    Map<String, Object> register(RegisterVo register);

    /**
     * 账号绑定
     *
     * @param user
     * @param bindingVo
     * @return
     */

    Map<String, Object> binding(User user, BindingVo bindingVo);

    /**
     * 修改密码
     *
     * @param username
     * @param password
     * @return
     */
    User updatePassword(String username, String password);
}
