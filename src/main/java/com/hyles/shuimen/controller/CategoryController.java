package com.hyles.shuimen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyles.shuimen.common.R;
import com.hyles.shuimen.entity.Category;
import com.hyles.shuimen.entity.Employee;
import com.hyles.shuimen.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    public CategoryService categoryService;


    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("增加菜品分类");

        categoryService.save(category);

        return R.success("菜品分类——添加成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        log.info("page={},pageSize={}",page,pageSize);

        Page pageInfo = new Page(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();

        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 通过id删除分类
     * 实际url：category/ids=xxx
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("删除分类，id={}",ids);

        categoryService.removeById(ids);
        return R.success("删除-分类-成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类种类信息 {}",category);

        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    /**
     * 新增分类时，下拉框显式的菜品类别
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list( Category category){
        // 条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        // 添加条件
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        // 优先sort排序，相同再看CreateTime
        queryWrapper.orderByAsc(Category::getSort).orderByAsc(Category::getCreateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }


}
