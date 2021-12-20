package com.lzq.jsyy.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.cmn.client.PermissionFeignClient;
import com.lzq.jsyy.common.helper.JwtHelper;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.user.Account;
import com.lzq.jsyy.model.user.User;
import com.lzq.jsyy.user.mapper.UserMapper;
import com.lzq.jsyy.user.repository.AccountRepository;
import com.lzq.jsyy.user.service.UserService;
import com.lzq.jsyy.vo.user.BindingVo;
import com.lzq.jsyy.vo.user.LoginVo;
import com.lzq.jsyy.vo.user.RegisterVo;
import com.lzq.jsyy.vo.user.add.UserAddVo;
import com.lzq.jsyy.vo.user.query.UserQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lzq
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PermissionFeignClient permissionFeignClient;

    @Cacheable(value = "loginVo", keyGenerator = "keyGenerator")
    @Override
    public Map<String, Object> loginByPassword(LoginVo loginVo) {
        Map<String, Object> map = new HashMap<>(3);

        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        User user = baseMapper.selectOne(wrapper);

        // 未查到用户，若已经失败5次及以上则返回LOGIN_ERROR_MULTI，否则返回LOGIN_USER_ERROR，若查到用户则返回SUCCESS
        if (null == user) {
            String oldCount = redisTemplate.opsForValue().get(username);
            int newCount = 0;
            if (null != oldCount) {
                newCount = Integer.parseInt(oldCount) + 1;
            }
            if (newCount > 5) {
                map.put("state", ResultCodeEnum.LOGIN_ERROR_MULTI);
            } else {
                map.put("state", ResultCodeEnum.LOGIN_USER_ERROR);
            }
            redisTemplate.opsForValue().set(username, String.valueOf(newCount), 3600, TimeUnit.SECONDS);
        } else {
            map.put("state", ResultCodeEnum.SUCCESS);
            String token = JwtHelper.createToken(user.getId(), user.getUsername());
            map.put("token", token);
            map.put("user", user);
            redisTemplate.opsForValue().set(username, String.valueOf(0), 3600, TimeUnit.SECONDS);
        }

        return map;
    }

    @Cacheable(value = "loginByCode", keyGenerator = "keyGenerator")
    @Override
    public Map<String, Object> loginByCode(LoginVo loginVo) {
        // 验证码登录不设置冻结
        Map<String, Object> map = new HashMap<>(1);

        String username = loginVo.getUsername();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = baseMapper.selectOne(wrapper);

        String code = loginVo.getCode();
        String redisCode = redisTemplate.opsForValue().get(username);
        if (redisCode == null || !redisCode.equals(code)) {
            map.put("state", ResultCodeEnum.CODE_ERROR);
            return map;
        }
        // 用户为空进行注册
        if (ObjectUtils.isEmpty(user)) {
            RegisterVo registerVo = new RegisterVo();
            registerVo.setUsername(username);
            registerVo.setPassword("");
            return register(registerVo);
        }

        map.put("state", ResultCodeEnum.SUCCESS);
        String token = JwtHelper.createToken(user.getId(), user.getUsername());
        map.put("token", token);
        map.put("user", user);

        return map;
    }

    @Override
    public Page<User> selectPage(Page<User> pageParam, UserQueryVo userQueryVo) {
        if (ObjectUtils.isEmpty(userQueryVo)) {
            return null;
        }

        String username = userQueryVo.getUsername();
        String type = userQueryVo.getType();
        String studentNumber = userQueryVo.getStudentNumber();

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(username)) {
            wrapper.like("username", username);
        }
        if (!StringUtils.isEmpty(type)) {
            wrapper.eq("type", type);
        }
        if (!StringUtils.isEmpty(studentNumber)) {
            wrapper.like("studentNumber", studentNumber);
        }

        Page<User> users = baseMapper.selectPage(pageParam, wrapper);

        return users;
    }

    @Override
    public Map<String, Object> add(UserAddVo userAddVo) {
        Map<String, Object> map = new HashMap<>(1);

        if (StringUtils.isEmpty(userAddVo)) {
            map.put("state", ResultCodeEnum.PERMISSION_ADD_ERROR);
            return map;
        }

        QueryWrapper<User> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("username", userAddVo.getUsername());
        User user1 = baseMapper.selectOne(wrapper1);

        if (!StringUtils.isEmpty(user1)) {
            map.put("state", ResultCodeEnum.PERMISSION_EXIST);
            return map;
        }

        User user = new User(userAddVo.getUsername(), userAddVo.getPassword());
        baseMapper.insert(user);
        map.put("state", ResultCodeEnum.SUCCESS);
        map.put("user", user);
        return map;
    }

    @Override
    public Map<String, Object> register(RegisterVo registerVo) {
        Map<String, Object> map = new HashMap<>(2);

        if (StringUtils.isEmpty(registerVo)) {
            map.put("state", ResultCodeEnum.REGISTER_ERROR);
            return map;
        }

        String username = registerVo.getUsername();

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(username)) {
            wrapper.eq("username", username);
            User user1 = baseMapper.selectOne(wrapper);
            if (!StringUtils.isEmpty(user1)) {
                map.put("state", ResultCodeEnum.REGISTER_USERNAME_ERROR);
                return map;
            }
        }

        User user = new User(registerVo.getUsername(), registerVo.getPassword());
        baseMapper.insert(user);

        map.put("state", ResultCodeEnum.SUCCESS);
        String token = JwtHelper.createToken(user.getId(), user.getUsername());
        map.put("token", token);
        map.put("user", user);
        return map;
    }

    @Override
    public User updatePassword(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return null;
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = baseMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        user.setPassword(password);
        baseMapper.updateById(user);
        return user;
    }

    @Cacheable(value = "binding", keyGenerator = "keyGenerator")
    @Override
    public Map<String, Object> binding(User user, BindingVo bindingVo) {
        Map<String, Object> map = new HashMap<>(2);

        if (ObjectUtils.isEmpty(user) || ObjectUtils.isEmpty(bindingVo)) {
            map.put("state", ResultCodeEnum.BINDING_ERROR);
            return map;
        }

        String studentNumber = bindingVo.getStudentNumber();
        String password = bindingVo.getPassword();
        if (StringUtils.isEmpty(studentNumber) || StringUtils.isEmpty(password)) {
            map.put("state", ResultCodeEnum.BINDING_ERROR);
            return map;
        }

        Account account = accountRepository.getAccountByStudentNumberAndPassword(studentNumber, password);
        if (ObjectUtils.isEmpty(account)) {
            map.put("state", ResultCodeEnum.BINDING_ERROR);
            return map;
        }

        user.setStudentNumber(account.getStudentNumber());
        // 获取用户类型默认权限
        String type = account.getType();
        user.setPermission(permissionFeignClient.getByType(type).getName());

        user.setType(account.getType());
        user.setIsAuth(1);
        user.setName(account.getName());

        map.put("state", ResultCodeEnum.SUCCESS);
        map.put("user", user);
        baseMapper.updateById(user);
        return map;
    }

}
