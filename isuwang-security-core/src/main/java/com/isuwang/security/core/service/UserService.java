package com.isuwang.security.core.service;


import org.springframework.security.core.userdetails.User;

public interface UserService {

    User getUserByUserName(String username);
}
