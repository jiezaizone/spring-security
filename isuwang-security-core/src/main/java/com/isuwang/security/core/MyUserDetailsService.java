package com.isuwang.security.core;

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

        return buildUser(username);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        //根据用户名查找用户信息
        logger.info("socile user login userId:"+userId);

        return buildUser(userId);
    }

    private SocialUserDetails buildUser(String username) {
//        // TODO 模拟数据库登录方式
//        logger.info("登录用户名：" + userId);
//        String password = passwordEncoder.encode("123456");
////        String password = "e10adc3949ba59abbe56e057f20f883e";
        User user = userService.getUserByUserName(username);
        logger.info("数据库密码是：" + user.getPassword());
        return new SocialUser(username, user.getPassword(),
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
    }
}
