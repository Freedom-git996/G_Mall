package com.vectory.controller.protal;

import com.vectory.common.CommonReturnType;
import com.vectory.common.aop.annotation.CheckLoginAnno;
import com.vectory.common.validate.ValidationResult;
import com.vectory.common.validate.ValidatorImpl;
import com.vectory.pojo.qo.AddShippingQO;
import com.vectory.service.IShippingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/shipping/")
@Api(tags = "前台-收货地址接口")
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    @Autowired
    private ValidatorImpl validator;

    @ResponseBody
    @CheckLoginAnno
    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    @ApiOperation(value = "添加收货地址", httpMethod = "POST")
    public CommonReturnType add(@ModelAttribute @RequestBody AddShippingQO addShippingQO, HttpServletRequest request) {
        ValidationResult result = validator.validate(addShippingQO);
        if (result.isHasErrors())
            return CommonReturnType.fail(result.getErrMsg());
        return shippingService.add(addShippingQO, request);
    }

    @ResponseBody
    @CheckLoginAnno
    @RequestMapping(value = "del.do", method = RequestMethod.POST)
    @ApiOperation(value = "删除收货地址", httpMethod = "POST")
    public CommonReturnType del(@ApiParam @RequestBody Integer shippingId, HttpServletRequest request) {

        return null;
    }
}
