package com.vectory.common.error;

public class BusinessException extends Exception implements CommonError {

    private CommonError commonError;

    public BusinessException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    @Override
    public int getErrorStatus() {
        return commonError.getErrorStatus();
    }

    @Override
    public String getErrorMsg() {
        return commonError.getErrorMsg();
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.commonError.setErrorMsg(errorMsg);
        return this;
    }
}
