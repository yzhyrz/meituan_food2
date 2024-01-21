package com.hyles.shuimen.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyles.shuimen.common.R;
import com.hyles.shuimen.dto.SetmealDto;
import com.hyles.shuimen.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto dto);
    public Page<SetmealDto> page(int page, int pageSize, String name);

    void removeWithDish(List<Long> ids);

    void updateStatusById(List<Long> ids, int status);

    SetmealDto showDishWithMeal(Long id);

    void updateMealWithDish(SetmealDto dto);
}
