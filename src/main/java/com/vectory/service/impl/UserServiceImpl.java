package com.vectory.service.impl;

import com.vectory.common.CommonReturnType;
import com.vectory.common.GlobalContant;
import com.vectory.common.error.EmBusinessError;
import com.vectory.dao.UserMapper;
import com.vectory.pojo.po.User;
import com.vectory.pojo.qo.*;
import com.vectory.pojo.vo.UserLoginVO;
import com.vectory.service.IUserService;
import com.vectory.utils.CookieUtil;
import com.vectory.utils.JedisUtil;
import com.vectory.utils.JsonUtil;
import com.vectory.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CommonReturnType login(UserLoginQO userLoginQo,
                                  HttpSession session,
                                  HttpServletResponse response) {
        userLoginQo.setPassword(MD5Util.MD5EncodeUtf8(userLoginQo.getPassword()));
        UserLoginVO userLoginVO = userMapper.selectForLogin(userLoginQo);
        if(userLoginVO == null)
            return CommonReturnType.fail(EmBusinessError.LOGIN_ERROR);
        CookieUtil.setCookie(response, GlobalContant.LOGIN_COOKIE, session.getId());
        JedisUtil.setEx(session.getId(), JsonUtil.obj2String(userLoginVO), GlobalContant.REDIS_SESSION_EXTIME);
        return CommonReturnType.success(userLoginVO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CommonReturnType register(UserRegisterQO userRegisterQo) {
        User user = new User();
        checkValid(userRegisterQo.getUsername(), GlobalContant.USERNAME);
        checkValid(userRegisterQo.getEmail(), GlobalContant.E_MAIL);
        BeanUtils.copyProperties(userRegisterQo, user);
        user.setPassword(MD5Util.MD5EncodeUtf8(userRegisterQo.getPassword()));
        user.setRole(GlobalContant.Role.ROLE_CUSTOMER);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return userMapper.insertSelective(user) > 0 ?
                CommonReturnType.success("注册成功")
                : CommonReturnType.fail(EmBusinessError.REGISTER_ERROR);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CommonReturnType checkValid(String checkValue, String paramName) {
        if(StringUtils.isNotBlank(paramName)) {
            switch (paramName) {
                case GlobalContant.USERNAME:
                    if (userMapper.checkValid(checkValue, paramName) > 0)
                        return CommonReturnType.fail(EmBusinessError.USERNAME_REPEAT);
                    else
                        return CommonReturnType.success("检验成功");
                case GlobalContant.E_MAIL:
                    if (userMapper.checkValid(checkValue, paramName) > 0)
                        return CommonReturnType.fail(EmBusinessError.EMAIL_REPEAT);
                    else
                        return CommonReturnType.success("检验成功");
                default: return CommonReturnType.fail(EmBusinessError.ILLEGAL_ARGUMENT);
            }
        }
        return CommonReturnType.fail(EmBusinessError.ILLEGAL_ARGUMENT);
    }

    @Override
    public CommonReturnType getUserInfo(HttpServletRequest request) {
        String loginToken = CookieUtil.getCookieValue(request, GlobalContant.LOGIN_COOKIE);
        UserLoginVO userLoginVO = null;
        if(StringUtils.isBlank(loginToken)
                || (userLoginVO = JsonUtil.string2Obj(JedisUtil.get(loginToken), UserLoginVO.class)) == null) {
            return CommonReturnType.fail(EmBusinessError.NO_LOGIN);
        }
        return CommonReturnType.success(userLoginVO);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CommonReturnType forgetGetQuestion(String username) {
        String question = userMapper.getQuestion(username);
        return StringUtils.isBlank(question) ?
                CommonReturnType.fail(EmBusinessError.NO_SECURITY_QUESTION)
                : CommonReturnType.success(question);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CommonReturnType forgetCheckAnswer(CheckAnswerQO checkAnswerQo) {
        if(userMapper.forgetCheckAnswer(checkAnswerQo) == 0)
            return CommonReturnType.fail(EmBusinessError.QUESTION_ANSWER_ERROR);
        String resetToken = UUID.randomUUID().toString().replaceAll("-", "");
        JedisUtil.setEx(GlobalContant.TOKEN_PREFIX + checkAnswerQo.getUsername(), resetToken, GlobalContant.RESET_TOKEN_EXTIME);
        return CommonReturnType.success(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CommonReturnType forgetResetPassword(ResetPasswordQO resetPasswordQO) {
        String resetToken = JedisUtil.get(GlobalContant.TOKEN_PREFIX + resetPasswordQO.getUsername());
        if(StringUtils.isBlank(resetToken) || StringUtils.equals(resetToken, resetPasswordQO.getResetToke())) {
            return CommonReturnType.success(EmBusinessError.RESET_TOKEN_ERROR);
        }
        resetPasswordQO.setNewPassword(MD5Util.MD5EncodeUtf8(resetPasswordQO.getNewPassword()));
        return userMapper.resetPassword(resetPasswordQO, new Date()) == 0 ?
                CommonReturnType.fail(EmBusinessError.USERINFO_UPDATE_ERROR)
                : CommonReturnType.success("密码重置成功");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CommonReturnType resetPassword(UpdatePasswordQO updatePasswordQO,
                                          HttpServletRequest request) {
        String loginToken = CookieUtil.getCookieValue(request, GlobalContant.LOGIN_COOKIE);
        UserLoginVO userLoginVO = null;
        if(StringUtils.isBlank(loginToken)
                || (userLoginVO = JsonUtil.string2Obj(JedisUtil.get(loginToken), UserLoginVO.class)) == null) {
            return CommonReturnType.fail(EmBusinessError.NO_LOGIN);
        }
        updatePasswordQO.setOldPassword(MD5Util.MD5EncodeUtf8(updatePasswordQO.getOldPassword()));
        updatePasswordQO.setNewPassword(MD5Util.MD5EncodeUtf8(updatePasswordQO.getNewPassword()));
        return userMapper.updatePassword(updatePasswordQO, userLoginVO.getUsername(), new Date()) == 0 ?
                CommonReturnType.fail(EmBusinessError.USERINFO_UPDATE_ERROR)
                : CommonReturnType.success("密码重置成功");
    }

    @Override
    public CommonReturnType updateInformation(UpdateUserInfoQO updateUserInfoQO,
                                              HttpServletRequest request) {
        String loginToken = CookieUtil.getCookieValue(request, GlobalContant.LOGIN_COOKIE);
        UserLoginVO userLoginVO = null;
        if(StringUtils.isBlank(loginToken)
                || (userLoginVO = JsonUtil.string2Obj(JedisUtil.get(loginToken), UserLoginVO.class)) == null) {
            return CommonReturnType.fail(EmBusinessError.NO_LOGIN);
        }
        User recode = new User();
        BeanUtils.copyProperties(userLoginVO, recode);
        BeanUtils.copyProperties(updateUserInfoQO, recode);
        int result = userMapper.updateByPrimaryKeySelective(recode);
        if(result == 0) {
            return CommonReturnType.fail(EmBusinessError.NO_LOGIN);
        }
        JedisUtil.setEx(loginToken, JsonUtil.obj2String(recode), GlobalContant.REDIS_SESSION_EXTIME);
        return CommonReturnType.success("个人信息更新成功");
    }

    @Override
    public CommonReturnType logout(HttpServletRequest request, HttpServletResponse response) {
        String loginToken = CookieUtil.getCookieValue(request, GlobalContant.LOGIN_COOKIE);
        if(StringUtils.isBlank(loginToken)
                || JsonUtil.string2Obj(JedisUtil.get(loginToken), UserLoginVO.class) == null) {
            return CommonReturnType.fail(EmBusinessError.NO_LOGIN);
        }
        CookieUtil.removeCookie(request, response, GlobalContant.LOGIN_COOKIE);
        JedisUtil.del(loginToken);
        return CommonReturnType.success("注销成功");
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CommonReturnType loginBackend(UserLoginQO userLoginQo,
                                  HttpSession session,
                                  HttpServletResponse response) {
        userLoginQo.setPassword(MD5Util.MD5EncodeUtf8(userLoginQo.getPassword()));
        UserLoginVO userLoginVO = userMapper.selectForLogin(userLoginQo);
        if(userLoginVO == null)
            return CommonReturnType.fail(EmBusinessError.LOGIN_ERROR);
        else if(userLoginVO.getRole() != GlobalContant.Role.ROLE_MANAGER) {
            return CommonReturnType.fail(EmBusinessError.USER_NO_PERMISSION);
        }
        CookieUtil.setCookie(response, GlobalContant.LOGIN_COOKIE, session.getId());
        JedisUtil.setEx(session.getId(), JsonUtil.obj2String(userLoginVO), GlobalContant.REDIS_SESSION_EXTIME);
        return CommonReturnType.success(userLoginVO);
    }
}
