package com.lzq.jsyy.cmn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.cmn.Permission;
import com.lzq.jsyy.vo.cmn.add.PermissionAddVo;
import com.lzq.jsyy.vo.cmn.query.PermissionQueryVo;

import java.util.Map;

/**
 * @author lzq
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 分页条件查询
     *
     * @param pageParam
     * @param permissionQueryVo
     * @return
     */
    Page<Permission> selectPage(Page<Permission> pageParam, PermissionQueryVo permissionQueryVo);

    /**
     * 添加一个权限
     *
     * @param permissionAddVo
     * @return
     */
    Map<String, Object> add(PermissionAddVo permissionAddVo);

}
