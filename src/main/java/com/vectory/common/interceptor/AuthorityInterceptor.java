package com.vectory.common.interceptor;

import com.google.common.collect.Maps;
import com.vectory.common.CommonReturnType;
import com.vectory.common.GlobalContant;
import com.vectory.common.error.EmBusinessError;
import com.vectory.pojo.po.User;
import com.vectory.utils.CookieUtil;
import com.vectory.utils.jedis.JedisUtil;
import com.vectory.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

public class AuthorityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod)handler;

        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getSimpleName();

        StringBuffer requestParamBuffer = new StringBuffer();
        Map paramMap = request.getParameterMap();
        for (Object o : paramMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String mapKey = (String) entry.getKey();

            String mapValue = StringUtils.EMPTY;

            // request这个参数的map,里面的value返回值是一个String[]
            Object obj = entry.getValue();
            if (obj instanceof String[]) {
                String[] strs = (String[]) obj;
                mapValue = Arrays.toString(strs);
            }
            requestParamBuffer.append(mapKey).append("=").append(mapValue);
        }

        if(StringUtils.equals(className, "UserManageController") && StringUtils.equals(methodName, "login")) {
            // 返回 true, 直接把请求丢给 controller
            return true;
        }

        User currentUser = null;

        String loginToken = CookieUtil.getCookieValue(request, GlobalContant.LOGIN_COOKIE);
        if(StringUtils.isNotEmpty(loginToken)){
            currentUser = JsonUtil.string2Obj(JedisUtil.get(loginToken), User.class);
        }

        if(currentUser == null || (currentUser.getRole() != GlobalContant.Role.ROLE_MANAGER)){
            // 返回 false， 即不会再调用controller里的方法
            response.reset(); // 必须重置response
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");

            PrintWriter out = response.getWriter();

            if(currentUser == null){
                if(StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "richtextImgUpload")){
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "请登录管理员");
                    out.print(JsonUtil.obj2String(resultMap));
                }else {
                    out.print(JsonUtil.obj2String(CommonReturnType.fail(EmBusinessError.NO_LOGIN)));
                }
            }else{
                if(StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "richtextImgUpload")){
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "无权限操作");
                    out.print(JsonUtil.obj2String(resultMap));
                }else{
                    out.print(JsonUtil.obj2String(CommonReturnType.fail(EmBusinessError.USER_NO_PERMISSION)));
                }
            }
            out.flush();
            out.close();

            return false;
        }
        return true;
    }
}
