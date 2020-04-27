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
public class Cart {
    private Integer id;

    private Integer userId;

    /**
    * 商品id
    */
    private Integer productId;

    /**
    * 数量
    */
    private Integer quantity;

    /**
    * 是否选择,1=已勾选,0=未勾选
    */
    private Integer checked;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;
}