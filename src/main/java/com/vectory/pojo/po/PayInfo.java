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
public class PayInfo {
    private Integer id;

    /**
    * 用户id
    */
    private Integer userId;

    /**
    * 订单号
    */
    private Long orderNo;

    /**
    * 支付平台:1-支付宝,2-微信
    */
    private Integer payPlatform;

    /**
    * 支付宝支付流水号
    */
    private String platformNumber;

    /**
    * 支付宝支付状态
    */
    private String platformStatus;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;
}