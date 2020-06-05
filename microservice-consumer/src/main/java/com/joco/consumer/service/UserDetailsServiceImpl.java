package com.joco.consumer.service;

import com.joco.consumer.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminServiceImpl adminServiceImpl;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return adminServiceImpl.loadUserByUsername(userName);
    }
}
