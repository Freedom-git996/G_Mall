package com.vectory.service;

import com.vectory.common.CommonReturnType;
import com.vectory.pojo.qo.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface IUserService {
    CommonReturnType login(UserLoginQO userLoginQo,
                           HttpSession session,
                           HttpServletResponse response);

    CommonReturnType register(UserRegisterQO userRegisterQo);

    CommonReturnType checkValid(String checkValue, String paramName);

    CommonReturnType getUserInfo(HttpServletRequest request);

    CommonReturnType forgetGetQuestion(String username);

    CommonReturnType forgetCheckAnswer(CheckAnswerQO checkAnswerQo);

    CommonReturnType forgetResetPassword(ResetPasswordQO resetPasswordQO);

    CommonReturnType resetPassword(UpdatePasswordQO updatePasswordQO,
                                   HttpServletRequest request);

    CommonReturnType updateInformation(UpdateUserInfoQO updateUserInfoQO,
                                   HttpServletRequest request);

    CommonReturnType logout(HttpServletRequest request,
                            HttpServletResponse response);
}
