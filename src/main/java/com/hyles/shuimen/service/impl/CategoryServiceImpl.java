package com.hyles.shuimen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyles.shuimen.common.CustomException;
import com.hyles.shuimen.common.R;
import com.hyles.shuimen.entity.Category;
import com.hyles.shuimen.entity.Dish;
import com.hyles.shuimen.entity.Setmeal;
import com.hyles.shuimen.mapper.CategoryMapper;
import com.hyles.shuimen.service.CategoryService;
import com.hyles.shuimen.service.DishService;
import com.hyles.shuimen.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    @Override
    public void removeById(Long id) {

        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper();
        // 1. 判断所删除的类别是否包含菜品

        dishQueryWrapper.eq(Dish::getCategoryId,id);
        int cnt = dishService.count(dishQueryWrapper);
        if (cnt>0){
            // 抛出异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        // 2. 。。。。。。。。。。。。套餐
        LambdaQueryWrapper<Setmeal> mealQueryWrapper = new LambdaQueryWrapper();
        // 1. 判断所删除的类别是否包含菜品

        mealQueryWrapper.eq(Setmeal::getCategoryId,id);
        int cnt2 = setmealService.count(mealQueryWrapper);
        if (cnt2>0){
            // 抛出异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        super.removeById(id) ;

    }
}
