package com.hyles.shuimen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyles.shuimen.common.R;
import com.hyles.shuimen.dto.DishDto;
import com.hyles.shuimen.entity.Category;
import com.hyles.shuimen.entity.Dish;
import com.hyles.shuimen.entity.DishFlavor;
import com.hyles.shuimen.mapper.DishMapper;
import com.hyles.shuimen.service.CategoryService;
import com.hyles.shuimen.service.DishFlavorService;
import com.hyles.shuimen.service.DishService;
import org.apache.ibatis.annotations.Lang;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;
    /**
     * 新增菜品，并保存口味信息
     * @param dto
     */
    @Transactional
    @Override
    public void saveWithFlavor(DishDto dto) {
        this.save(dto);
        // 保存口味信息
        Long id = dto.getCategoryId();
        List<DishFlavor> dishFlavors = dto.getFlavors();
        dishFlavors = dishFlavors.stream().map((item)->{
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(dishFlavors);
    }

    @Override
    public Page<DishDto> pageHandler(Page<DishDto> dishDtoPage, Page<Dish> pageInfo,String name) {
        // 条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        // 添加排序条件
        queryWrapper.like(name!=null,Dish::getName,name);

        // 执行分页查询
        dishService.page(pageInfo,queryWrapper);

        // 这里为什么不直接返回pageInfo呢？
        // 因为pageInfo中只有菜品的分类id，不具有名称
        // 对象拷贝，只拷贝size和pageSize等属性，不拷贝records属性
        // 此属性是数据库中的数据，占不拷贝，需要处理
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        // 遍历每一个元素，并进行处理
        List<DishDto> list = records.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();// 分类id
            // 根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category!=null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }


            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return dishDtoPage;
    }

    @Override
    public DishDto getDishWithFlavorById(Long id) {
        DishDto dishDto = new DishDto();
        // dish信息
        Dish dish = dishService.getById(id);

        // flavor信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);

        // 拷贝到dishDto容器
        BeanUtils.copyProperties(dish,dishDto);

        dishDto.setFlavors(flavors);

        return dishDto;
    }

    /**
     *
     * @param dto  表单信息
     */
    @Override
    @Transactional
    public void updateDishWithFlavor(DishDto dto) {
        // 更新dish信息
        this.updateById(dto);

        /**
         * 这里为什么要先删除后添加呢？为什么不能直接根据dishId更新口味呢
         * 会发现：如果原来菜品有3个口味（忌口，辣，甜度），变成（忌口，辣）
         * 如果我删除一个，直接根据id修改name和value，那甜度还会保留，这样不符合需求
         */

        // 先清除原flavor信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId,dto.getId());
        dishFlavorService.remove(queryWrapper);

        //再更新flavor信息
        List<DishFlavor> flavors = dto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(dto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public void updateStatusById(String ids, int status) {
        String idArr[] = ids.split(",");


        UpdateWrapper<Dish> dishUpdateWrapper = new UpdateWrapper<>();
        dishUpdateWrapper.in("id",idArr).set("status",status);


        dishService.update(dishUpdateWrapper);
    }

    /**
     * 用于新增套餐时，菜品列表展示
     * @param dish
     * @return
     */
    @Override
    public List<Dish> list(Dish dish) {

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        // 筛选 起售状态的菜品
        queryWrapper.eq(Dish::getStatus,1);
        // 排序
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = this.list(queryWrapper);
        return list;
    }

}
