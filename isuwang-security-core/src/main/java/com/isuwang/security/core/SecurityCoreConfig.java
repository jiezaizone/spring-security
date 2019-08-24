package com.isuwang.security.core;

import com.isuwang.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.DigestUtils;

/**
 * @Configuration 注解为配置类
 * @EnableConfigurationProperties 让带有@ConfigurationProperties 注解的类生效
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    /**
     * 配置一个密码加密器
     * 加密方式在这里配置，这里配置加密方式为MD5
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
        PasswordEncoder md5PasswordEncoder = new PasswordEncoder(){

            @Override
            public String encode(CharSequence rawPassword) {
                String md5Password = DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
                return md5Password;
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes()));
            }
        };
        return md5PasswordEncoder;
    }

    /**
     * 配置TokenStore，默认为RedisTokenStore
     * @return
     */
    @Bean
    public TokenStore tokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();

    }
}
