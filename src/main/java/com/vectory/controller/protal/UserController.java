package com.vectory.controller.protal;

import com.vectory.common.CommonReturnType;
import com.vectory.common.error.BusinessException;
import com.vectory.common.error.EmBusinessError;
import com.vectory.common.validate.ValidationResult;
import com.vectory.common.validate.ValidatorImpl;
import com.vectory.pojo.qo.UserLoginQo;
import com.vectory.pojo.qo.UserRegisterQo;
import com.vectory.pojo.vo.UserLoginVO;
import com.vectory.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user/")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ValidatorImpl validator;

    @ResponseBody
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public CommonReturnType login(UserLoginQo userLoginQo) throws BusinessException {
        ValidationResult result = validator.validate(userLoginQo);
        if (result.isHasErrors()) {
            return CommonReturnType.create(result.getErrMsg(), EmBusinessError.ILLEGAL_ARGUMENT.getErrorStatus());
        }
        UserLoginVO userLoginVO = userService.login(userLoginQo);
        if(userLoginVO == null)
            throw new BusinessException(EmBusinessError.LOGIN_ERROR);
        return CommonReturnType.success(userService.login(userLoginQo));
    }

    @ResponseBody
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    public CommonReturnType register(UserRegisterQo userRegisterQo) {
        return CommonReturnType.success("注册成功");
    }

}
