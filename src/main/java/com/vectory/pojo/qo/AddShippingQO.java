package com.vectory.pojo.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "增加收货地址")
public class AddShippingQO implements Serializable {
    private static final long serialVersionUID = -5144153677091779979L;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "收货人id")
    private Integer userId;

    /**
     * 收货姓名
     */
    @NotBlank(message = "收货人姓名不能为空")
    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;

    /**
     * 收货固定电话
     */
    @NotBlank(message = "收货人固定电话不能为空")
    @ApiModelProperty(value = "收货人固定电话")
    private String receiverPhone;

    /**
     * 收货移动电话
     */
    @NotBlank(message = "收货人移动电话不能为空")
    @ApiModelProperty(value = "收货人移动电话")
    private String receiverMobile;

    /**
     * 省份
     */
    @NotBlank(message = "收货省份电话不能为空")
    @ApiModelProperty(value = "收货省份")
    private String receiverProvince;

    /**
     * 城市
     */
    @NotBlank(message = "收货城市不能为空")
    @ApiModelProperty(value = "收货城市")
    private String receiverCity;

    /**
     * 详细地址
     */
    @NotBlank(message = "收货详细地址不能为空")
    @ApiModelProperty(value = "收获详细地址")
    private String receiverAddress;

    /**
     * 邮编
     */
    @NotBlank(message = "收货地邮编不能为空")
    @ApiModelProperty(value = "收货地邮编")
    private String receiverZip;
}
