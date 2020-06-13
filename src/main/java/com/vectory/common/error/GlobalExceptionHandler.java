package com.vectory.common.error;

import com.vectory.common.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public CommonReturnType handlerBusinessException(BusinessException ex) {
        return CommonReturnType.create(ex.getErrorMsg(), ex.getErrorStatus());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonReturnType handlerNotFoundException(NoHandlerFoundException ex)
    {
        return CommonReturnType.create("请求的资源不可用", 404);
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public CommonReturnType handleHttpMediaTypeNotSupportedException(Exception e) {
        return CommonReturnType.create("内容类型不支持", 415);
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonReturnType handleHttpRequestMethodNotSupportedException(Exception e) {
        return CommonReturnType.create("不合法的请求方法", 405);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonReturnType handleException(Exception ex) {
        return CommonReturnType.create(ex.getMessage(), 500);
    }
}
