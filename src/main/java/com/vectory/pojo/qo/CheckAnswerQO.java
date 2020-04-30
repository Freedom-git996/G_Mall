package com.vectory.pojo.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "校验密保问题的答案是否正确")
public class CheckAnswerQO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 找回密码问题
     */
    @NotBlank(message = "密保问题不能为空")
    @ApiModelProperty(value = "密保问题")
    private String question;

    /**
     * 找回密码答案
     */
    @NotBlank(message = "密保问题答案不能为空")
    @ApiModelProperty(value = "密保问题答案")
    private String answer;
}
