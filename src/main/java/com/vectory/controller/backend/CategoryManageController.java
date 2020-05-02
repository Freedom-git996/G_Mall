package com.vectory.controller.backend;

import com.vectory.common.CommonReturnType;
import com.vectory.common.validate.ValidationResult;
import com.vectory.common.validate.ValidatorImpl;
import com.vectory.pojo.qo.AddCategoryQO;
import com.vectory.pojo.qo.UpdateCategoryQO;
import com.vectory.service.ICategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/manage/category/")
@Api(tags = "后台-品类接口")
public class CategoryManageController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ValidatorImpl validator;

    @ResponseBody
    @RequestMapping(value = "get_category.do")
    @ApiOperation(value = "获取品类子节点(平级)", httpMethod = "POST")
    public CommonReturnType getCategory(@ApiParam(value = "品类ID") @RequestParam(defaultValue = "0") Integer categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @ResponseBody
    @RequestMapping(value = "add_category.do")
    @ApiOperation(value = "增加品类", httpMethod = "POST")
    public CommonReturnType addCategory(@ApiParam AddCategoryQO addCategoryQO) {
        ValidationResult result = validator.validate(addCategoryQO);
        if (result.isHasErrors())
            return CommonReturnType.fail(result.getErrMsg());
        return categoryService.addCategory(addCategoryQO);
    }

    @ResponseBody
    @RequestMapping(value = "set_category_name.do")
    @ApiOperation(value = "更新品类名", httpMethod = "POST")
    public CommonReturnType setCategoryName(@ApiParam UpdateCategoryQO updateCategoryQO) {
        ValidationResult result = validator.validate(updateCategoryQO);
        if (result.isHasErrors())
            return CommonReturnType.fail(result.getErrMsg());
        return categoryService.setCategoryName(updateCategoryQO);
    }

    @ResponseBody
    @RequestMapping(value = "get_deep_category.do")
    @ApiOperation(value = "获取当前品类和其子品类", httpMethod = "POST")
    public CommonReturnType getDeepCategory(@ApiParam @RequestParam(defaultValue = "0") Integer categoryId) {
        return categoryService.getDeepCategory(categoryId);
    }
}
