package com.vectory.dao;

import com.vectory.pojo.po.User;
import com.vectory.pojo.qo.CheckAnswerQO;
import com.vectory.pojo.qo.ResetPasswordQO;
import com.vectory.pojo.qo.UpdatePasswordQO;
import com.vectory.pojo.qo.UserLoginQO;
import com.vectory.pojo.vo.UserLoginVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    UserLoginVO selectForLogin(UserLoginQO userLoginQo);

    int checkValid(@Param(value = "checkValue") String checkValue,
                   @Param(value = "paramName") String paramName);

    String getQuestion(@Param(value = "username") String username);

    int forgetCheckAnswer(CheckAnswerQO checkAnswerQo);

    int resetPassword(@Param(value = "resetPasswordQO") ResetPasswordQO resetPasswordQO,
                      @Param(value = "updateTime") Date updateTime);

    int updatePassword(@Param(value = "updatePasswordQO")UpdatePasswordQO updatePasswordQO,
                       @Param(value = "username") String username,
                       @Param(value = "updateTime") Date updateTime);
}