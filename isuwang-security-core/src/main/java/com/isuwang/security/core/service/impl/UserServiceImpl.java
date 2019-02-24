package com.isuwang.security.core.service.impl;

import com.isuwang.security.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByUserName(String username) {
//        User userDetail  = null;
//        try{
//            userDetail = (User) jdbcTemplate.queryForObject("select c.*,c.login_name as username from crm_customers c where login_name = ?",
//                    new Object[]{username},new BeanPropertyRowMapper(User.class));
//        }catch (EmptyResultDataAccessException e){
//            return null;
//        }
//
//        return userDetail;
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select c.*,c.login_name as username from crm_customers c where login_name = ?",new Object[]{username});
        if(null != list && list.size() ==1 ){
            Map<String, Object> user = list.get(0);
            User userDetails = (User) User.withUsername(user.get("username")+"").password(user.get("password")+"").authorities("USER","GURP").build();
            return userDetails;
        }
        throw new UsernameNotFoundException("用户不存在");
    }
}
