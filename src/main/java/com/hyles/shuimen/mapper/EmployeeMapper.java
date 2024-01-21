package com.hyles.shuimen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyles.shuimen.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 使用mybatis-plus提供的数据库操作方法
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
