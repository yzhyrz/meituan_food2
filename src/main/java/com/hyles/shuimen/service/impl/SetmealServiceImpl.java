package com.hyles.shuimen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyles.shuimen.common.CustomException;
import com.hyles.shuimen.common.R;
import com.hyles.shuimen.dto.DishDto;
import com.hyles.shuimen.dto.SetmealDto;
import com.hyles.shuimen.entity.Category;
import com.hyles.shuimen.entity.Dish;
import com.hyles.shuimen.entity.Setmeal;
import com.hyles.shuimen.entity.SetmealDish;
import com.hyles.shuimen.mapper.SetmealMapper;
import com.hyles.shuimen.service.CategoryService;
import com.hyles.shuimen.service.SetmealDishService;
import com.hyles.shuimen.service.SetmealService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    @Transactional
    public void saveWithDish(SetmealDto dto) {
        // 先添加套餐中信息
        setmealService.save(dto);

        //再关联套餐和菜品关系表
        /**
         * 这里要对SetmealDish中的mealId进行赋值
         * 直接从dto里面拿出来的数据不具有这个属性
         */
        List<SetmealDish> setmealDish = dto.getSetmealDishes();
        setmealDish.stream().map((item)->{
            item.setSetmealId(dto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDish);

    }

    @Override
    public Page<SetmealDto> page(int page, int pageSize, String name) {
        // 先查询套餐信息（缺少分类名称）
        Page<Setmeal> pageInfo = new Page(page,pageSize);


        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null,Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getCreateTime);

        setmealService.page(pageInfo,queryWrapper);

        // 再通过setMeal的分类ID查询分类名，并整合到SetMealDto
        Page<SetmealDto> page1 = new Page(page,pageSize);
        BeanUtils.copyProperties(pageInfo,page1,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto dto = new SetmealDto();
            BeanUtils.copyProperties(item,dto);
            Long category_id = item.getCategoryId();
            Category category = categoryService.getById(category_id);
            if(category!=null){
                String name1 = category.getName();
                dto.setCategoryName(name1);
            }
            return dto;
        }).collect(Collectors.toList());

        page1.setRecords(list);
        return page1;
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        // 先查看当前套餐是否是停售状态
        // select count(*) from setmeal where id in (a,b,c) and status = '在售'
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getStatus,1);
        queryWrapper.in(Setmeal::getId,ids);
        int count = setmealService.count(queryWrapper);

        if(count > 0){
            // 如果不是，则抛出异常
            throw new CustomException("套餐处于在售状态，无法删除");
        }
        // 如果是，先删除套餐和菜品关系表
        LambdaQueryWrapper<SetmealDish> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(queryWrapper1);

        // 再删除套餐信息
        this.removeByIds(ids);
    }

    @Override
    public void updateStatusById(List<Long> ids, int status) {
        UpdateWrapper<Setmeal> queryWrapper = new UpdateWrapper<>();
        queryWrapper.in("id",ids).set("status",status);
        setmealService.update(queryWrapper);
    }

    @Override
    public SetmealDto showDishWithMeal(Long id) {
        SetmealDto setmealDto = new SetmealDto();

        // 显示套餐信息
        Setmeal setmeal = setmealService.getById(id);

        // 显示餐品信息列表
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> setmealDishes = setmealDishService.list(queryWrapper);

        BeanUtils.copyProperties(setmeal,setmealDto);

        setmealDto.setSetmealDishes(setmealDishes);
        return setmealDto;
    }

    @Override
    @Transactional
    public void updateMealWithDish(SetmealDto dto) {
        // 修改表单内容
        setmealService.updateById(dto);
        // 修改餐品信息
        // 先清除
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,dto.getId());
        setmealDishService.remove(queryWrapper);

        List<SetmealDish> setmealDishes = dto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item)->{
            item.setSetmealId(dto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);



    }
}
