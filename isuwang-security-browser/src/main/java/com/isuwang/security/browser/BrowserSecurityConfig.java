package com.isuwang.security.browser;

import com.isuwang.security.core.authentication.mobile.SmsCodeAuthenticationFilter;
import com.isuwang.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.isuwang.security.core.properties.SecurityProperties;
import com.isuwang.security.core.vaildate.code.SmsCodeFilter;
import com.isuwang.security.core.vaildate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 注入security配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 注入校验成功处理器
     */
    @Autowired
    private AuthenticationSuccessHandler isuwangAuthenticationSuccessHandler;

    /**
     * 注入校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler isuwangAuthenticationFailureHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    /**
     * 配置一个密码加密器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true); //启动时候系统自动创建表
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);

        // 定义要添加的拦截器
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(isuwangAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet(); //调用初始化方法


        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(isuwangAuthenticationFailureHandler);
        smsCodeFilter.setSecurityProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet(); //调用初始化方法

        http.addFilterBefore(validateCodeFilter , UsernamePasswordAuthenticationFilter.class) //在用户密码校验之前加入自定义（验证码校验拦截器）拦截器
                .addFilterBefore(smsCodeFilter , UsernamePasswordAuthenticationFilter.class)
                .formLogin()  // 配置表单访问
                    .loginPage("/authentication/required")
                    .loginProcessingUrl("/authentication/login")
                    .successHandler(isuwangAuthenticationSuccessHandler)
                    .failureHandler(isuwangAuthenticationFailureHandler)
                    .and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
                .and()
                .authorizeRequests()  // 配置验证配置
                .antMatchers("/authentication/required",
                        securityProperties.getBrowser().getLoginPage(),
                        "/code/*")
                .permitAll()
                .anyRequest()  // 所有的请求都要通过验证
                .authenticated()
                .and()
                .csrf().disable()
        .apply(smsCodeAuthenticationSecurityConfig);

    }
}
