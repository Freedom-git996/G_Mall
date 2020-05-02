package com.vectory.service.impl;

import com.vectory.common.CommonReturnType;
import com.vectory.common.error.EmBusinessError;
import com.vectory.dao.CategoryMapper;
import com.vectory.pojo.po.Category;
import com.vectory.pojo.qo.AddCategoryQO;
import com.vectory.pojo.qo.UpdateCategoryQO;
import com.vectory.pojo.vo.CategoryVO;
import com.vectory.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service

public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CommonReturnType getCategory(Integer categoryId) {
        if(categoryId == null) categoryId = 0;
        List<CategoryVO> categoryVOList = categoryMapper.selectByParentId(categoryId);
        return CommonReturnType.success(categoryVOList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CommonReturnType addCategory(AddCategoryQO addCategoryQO) {
        Category record = new Category();
        if(addCategoryQO.getParentId() == null)
            addCategoryQO.setParentId(0);
        else if(categoryMapper.checkById(addCategoryQO.getParentId()) == 0)
            return CommonReturnType.fail(EmBusinessError.CATEGORY_NOT_EXIST);
        record.setName(addCategoryQO.getName());
        record.setParentId(addCategoryQO.getParentId());
        record.setStatus(1);
        record.setSortOrder(1);
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        return categoryMapper.insert(record) > 0 ?
                CommonReturnType.success("新增成功")
                : CommonReturnType.fail(EmBusinessError.CATEGORY_ADD_ERROR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CommonReturnType setCategoryName(UpdateCategoryQO updateCategoryQO) {
        if(categoryMapper.checkById(updateCategoryQO.getId()) == 0)
            return CommonReturnType.fail(EmBusinessError.CATEGORY_NOT_EXIST);
        return categoryMapper.updateById(updateCategoryQO, new Date()) > 0 ?
                CommonReturnType.success("更新成功")
                : CommonReturnType.fail(EmBusinessError.CATEGORY_UPDATE_ERROR);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CommonReturnType getDeepCategory(Integer categoryId) {
        List<CategoryVO> child = null;
        if(categoryId == 0) {
            List<CategoryVO> categoryVOList = categoryMapper.selectByParentId(categoryId);
            for(CategoryVO categoryVO : categoryVOList) {
                categoryVO.setChild((child = findChildCategory(categoryVO.getId())).size() > 0 ? child : null);
            }
            return CommonReturnType.success(categoryVOList);
        } else if(categoryMapper.checkById(categoryId) == 0) {
            return CommonReturnType.fail(EmBusinessError.CATEGORY_NOT_EXIST);
        }
        CategoryVO categoryVO = categoryMapper.selectById(categoryId);
        categoryVO.setChild((child = findChildCategory(categoryVO.getId())).size() > 0 ? child : null);
        return CommonReturnType.success(categoryVO);
    }

    private List<CategoryVO> findChildCategory(Integer categoryId) {
        if(categoryMapper.checkById(categoryId) == 0)
            return null;
        List<CategoryVO> categoryVOList = categoryMapper.selectByParentId(categoryId);
        for(CategoryVO categoryVO : categoryVOList) {
            List<CategoryVO> child = null;
            categoryVO.setChild((child = findChildCategory(categoryVO.getId())).size() > 0 ? child : null);
        }
        return categoryVOList;
    }
}
