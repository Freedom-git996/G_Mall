package com.vectory.common.error;

public enum EmBusinessError implements CommonError {
    LOGIN_ERROR(10001, "用户不存在或密码错误"),
    USERNAME_REPEAT(1002, "用户名已存在"),
    EMAIL_REPEAT(1002, "邮箱已被注册"),

    ILLEGAL_ARGUMENT(501, "不合法参数"),
    SERVER_ERROR(500, "服务器内部异常，请联系管理员")
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
