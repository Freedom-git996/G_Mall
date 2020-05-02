package com.vectory.dao;

import com.vectory.pojo.po.Category;
import com.vectory.pojo.qo.UpdateCategoryQO;
import com.vectory.pojo.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    int checkById(Integer parentId);

    List<CategoryVO> selectByParentId(@Param(value = "categoryId") Integer categoryId);

    int updateById(@Param(value = "updateCategoryQO") UpdateCategoryQO updateCategoryQO,
                   @Param(value = "updateTime") Date updateTime);

    CategoryVO selectById(@Param(value = "categoryId") Integer categoryId);
}