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
@ApiModel(value = "检查用户名是否有效，同样适用于邮箱")
public class CheckValidQO implements Serializable {
    private static final long serialVersionUID = -5144153677091779979L;

    @NotBlank(message = "待校验的参数值不能为空")
    @ApiModelProperty(value = "参数值")
    private String str;

    @NotBlank(message = "待校验参数名不能为空")
    @ApiModelProperty(value = "参数名")
    private String type;
}
