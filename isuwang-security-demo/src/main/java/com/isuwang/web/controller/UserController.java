package com.isuwang.web.controller;

import com.isuwang.dto.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @RequestMapping(value = "/user", method = RequestMethod.GET )
    public List<User> query() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    @RequestMapping(value = "/user/{id:\\d+}", method = RequestMethod.GET )
    public User getInfo(@PathVariable String id) {
        User user = new User();
        user.setUsername("tom");
        return user;
    }
}
