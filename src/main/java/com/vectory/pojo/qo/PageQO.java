package com.vectory.pojo.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "分页参数")
public class PageQO implements Serializable {
    private static final long serialVersionUID = -5144153677091779979L;

    @Builder.Default
    @ApiModelProperty(value = "页码，默认为1")
    private Integer pageNum = 1;

    @Builder.Default
    @ApiModelProperty(value = "页的大小，默认为10")
    private Integer pageSize = 10;
}
