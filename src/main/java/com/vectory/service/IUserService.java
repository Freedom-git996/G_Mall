package com.vectory.service;

import com.vectory.common.CommonReturnType;
import com.vectory.common.error.BusinessException;
import com.vectory.pojo.qo.UserLoginQo;
import com.vectory.pojo.qo.UserRegisterQo;
import com.vectory.pojo.vo.UserLoginVO;

public interface IUserService {
    UserLoginVO login(UserLoginQo userLoginQo);

    int register(UserRegisterQo userRegisterQo) throws BusinessException;
}
