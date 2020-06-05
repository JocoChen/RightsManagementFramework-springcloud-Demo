package com.joco.consumer.service;

import com.joco.consumer.dto.UmsAdminParam;
import com.joco.mbg.model.UmsAdmin;
import com.joco.mbg.model.UmsResource;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 后台管理员Service
 * Created by jocochen on 2020/5/20.
 */

public interface AdminService {

    /**
     * 根据用户名获取后台管理员
     */

    UmsAdmin getAdminByUsername(String username);
    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 获取指定用户的可访问资源
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 用户注册
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);
}
