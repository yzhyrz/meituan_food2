package com.hyles.shuimen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyles.shuimen.entity.User;
import com.hyles.shuimen.mapper.UserMap;
import com.hyles.shuimen.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMap, User> implements UserService {
}
