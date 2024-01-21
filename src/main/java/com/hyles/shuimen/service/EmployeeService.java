package com.hyles.shuimen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyles.shuimen.entity.Employee;

import javax.servlet.http.HttpServletRequest;


/**
 * mybatis-plus 提供
 * IService 是一个通用的服务接口，可能定义了一些通用的服务方法，如插入、更新、删除、查询等
 */
public interface EmployeeService extends IService<Employee> {
    Employee login(HttpServletRequest request, Employee employee);
}
