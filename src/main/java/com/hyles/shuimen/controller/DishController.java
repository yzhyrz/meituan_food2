package com.hyles.shuimen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyles.shuimen.common.R;
import com.hyles.shuimen.dto.DishDto;
import com.hyles.shuimen.entity.Category;
import com.hyles.shuimen.entity.Dish;
import com.hyles.shuimen.service.CategoryService;
import com.hyles.shuimen.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     * @param dto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dto){
        log.info("新增菜品{}",dto.toString());

        dishService.saveWithFlavor(dto);

        return R.success("新增菜品成功");
    }

    /**
     * 分页显式菜品列表
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        // 构造分页器对象
        Page<Dish> pageInfo = new Page<>(page,pageSize);


        Page<DishDto> dishDotoPage = new Page<>();
        dishDotoPage = dishService.pageHandler(dishDotoPage,pageInfo,name);

        return R.success(dishDotoPage);
    }

    /**
     * 添加菜品信息-前-回显数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        log.info("回显菜品信息");
        // 回显下拉框信息,这里不用写，之前写过，会自动调用
//        categoryService.list();
        // 回显dish信息和口味dishflavor信息
        DishDto dto = dishService.getDishWithFlavorById(id);
        return R.success(dto);
    }

    /**
     * 修改菜品信息
     * @param dto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dto){
        log.info(dto.toString());

        dishService.updateDishWithFlavor(dto);
        return R.success("更新-菜品信息-成功");
    }

    /**
     * 删除菜品（批量）
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(String ids){
        log.info("删除-菜品{}",ids);
        String idArr[] = ids.split(",");
        List<String> set = Arrays.asList(idArr);
        dishService.removeByIds(Arrays.asList(idArr));
        return R.success("删除菜品成功");
    }

    @PostMapping("/status/{status}")
    public R<String> changeStatus(@PathVariable int status,String ids){
        log.info("修改-菜品状态信息");
        dishService.updateStatusById(ids,status);
        return R.success("修改成功");
    }

    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish){
        log.info("菜品列表");
        List<Dish> list = dishService.list(dish);
        return R.success(list);
    }
}
