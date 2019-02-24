package com.isuwang.security.core;

import com.isuwang.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Configuration 注解为配置类
 * @EnableConfigurationProperties 让带有@ConfigurationProperties 注解的类生效
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

    /**
     * 配置一个密码加密器
     * 加密方式在这里配置，这里配置加密方式为MD5
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
        return new MessageDigestPasswordEncoder("MD5");
    }

}
