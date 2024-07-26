package com.bjca.securitydemo.config;

import com.bjca.securitydemo.handler.MyAccessDeniedHandler;
import com.bjca.securitydemo.handler.MyAuthenticationFailureHandler;
import com.bjca.securitydemo.handler.MyAuthenticationSuccessHandler;
import com.bjca.securitydemo.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表单提交
        http.formLogin()
                //自定义用户名和密码参数
                .usernameParameter("username123")
                .passwordParameter("password123")
                //自定义登录页面
                .loginPage("/login.html")
                //必须和表单提交的接口一样，执行自定义登录逻辑
                .loginProcessingUrl("/login")
                //默认的登录成功处理器
                .successForwardUrl("/toMain")
                //默认的登录失败处理器
                .failureForwardUrl("/toError");
                //自定义登录成功处理器
                //.successHandler(new MyAuthenticationSuccessHandler("/main.html"))
                //自定义登录失败处理器
                //.failureHandler(new MyAuthenticationFailureHandler("/error.html"));

        //授权
        http.authorizeRequests()
                //ant表达式匹配
                //放行/login.html,不需要认证
                .antMatchers("/login.html").permitAll()
                //.antMatchers("/login.html").access("permitAll")
                //放行/error.html，不需要认证
                .antMatchers("/error.html").permitAll()
                //基于权限判断（严格大小写）
                //.antMatchers("/main1.html").hasAuthority("permission1")
                //.antMatchers("main1.html").hasAnyAuthority("permission1", "permission2")
                //基于角色判断（严格大小写)
                //.antMatchers("/main2.html").hasRole("role1")
                //.antMatchers("/main2.html").hasAnyRole("role1,role2")
                //正则表达式匹配
                //.regexMatchers()
                //mvc表达式匹配，和properties中的mvc.servlet.path配合使用
                //.mvcMatchers("demo").servletPath("/xxx").permitAll()
                //所有其他请求必须认证
                .anyRequest().authenticated();
                //自定义access方法
                //.anyRequest().access("@myServiceImpl.hasPermission(request, authentication)");

        //异常处理器
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

        //添加rememberMe功能
        http.rememberMe()
                //设置数据源
                .tokenRepository(persistentTokenRepository)
                //.rememberMeParameter()
                //超时时间
                .tokenValiditySeconds(60)
                //自定义登录逻辑
                .userDetailsService(userDetailServiceImpl);

        //退出
        http.logout()
                //退出的URL
                .logoutUrl("/logout")
                //退出成功后的URL
                .logoutSuccessUrl("/login.html");

        //关闭csrf防护
        http.csrf().disable();
    }

    /**
     * 指定密码加密的方法
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder getPasswordEncode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        jdbcTokenRepository.setDataSource(dataSource);
        //自动建表，仅在第一次启动时开启
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
    /**
     * 放行静态资源,css,js,images
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**")
                .antMatchers("/**/*.png")
                .antMatchers("/**/*.jpg");
    }
}

