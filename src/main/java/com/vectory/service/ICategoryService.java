package com.vectory.service;

import com.vectory.common.CommonReturnType;
import com.vectory.pojo.qo.AddCategoryQO;
import com.vectory.pojo.qo.UpdateCategoryQO;

public interface ICategoryService {
    CommonReturnType getCategory(Integer categoryId);

    CommonReturnType addCategory(AddCategoryQO addCategoryQO);

    CommonReturnType setCategoryName(UpdateCategoryQO updateCategoryQO);

    CommonReturnType getDeepCategory(Integer categoryId);
}
