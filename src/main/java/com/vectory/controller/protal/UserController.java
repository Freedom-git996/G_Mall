package com.vectory.controller.protal;

import com.vectory.common.CommonReturnType;
import com.vectory.common.validate.ValidationResult;
import com.vectory.common.validate.ValidatorImpl;
import com.vectory.pojo.qo.*;
import com.vectory.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user/")
@Api(tags = "用户接口")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ValidatorImpl validator;

    @ResponseBody
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录接口", httpMethod = "POST")
    public CommonReturnType login(HttpSession session,
                                  HttpServletResponse response,
                                  @ApiParam UserLoginQO userLoginQo) {
        ValidationResult result = validator.validate(userLoginQo);
        if (result.isHasErrors())
            return CommonReturnType.fail(result.getErrMsg());
        return userService.login(userLoginQo, session, response);
    }

    @ResponseBody
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册接口", httpMethod = "POST")
    public CommonReturnType register(@ApiParam UserRegisterQO userRegisterQo) {
        return userService.register(userRegisterQo);
    }

    @ResponseBody
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ApiOperation(value = "检查用户名、邮箱是否有效", httpMethod = "POST")
    public CommonReturnType checkValid(@ApiParam CheckValidQO checkValidQo) {
        ValidationResult result = validator.validate(checkValidQo);
        if (result.isHasErrors())
            return CommonReturnType.fail(result.getErrMsg());
        return userService.checkValid(checkValidQo.getStr(), checkValidQo.getType());
    }

    @ResponseBody
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ApiOperation(value = "获取登录用户信息", httpMethod = "POST")
    public CommonReturnType getUserInfo(HttpServletRequest request) {
        return userService.getUserInfo(request);
    }

    @ResponseBody
    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    @ApiOperation(value = "忘记密码，获取密保问题", httpMethod = "POST")
    public CommonReturnType forgetGetQuestion(@ApiParam(value = "用户名") @RequestParam(required = true) String username) {
        return userService.forgetGetQuestion(username);
    }

    @ResponseBody
    @RequestMapping(value = "forget_check_answer.do", method = RequestMethod.POST)
    @ApiOperation(value = "提交问题答案", httpMethod = "POST")
    public CommonReturnType forgetCheckAnswer(@ApiParam CheckAnswerQO checkAnswerQo) {
        ValidationResult result = validator.validate(checkAnswerQo);
        if (result.isHasErrors())
            return CommonReturnType.fail(result.getErrMsg());
        return userService.forgetCheckAnswer(checkAnswerQo);
    }

    @ResponseBody
    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.POST)
    @ApiOperation(value = "重置密码", httpMethod = "POST")
    public CommonReturnType forgetResetPassword(@ApiParam ResetPasswordQO resetPasswordQO) {
        return userService.forgetResetPassword(resetPasswordQO);
    }

    @ResponseBody
    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    @ApiOperation(value = "登录状态下修改密码", httpMethod = "POST")
    public CommonReturnType resetPassword(@ApiParam UpdatePasswordQO updatePasswordQO,
                                          HttpServletRequest request) {
        return userService.resetPassword(updatePasswordQO, request);
    }

    @ResponseBody
    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    @ApiOperation(value = "登录状态更新个人信息", httpMethod = "POST")
    public CommonReturnType updateInformation(@ApiParam UpdateUserInfoQO updateUserInfoQO,
                                          HttpServletRequest request) {
        return userService.updateInformation(updateUserInfoQO, request);
    }

    @ResponseBody
    @RequestMapping(value = "get_information.do", method = RequestMethod.POST)
    @ApiOperation(value = "获取当前登录用户的详细信息", httpMethod = "POST")
    public CommonReturnType getInformation(HttpServletRequest request) {
        return userService.getUserInfo(request);
    }

    @ResponseBody
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ApiOperation(value = "注销登录", httpMethod = "POST")
    public CommonReturnType logout(HttpServletRequest request,
                                   HttpServletResponse response) {
        return userService.logout(request, response);
    }
}
