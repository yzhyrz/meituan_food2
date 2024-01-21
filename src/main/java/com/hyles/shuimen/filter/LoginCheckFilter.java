package com.hyles.shuimen.filter;

import com.alibaba.fastjson.JSON;
import com.hyles.shuimen.common.BaseContext;
import com.hyles.shuimen.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器
 * 检查用户是否已经完成登录
 * urlPatterns = "/*"  代表对所有请求进行过滤
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    // 路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获得本次请求的url
        String requestUri = request.getRequestURI();

        log.info("拦截到请求：{}",requestUri);

        String[] urls= new String[]{
                "/employee/login",// 员工登录请求
                "/employee/logout",// 员工注销请求
                // 这里的前端页面可以查看（放行），因为显示出来也没有数据
                // 我们针对的是请求，controller
                "/backend/**",// 所有以backend开头的页面
                "/front/**",// 所有以front开头的页面
                // 这里是为了 后面单独验证这个功能用的，可以删掉
                "/common/**",// 以common开头（上传和下载请求）
                "/user/sendMsg", // 移动端发送短信
                "/user/login" // 移动端-登录
//
        };

        // 判断本次请求是否需要处理
        boolean res = check(urls,requestUri);

        // 不需要处理，直接放行
        if(res){
            log.info("本次请求 {} 不需要处理",requestUri);
            filterChain.doFilter(request,response);
            return;
        }

        // 判断后台-登录状态，如果已登录，直接放行
        if(request.getSession().getAttribute("employee") != null){
            Long id =(Long) request.getSession().getAttribute("employee");
            log.info("用户{}已登录",id);

            // 这里存用户id，为了后面实现公共字段添加功能
            BaseContext.setCurrentId(id);

            filterChain.doFilter(request,response);
            return;
        }

        // 判断移动端-登录状态，如果已登录，直接放行
        if(request.getSession().getAttribute("user") != null){
            Long userId =(Long) request.getSession().getAttribute("user");
            log.info("用户{}已登录",userId);

            // 这里存用户id，为了后面实现公共字段添加功能
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request,response);
            return;
        }

        log.info("用户未登录");
        //如果未登录，返回登录结果
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;


    }

    public boolean check(String[] urls,String requestURI){
        for(String url:urls){
            boolean match = PATH_MATCHER.match(url,requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
