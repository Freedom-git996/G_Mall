package com.vectory.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonReturnType implements Serializable {
    private static final long serialVersionUID = 2734789743624970908L;

    private int status;

    private String msg;

    private Object data;

    public static CommonReturnType success(Object result) {
        return CommonReturnType.create(result, 200);
    }

    public static CommonReturnType success(String msg) {
        return CommonReturnType.create(msg, 200);
    }

    public static CommonReturnType create(Object result, int status) {
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus(status);
        commonReturnType.setData(result);
        return commonReturnType;
    }

    public static CommonReturnType create(String msg, int status) {
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus(status);
        commonReturnType.setMsg(msg);
        return commonReturnType;
    }
}
