package com.atguigu.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String sql = "SELECT * FROM `t_admin` WHERE loginacct=?";

        Map<String, Object> map = null;
        try{
            //1、查询指定用户的信息
           map = jdbcTemplate.queryForMap(sql, username);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (map == null)
            return null;
//2、将查询到的用户封装到框架使用的UserDetails里面
        return new User(map.get("loginacct").toString(), map.get("userpswd").toString(),
                AuthorityUtils.createAuthorityList("ROLE_学徒", "ROLE_大师", "罗汉拳"));//暂时写死，过后数据库中查
    }
}
