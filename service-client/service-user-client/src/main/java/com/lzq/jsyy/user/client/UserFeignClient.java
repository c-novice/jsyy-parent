package com.lzq.jsyy.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lzq
 */
@Component
@FeignClient("service-user")
public interface UserFeignClient {

    /**
     * 根据用户名获取用户权限名
     *
     * @param username
     * @return
     */
    @GetMapping("/api/user/inner/getPermissionByUsername")
    String getPermissionByUsername(@RequestParam String username);
}
