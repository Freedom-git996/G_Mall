package com.vectory.common.aop;

import com.vectory.common.GlobalContant;
import com.vectory.common.error.BusinessException;
import com.vectory.common.error.EmBusinessError;
import com.vectory.pojo.vo.UserLoginVO;
import com.vectory.utils.CookieUtil;
import com.vectory.utils.JsonUtil;
import com.vectory.utils.jedis.JedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class CheckLoginAop {

    @Before("@annotation(com.vectory.common.aop.annotation.CheckLoginAnno)")
    public void checkLoginMethod(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for(Object o : args) {
            if(o instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest)o;
                String loginToken = CookieUtil.getCookieValue(request, GlobalContant.LOGIN_COOKIE);
                if(StringUtils.isBlank(loginToken)
                        || JsonUtil.string2Obj(JedisUtil.get(loginToken), UserLoginVO.class) == null) {
                    throw new BusinessException(EmBusinessError.NO_LOGIN);
                }
            }
        }
    }
}
