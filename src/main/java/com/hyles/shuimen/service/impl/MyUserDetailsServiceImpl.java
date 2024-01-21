//package com.hyles.shuimen.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.hyles.shuimen.common.BaseContext;
//import com.hyles.shuimen.entity.Employee;
//import com.hyles.shuimen.service.EmployeeService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import javax.xml.ws.soap.Addressing;
//
///**
// * 登录服务实现类型
// */
//@Slf4j
//@Service
//public class MyUserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private EmployeeService employeeService;
//    /**
//     * 根据用户名查询用户对象和用户权限列表
//     * @param username
//     * @return UserDetails类型
//     * @throws UsernameNotFoundException
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        log.info("自定义-登陆服务运行-启动");
//        log.info("username = {}",username);
//        // 根据用户名查询用户
//        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
//        // 查询条件
//        queryWrapper.eq(Employee::getUsername,username);
//        Employee emp = employeeService.getOne(queryWrapper);
//
//        if(emp == null){
//            log.info("自定义-登陆服务运行-用户不存在");
//            throw new UsernameNotFoundException("用户或密码错误");
//        }
//        long x = Thread.currentThread().getId();
//        System.out.println("hbwebfwe=   "+ x);
//        Long id = emp.getId();
////        BaseContext.setCurrentId(id);
//        log.info("自定义-登陆服务运行-{}",emp.toString());
//        User result = new User(
//                username,// 登录用户的用户名
//                emp.getPassword(),// 登录用户的密码
//                AuthorityUtils.NO_AUTHORITIES// security 提供的无权限-空集合
//        );
//        log.info(result.toString());
//        return result;
//    }
//}
