package com.joco.consumer.utils;

import com.joco.consumer.bo.AdminUserDetails;
import com.joco.mbg.model.UmsAdmin;
import com.joco.mbg.model.UmsResource;
import com.joco.consumer.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Qualifier("JwtOfUserDetailsService")
public class JwtOfUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        try {
            //获取用户信息
            UmsAdmin admin = adminService.getAdminByUsername(username);
            if (admin != null) {
                List<UmsResource> resourceList = adminService.getResourceList(admin.getId());
                return new AdminUserDetails(admin, resourceList);
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetails;
    }
}
