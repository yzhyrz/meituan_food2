package com.hyles.shuimen.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyles.shuimen.common.R;
import com.hyles.shuimen.dto.DishDto;
import com.hyles.shuimen.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dto);
    public Page<DishDto> pageHandler(Page<DishDto> dishDtoPage,Page<Dish> pageInfo,String name);
    public DishDto getDishWithFlavorById(Long id);

    public void updateDishWithFlavor(DishDto dto);

    public void updateStatusById(String ids, int status);
    public List<Dish> list(Dish dish);
}
