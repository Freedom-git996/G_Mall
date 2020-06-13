package com.vectory.controller.backend;

import com.vectory.common.CommonReturnType;
import com.vectory.pojo.qo.PageQO;
import com.vectory.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/manage/product/")
@Api(tags = "后台-商品接口")
public class ProductManageController {

    @Autowired
    private IProductService productService;

    @ResponseBody
    @RequestMapping(value = "list.do")
    @ApiOperation(value = "产品列表", httpMethod = "POST")
    public CommonReturnType list(@ApiParam(value = "分页参数") PageQO pageQO) {

        return null;
    }

    @ResponseBody
    @RequestMapping(value = "upload.do")
    @ApiOperation(value = "图片上传", httpMethod = "POST")
    public CommonReturnType list(@RequestParam(value = "upload_file", required = false) MultipartFile file) {

        return null;
    }
}
