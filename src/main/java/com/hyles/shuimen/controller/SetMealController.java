package com.hyles.shuimen.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyles.shuimen.common.R;
import com.hyles.shuimen.dto.SetmealDto;
import com.hyles.shuimen.entity.SetmealDish;
import com.hyles.shuimen.service.SetmealDishService;
import com.hyles.shuimen.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐信息
     * @param dto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto dto){
        log.info("新增-套餐信息{}",dto.toString());
        setmealService.saveWithDish(dto);
        return R.success("新增-套餐信息-成功");
    }

    /**
     * 分页查找
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("分页-page = {},pageSize = {},name = {}",page,pageSize,name);
        Page<SetmealDto> pages = setmealService.page(page,pageSize,name);
        return R.success(pages);
    }

    /**
     * 批量删除订单
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("删除套餐{}",ids);
        setmealService.removeWithDish(ids);
        return R.success("套餐删除成功");
    }

    /**
     * 批量修改状态
     */
    @PostMapping("/status/{status}")
    public R<String> updataStatus(@RequestParam List<Long> ids,@PathVariable int status){

        log.info("修改-套餐状态信息");
        setmealService.updateStatusById(ids,status);
        return R.success("状态修改成功");
    }
    /**
     * 修改订单信息-前-回显列表
     */

    @GetMapping("{id}")
    public R<SetmealDto> get(@PathVariable Long id){
        log.info("回显-套餐信息");
        // 显示下拉框-之前已实现

        // 显示菜品列表和套餐信息
        SetmealDto setmealDto = setmealService.showDishWithMeal(id);
        return R.success(setmealDto);
    }

    /**
     * 更新-套餐信息
     */

    @PutMapping
    public R<String> updateMealWithDish(@RequestBody SetmealDto dto){
        log.info("修改-套餐信息");
        setmealService.updateMealWithDish(dto);
        return R.success("修改套餐信息-成功");
    }

}
