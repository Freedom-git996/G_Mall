package com.vectory.controller.backend;

import com.vectory.common.CommonReturnType;
import com.vectory.common.validate.ValidationResult;
import com.vectory.common.validate.ValidatorImpl;
import com.vectory.pojo.qo.UserLoginQO;
import com.vectory.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/manage/user/")
@Api(tags = "后台-用户接口")
public class UserManageController {

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
        return userService.loginBackend(userLoginQo, session, response);
    }
}
