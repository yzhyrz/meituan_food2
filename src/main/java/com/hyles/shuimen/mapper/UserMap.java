package com.hyles.shuimen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyles.shuimen.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMap extends BaseMapper<User> {
}
