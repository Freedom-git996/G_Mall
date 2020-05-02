package com.vectory.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "品类信息响应对象")
public class CategoryVO implements Serializable {
    private static final long serialVersionUID = -5144153677091779979L;
    /**
    * 类别Id
    */
    @ApiModelProperty(value = "类别Id")
    private Integer id;

    /**
    * 父类别id当id=0时说明是根节点,一级类别
    */
    @ApiModelProperty(value = "父类别id当id=0时说明是根节点,一级类别")
    private Integer parentId;

    /**
    * 类别名称
    */
    @ApiModelProperty(value = "类别名称")
    private String name;

    /**
    * 类别状态1-正常,2-已废弃
    */
    @ApiModelProperty(value = "类别状态1-正常,2-已废弃")
    private Integer status;

    /**
    * 排序编号,同类展示顺序,数值相等则自然排序
    */
    @ApiModelProperty(value = "排序编号,同类展示顺序,数值相等则自然排序")
    private Integer sortOrder;

    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
    * 更新时间
    */
    @ApiModelProperty(value = "最后一次更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "子节点集")
    private List<CategoryVO> child;

    public CategoryVO(Integer id, Integer parentId, String name, Integer status,
                      Integer sortOrder, Date createTime, Date updateTime) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.status = status;
        this.sortOrder = sortOrder;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}