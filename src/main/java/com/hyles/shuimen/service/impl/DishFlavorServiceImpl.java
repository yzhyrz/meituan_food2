package com.hyles.shuimen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyles.shuimen.entity.DishFlavor;
import com.hyles.shuimen.mapper.DishFlavorMapper;
import com.hyles.shuimen.service.DishFlavorService;
import com.hyles.shuimen.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
