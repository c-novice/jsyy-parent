package com.lzq.jsyy.cmn.client;


import com.lzq.jsyy.model.cmn.Permission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 权限调用
 *
 * @author lzq
 */
@Component
@FeignClient("service-cmn")
public interface PermissionFeignClient {

    /**
     * 根据用户类型询获取默认权限
     *
     * @param type
     * @return
     */
    @GetMapping("/api/cmn/permission/inner/getByType")
    Permission getByType(@RequestParam String type);

    /**
     * 根据权限名获取父权限名
     *
     * @param name
     * @return
     */
    @GetMapping("/api/cmn/permission/inner/getFatherByName")
    String getFatherByName(@RequestParam String name);
}