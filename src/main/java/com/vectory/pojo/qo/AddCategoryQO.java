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
@ApiModel(value = "增加品类")
public class AddCategoryQO implements Serializable {
    private static final long serialVersionUID = -5144153677091779979L;
    /**
     * 父类别id当id=0时说明是根节点,一级类别
     */
    @ApiModelProperty(value = "上级品类的ID，没有即为0")
    private Integer parentId;

    /**
     * 类别名称
     */
    @NotBlank(message = "品类名不能为空")
    @ApiModelProperty(value = "品类名")
    private String name;
}
