package com.vectory.common.error;

public interface CommonError {
    int getErrorStatus();
    String getErrorMsg();
    CommonError setErrorMsg(String errorMsg);
}
