package com.vectory.service.impl;

import com.vectory.common.CommonReturnType;
import com.vectory.common.GlobalContant;
import com.vectory.common.error.EmBusinessError;
import com.vectory.dao.ShippingMapper;
import com.vectory.pojo.po.Shipping;
import com.vectory.pojo.qo.AddShippingQO;
import com.vectory.pojo.vo.UserLoginVO;
import com.vectory.service.IShippingService;
import com.vectory.utils.CookieUtil;
import com.vectory.utils.JsonUtil;
import com.vectory.utils.jedis.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CommonReturnType add(AddShippingQO addShippingQO, HttpServletRequest request) {
        String loginToken = CookieUtil.getCookieValue(request, GlobalContant.LOGIN_COOKIE);
        UserLoginVO userLoginVO = JsonUtil.string2Obj(JedisUtil.get(loginToken), UserLoginVO.class);
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(addShippingQO, shipping);
        if(!addShippingQO.getUserId().equals(userLoginVO.getId())) {
            return CommonReturnType.fail(EmBusinessError.NO_LOGIN);
        }
        shipping.setUserId(userLoginVO.getId());
        shipping.setCreateTime(new Date());
        shipping.setUpdateTime(new Date());
        return shippingMapper.insertSelective(shipping) > 0 ?
                CommonReturnType.success("添加收获地址成功")
                : CommonReturnType.fail(EmBusinessError.SHIPPING_ADD_ERROR);
    }
}
