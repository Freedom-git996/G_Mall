package com.vectory.service;

import com.vectory.common.CommonReturnType;
import com.vectory.pojo.qo.AddShippingQO;

import javax.servlet.http.HttpServletRequest;

public interface IShippingService {
    CommonReturnType add(AddShippingQO addShippingQO, HttpServletRequest request);
}
