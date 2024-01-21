//package com.hyles.shuimen.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.hyles.shuimen.filter.LoginFilter;
//import com.hyles.shuimen.service.impl.MyUserDetailsServiceImpl;
//import com.hyles.shuimen.utils.MyPasswordEncoder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
//import org.springframework.security.core.session.SessionRegistry;
//import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.session.HttpSessionEventPublisher;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.OrRequestMatcher;
//import org.springframework.security.web.util.matcher.RegexRequestMatcher;
//
//import javax.servlet.RequestDispatcher;
//import java.io.PrintWriter;
//
//import static org.springframework.web.servlet.function.RequestPredicates.headers;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private MyUserDetailsServiceImpl userDetailsService;
//
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    // 忽略静态资源
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers( "/**/*.css","/**/plugins/**","/**/styles/**"
//                        ,"/**/*.js", "/**/images/**","/**/*.ico");
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // 链式配置
//        http.authorizeRequests() // 开启权限配置
//                .antMatchers("/backend/index.html").authenticated()
//                .anyRequest().authenticated()// 所有请求都要认证之后才能访问
//                .and()// 返回HttpSecurity类的子类，重新链式
//                .formLogin() // 开启表单登录配置
//                .loginPage("/backend/page/login/login.html") // 配置登陆页面地址
//                .loginProcessingUrl("/employee/login") // 配置登录接口地址
////                .successForwardUrl("/employee/login") // 登录成功后的跳转地址
//                .failureUrl("/backend/page/login/login.html") // 登录失败后的跳转地址
////                .usernameParameter("username") // 用户名的参数名
////                .passwordParameter("password")// 登陆密码的参数名
//                .permitAll() // 表示与登录相关的这几个页面或接口不拦截，直接放行
//                .and()
//                .logout() // 开启注销登录配置
//                .logoutUrl("/logout") // 指定注销登录请求地址 默认为Get请求
//                .invalidateHttpSession(true) // 是否使session 失效
//                .clearAuthentication(true) // 是否清除认证信息
//                .logoutSuccessUrl("/backend/page/login/login.html") // 注销登录后的跳转地址
//
//                .and()
//                .csrf().disable(); // 禁用CSRF防御机制
//        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
//        // 添加在UsernamePasswordAuthenticationFilter之后
//        http.headers()
//                .contentSecurityPolicy("frame-ancestors 'self'");
//        http.headers().frameOptions().sameOrigin();
//    }
//
////     json 数据格式登录
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        auth.authenticationProvider(authProvider);
//
////        auth.userDetailsService(userDetailsService);
//    }
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    LoginFilter loginFilter() throws Exception {
//        LoginFilter loginFilter = new LoginFilter();
//        loginFilter.setAuthenticationManager(authenticationManagerBean());
////        loginFilter.setAuthenticationSuccessHandler((req, resp,auth) -> {
////            resp.setContentType("application/json;charset=utf-8");
//////            resp.sendRedirect("/employee/login");
////            PrintWriter out = resp.getWriter();
////            out.write(new ObjectMapper().writeValueAsString(auth));
////            out.flush();
////            out.close();
////
//////            RequestDispatcher dispatcher = req.getRequestDispatcher("/employee/login");
//////            dispatcher.forward(req, resp);
////
////        });
//        loginFilter.setFilterProcessesUrl("/employee/login");
//
//        return loginFilter;
//    }
////    @Bean
////    public FilterRegistrationBean<LoginFilter> loginFilter() {
////        FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
////        registrationBean.setFilter(new LoginFilter());
////        registrationBean.addUrlPatterns("/employee/login"); // 设置过滤的路径
////        return registrationBean;
////    }
//}
//
