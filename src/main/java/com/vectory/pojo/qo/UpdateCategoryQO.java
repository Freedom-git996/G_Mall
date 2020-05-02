package com.vectory.pojo.qo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "更新品类")
public class UpdateCategoryQO implements Serializable {
    private static final long serialVersionUID = -5144153677091779979L;

    /**
     * 类别Id
     */
    private Integer id;

    /**
     * 类别名称
     */
    private String name;
}
