package com.vectory.common.error;

public enum EmBusinessError implements CommonError {
    LOGIN_ERROR(1001, "用户不存在或密码错误"),
    REGISTER_ERROR(1002, "注册失败"),
    USERNAME_REPEAT(1003, "用户名已存在"),
    EMAIL_REPEAT(1003, "邮箱已被注册"),
    NO_LOGIN(1004, "暂无用户登录"),
    NO_SECURITY_QUESTION(1005, "该用户未设置密保问题"),
    QUESTION_ANSWER_ERROR(1006, "密保问题答案错误"),
    RESET_TOKEN_ERROR(1007, "token失效"),
    USERINFO_UPDATE_ERROR(1008, "信息更新失败"),
    USER_NO_PERMISSION(1009, "当前用户无权限"),

    CATEGORY_ADD_ERROR(2001, "新增品类失败"),
    CATEGORY_NOT_EXIST(2002, "品类信息不存在"),
    CATEGORY_UPDATE_ERROR(2003, "更新失败"),

    SHIPPING_ADD_ERROR(3001, "新增收货地址失败"),

    ILLEGAL_ARGUMENT(5001, "不合法参数"),
    SERVER_ERROR(5000, "服务器内部异常，请联系管理员")
    ;

    private int status;

    private String msg;

    EmBusinessError(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @Override
    public int getErrorStatus() {
        return status;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.msg = errorMsg;
        return this;
    }
}
