package com.isuwang.security.core;

import com.isuwang.security.core.domain.LoginUser;
import com.isuwang.security.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService , SocialUserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查找用户信息
        logger.info("form user login name:"+username);

        LoginUser userDetails = buildUser(username);;
        return userDetails;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        //根据用户名查找用户信息
        logger.info("socile user login userId:"+userId);

        SocialUserDetails userDetails = buildUser(userId);;

        return userDetails;
    }

    private LoginUser buildUser(String username) {


        // TODO 获取用户信息，封装User对象，可以自行定义（调用接口、自己查询或是默认对象皆可）。默认返回固定对象
            // TODO 模拟数据库登录方式,任意登录名，密码123456
        logger.info("登录用户名：" + username);
        String password = passwordEncoder.encode("123456");
        LoginUser loginUser =  new LoginUser(username, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
        return loginUser;
    }
}
