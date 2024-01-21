package com.hyles.shuimen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyles.shuimen.common.R;
import com.hyles.shuimen.entity.Category;

public interface CategoryService extends IService<Category> {
    public void removeById(Long id);
}
