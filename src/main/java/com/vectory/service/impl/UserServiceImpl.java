package com.vectory.service.impl;

import com.vectory.common.GlobalContant;
import com.vectory.common.error.BusinessException;
import com.vectory.common.error.EmBusinessError;
import com.vectory.dao.UserMapper;
import com.vectory.pojo.po.User;
import com.vectory.pojo.qo.UserLoginQo;
import com.vectory.pojo.qo.UserRegisterQo;
import com.vectory.pojo.vo.UserLoginVO;
import com.vectory.service.IUserService;
import com.vectory.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserLoginVO login(UserLoginQo userLoginQo) {
        return userMapper.selectForLogin(userLoginQo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int register(UserRegisterQo userRegisterQo) throws BusinessException {
        if(checkValid(userRegisterQo.getUsername(), GlobalContant.USERNAME) > 0)
            throw new BusinessException(EmBusinessError.USERNAME_REPEAT);
        if(checkValid(userRegisterQo.getEmail(), GlobalContant.E_MAIL) > 0)
            throw new BusinessException(EmBusinessError.EMAIL_REPEAT);
        User user = new User();
        BeanUtils.copyProperties(userRegisterQo, user);
        user.setPassword(MD5Util.MD5EncodeUtf8(userRegisterQo.getPassword()));
        user.setRole(GlobalContant.Role.ROLE_CUSTOMER);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return userMapper.insertSelective(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int checkValid(String checkValue, String paramName) throws BusinessException {
        if(StringUtils.isBlank(paramName)) throw new BusinessException(EmBusinessError.ILLEGAL_ARGUMENT);
        return userMapper.checkValid(checkValue, paramName);
    }
}
