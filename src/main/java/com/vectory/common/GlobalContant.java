package com.vectory.common;

public class GlobalContant {

    // check_valid.do
    public static final String USERNAME = "username";
    public static final String E_MAIL = "email";

    // login
    public static final String LOGIN_COOKIE = "login_cookie";
    public static final int REDIS_SESSION_EXTIME = 60 * 30; //30分钟

    // security_question
    public static final String TOKEN_PREFIX = "token_";
    public static final int RESET_TOKEN_EXTIME = 60 * 5; // 5分钟

    public interface Role {
        int ROLE_MANAGER = 0;
        int ROLE_CUSTOMER = 1;
    }
}
