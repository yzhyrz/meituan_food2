package com.hyles.shuimen.dto;


import com.hyles.shuimen.entity.Setmeal;
import com.hyles.shuimen.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
