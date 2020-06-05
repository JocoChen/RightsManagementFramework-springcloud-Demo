package com.joco.consumer.config;

import com.joco.consumer.service.AdminService;
import com.joco.consumer.service.UmsResourceService;
import com.joco.mbg.model.UmsResource;
import com.joco.security.component.DynamicSecurityService;
import com.joco.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * security模块动态访问权限相关配置
 * Created by jocochen on 2020/5/20.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DynamicSecurityConfig extends SecurityConfig {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UmsResourceService resourceService;


    //@Autowired
    //private UmsPermissionService permissionService;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserByUsername(username);
    }

    //动态添加资源管控表信息
    //扩展：如果希望用户权限表中对目录、菜单、按钮信息的管控放在后端，那么可在Map<String, ConfigAttribute>中添加permission表的url
    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UmsResource> resourceList = resourceService.listAll();
                for (UmsResource resource : resourceList) {
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
                }

                //扩展permission表的url
                //List<UmsPermission> permissionList = permissionService.listAll();
                //for (UmsPermission permission : permissionList) {
                //    map.put(permission.getUrl(), new org.springframework.security.access.SecurityConfig(permission.getId() + ":" + permission.getName()));
                //}

                return map;
            }
        };
    }
}
