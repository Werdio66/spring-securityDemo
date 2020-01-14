package com.atguigu.security.config;

import com.atguigu.security.service.UserDetailsService;
import com.atguigu.security.util.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

@Configuration  // 声明为配置类
@EnableWebSecurity  //
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 基于内存的认证
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("zhangsan").password(new BCryptPasswordEncoder().encode("1")).roles("学徒", "大师");
//
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("lisi").password(new BCryptPasswordEncoder().encode("2")).roles("大宗师");
        // 根据用户名去查用户的详细信息

        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 记住我，cookie版
        http.rememberMe();


        // 记住我，数据库版
      /*  JdbcTokenRepositoryImpl jtrl = new JdbcTokenRepositoryImpl();
        jtrl.setDataSource(dataSource);
        http.rememberMe().tokenRepository(jtrl);*/


        // 授权首页和静态资源
        http.authorizeRequests()
                .antMatchers("/layui/**", "/index.jsp").permitAll() // 对这些资源放行
                .antMatchers("/level1/**").hasRole("学徒")
                .antMatchers("/level2/**").hasRole("大师")
                .antMatchers("/level3/**").hasRole("大宗师")
                .anyRequest().authenticated();// 其他的请求必须认证

        // 默认及自定义登录页
//        http.formLogin();   // 默认登录页，没有权限访问时跳转到这个页面

//        http.formLogin().loginPage("/index.jsp");   // 自定义登录页
        // 设置表单用户名和密码名称，登录成功后的跳转页面
        http.formLogin().loginPage("/index.jsp")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/main.html");

        // 没有访问权限跳转
        http.exceptionHandling().accessDeniedPage("/unauth.html");
        // 禁用csrf
//        http.csrf().disable();
        // 默认注销
//        http.logout();
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/index/jsp");




    }

}
