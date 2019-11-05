package com.isuwang.security.app;

import com.isuwang.security.core.properties.Oauth2ClientProperties;
import com.isuwang.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 权限配置
 */
@Configuration
@ComponentScan("com.isuwang.security.core")
@EnableAuthorizationServer
public class IsuwangAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // TODO 配置有效登录用户，可自行更改为jdbc连接方式，默认为内存方式，读取配置文件
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if(ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
            for (Oauth2ClientProperties config : securityProperties.getOauth2().getClients()) {
                builder.withClient(config.getClientId()).secret(config.getClientSecret())
                .accessTokenValiditySeconds(3*365*24*60*60) //有效期3年
                        .authorizedGrantTypes("authorization_code", "refresh_token", "password")
                        .scopes("all");
            }
        }
//        clients.inMemory()
//                .withClient("isuwang").secret("isuwang-security")
//                .accessTokenValiditySeconds(3*365*24*60*60) //有效期3年
//                .authorizedGrantTypes("authorization_code", "refresh_token", "password")
//                .scopes("all")
//                .and()
//                .withClient("kuaisu").secret("kuaisu-security")
//                .accessTokenValiditySeconds(18*60*60)
//                .authorizedGrantTypes("authorization_code", "refresh_token", "password")
//                .scopes("all");
        // 修改数据库方式
//            clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()") //allow check token
                .allowFormAuthenticationForClients();
    }
}
