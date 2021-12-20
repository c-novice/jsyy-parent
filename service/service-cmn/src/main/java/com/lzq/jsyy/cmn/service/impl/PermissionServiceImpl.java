package com.lzq.jsyy.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.cmn.mapper.PermissionMapper;
import com.lzq.jsyy.cmn.service.PermissionService;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Permission;
import com.lzq.jsyy.vo.cmn.add.PermissionAddVo;
import com.lzq.jsyy.vo.cmn.query.PermissionQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public Page<Permission> selectPage(Page<Permission> pageParam, PermissionQueryVo permissionQueryVo) {
        if (null == permissionQueryVo) {
            return null;
        }

        String type = permissionQueryVo.getType();
        String name = permissionQueryVo.getName();

        QueryWrapper<Permission> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(type)) {
            wrapper.like("type", type);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        Page<Permission> page = baseMapper.selectPage(pageParam, wrapper);

        return page;
    }

    @Override
    public Map<String, Object> add(PermissionAddVo permissionAddVo) {
        Map<String, Object> map = new HashMap<>(1);

        if (StringUtils.isEmpty(permissionAddVo)) {
            map.put("state", ResultCodeEnum.PERMISSION_ADD_ERROR);
            return map;
        }

        String name = permissionAddVo.getName();
        String type = permissionAddVo.getType();
        String father = permissionAddVo.getFather();
        QueryWrapper<Permission> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("name", name)
                .or()
                .eq("type", type);
        Permission permission1 = baseMapper.selectOne(wrapper1);

        if (!StringUtils.isEmpty(permission1)) {
            map.put("state", ResultCodeEnum.PERMISSION_EXIST);
            return map;
        }

        Permission permission = new Permission();
        permission.setName(name);
        permission.setFather(father);
        permission.setType(type);
        save(permission);
        map.put("state", ResultCodeEnum.SUCCESS);

        return map;
    }
}
