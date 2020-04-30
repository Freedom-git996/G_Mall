package com.vectory.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vectory.common.error.EmBusinessError;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "统一JSON返回格式")
public class CommonReturnType implements Serializable {
    private static final long serialVersionUID = 2734789743624970908L;

    @ApiModelProperty(value = "返回标志，0表示成功返回")
    private int status;

    @ApiModelProperty(value = "返回信息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private Object data;

    public static CommonReturnType success(Object result) {
        return CommonReturnType.create(result, 0);
    }

    public static CommonReturnType success(String msg) {
        return CommonReturnType.create(msg, 0);
    }

    public static CommonReturnType fail(String msg) {
        return CommonReturnType.create(msg, 1);
    }

    public static CommonReturnType fail(EmBusinessError emBusinessError) {
        return CommonReturnType.create(emBusinessError.getErrorMsg(), emBusinessError.getErrorStatus());
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
