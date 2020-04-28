package com.vectory.dao;

import com.vectory.pojo.po.User;
import com.vectory.pojo.qo.UserLoginQo;
import com.vectory.pojo.vo.UserLoginVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    UserLoginVO selectForLogin(UserLoginQo userLoginQo);

    int checkValid(@Param(value = "checkValue") String checkValue,
                   @Param(value = "paramName") String paramName);
}