package com.atguigu.security.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *  自定义加密
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        System.out.println(rawPassword.toString());
        return MD5Util.digest(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // 自行判断
        return MD5Util.digest(rawPassword.toString()).equals(encodedPassword);
    }
}
