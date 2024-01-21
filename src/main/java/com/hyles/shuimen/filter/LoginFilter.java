//package com.hyles.shuimen.filter;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.hyles.shuimen.common.BaseContext;
//import com.hyles.shuimen.entity.Employee;
//import com.hyles.shuimen.service.EmployeeService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//public class LoginFilter extends UsernamePasswordAuthenticationFilter {
//    @Autowired
//    private EmployeeService employeeService;
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//        if (!request.getMethod().equals("POST")) {
//            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
//        }
//
//        // 是json格式
//        if (request.getContentType() != null
//                && (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)
//                || request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE))) {
//
//            Map<String, String> userInfo = new HashMap<>();
//            try {
//                userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
//
//                String username = userInfo.get(getUsernameParameter());
//                String password = userInfo.get(getPasswordParameter());
//
//                LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
//                // 查询条件
//                queryWrapper.eq(Employee::getUsername,username);
//
//
//                UsernamePasswordAuthenticationToken authRequest =
//                        new UsernamePasswordAuthenticationToken(username, password);
//                setDetails(request, authRequest);
//
//                return this.getAuthenticationManager().authenticate(authRequest);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // If content type is not JSON or any exception occurs, fall back to the default behavior
//        return super.attemptAuthentication(request, response);
//    }
//
//
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        // 在认证成功后的处理逻辑
//
//        SecurityContextHolder.getContext().setAuthentication(authResult);
//
//        log.info(authResult.getName());
//        request.setAttribute("authenticatedUser", authResult);
//
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/employee/login2");
//        dispatcher.forward(request, response);
//        // 设置响应内容类型
////        response.setContentType("application/json;charset=utf-8");
//
//        // 将认证信息转换成 JSON 格式并写入响应
////        PrintWriter out = response.getWriter();
////        out.write(new ObjectMapper().writeValueAsString(authResult));
////        out.flush();
////        out.close();
////        response.sendRedirect("/employee/login2");
//        // 让过滤器链自动继续执行
////        chain.doFilter(request, response);
//    }
//}
