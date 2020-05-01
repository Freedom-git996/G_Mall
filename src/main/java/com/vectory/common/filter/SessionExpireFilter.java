package com.vectory.common.filter;

import com.vectory.common.GlobalContant;
import com.vectory.pojo.po.User;
import com.vectory.utils.CookieUtil;
import com.vectory.utils.JedisUtil;
import com.vectory.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SessionExpireFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;

        String loginToken = CookieUtil.getCookieValue(httpServletRequest, GlobalContant.LOGIN_COOKIE);
        if(StringUtils.isNotEmpty(loginToken)){
            String userJsonStr = JedisUtil.get(loginToken);
            User user = JsonUtil.string2Obj(userJsonStr, User.class);
            if(user != null){
                JedisUtil.expire(loginToken, GlobalContant.REDIS_SESSION_EXTIME);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
