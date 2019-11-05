package com.isuwang.security.core.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class OAuthController {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private SessionRegistry sessionRegistry;

    /**
     * 注销操作
     *
     * @param request
     * @param token
     */
    @RequestMapping(value = "/oauth/revoke-token", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request, @RequestParam(value = "", required = false) String token) {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null) {
//            String tokenValue = authHeader.replace("Bearer", "").trim();
//            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
//            tokenStore.removeAccessToken(accessToken);
//        }

        if (!StringUtils.equals("token", "")) {
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
            tokenStore.removeAccessToken(accessToken);
        }
    }

    @RequestMapping(value = "/oauth/getCurrentUser", method = {RequestMethod.GET, RequestMethod.POST})
    public Object getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @RequestMapping(value = "/oauth/getAllLoginUser", method = {RequestMethod.GET, RequestMethod.POST})
    public List<Object> getAllLoginUser() {
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        return allPrincipals;
    }
}
