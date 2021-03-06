package com.vectory.pojo.po;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shipping {
    private Integer id;

    /**
    * 用户id
    */
    private Integer userId;

    /**
    * 收货姓名
    */
    private String receiverName;

    /**
    * 收货固定电话
    */
    private String receiverPhone;

    /**
    * 收货移动电话
    */
    private String receiverMobile;

    /**
    * 省份
    */
    private String receiverProvince;

    /**
    * 城市
    */
    private String receiverCity;

    /**
    * 区/县
    */
    private String receiverDistrict;

    /**
    * 详细地址
    */
    private String receiverAddress;

    /**
    * 邮编
    */
    private String receiverZip;

    private Date createTime;

    private Date updateTime;
}