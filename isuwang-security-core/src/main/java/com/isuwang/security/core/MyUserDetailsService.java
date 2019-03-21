package com.isuwang.security.core;

import com.github.dapeng.core.SoaException;
import com.isuwang.security.core.service.UserService;
import com.isuwang.soa.crmdb.customer.CustomerServiceClient;
import com.isuwang.soa.crmdb.customer.domain.TCustomer;
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

        UserDetails userDetails = null;
        try {
            userDetails =  buildUser(username);
        } catch (SoaException e) {
            logger.error(e.getMsg(),e);
        }
        return userDetails;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        //根据用户名查找用户信息
        logger.info("socile user login userId:"+userId);

        SocialUserDetails userDetails = null;
        try {
            userDetails =  buildUser(userId);
        } catch (SoaException e) {
            logger.error(e.getMsg(),e);
        }

        return userDetails;
    }

    private SocialUserDetails buildUser(String username) throws SoaException {
//        // TODO 模拟数据库登录方式
//        logger.info("登录用户名：" + userId);
//        String password = passwordEncoder.encode("123456");
////        String password = "e10adc3949ba59abbe56e057f20f883e";

        TCustomer customer =new CustomerServiceClient().findCustomerByLoginName(username);
        User user = userService.getUserByUserName(username);
        logger.info("数据库密码是：" + user.getPassword());
        return new SocialUser(username, customer.password,
                customer.disable == 0 ? true : false, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
    }
}
