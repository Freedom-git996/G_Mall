package com.vectory.pojo.qo;

import com.vectory.utils.RegexpUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户登录状态下修改个人信息入参")
public class UpdateUserInfoQO implements Serializable {
    private static final long serialVersionUID = -5144153677091779979L;

    @Pattern(regexp = RegexpUtil.REGEX_EMAIL,
            message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Pattern(regexp = RegexpUtil.REGEX_MOBILE,
            message = "手机号码格式不正确")
    @ApiModelProperty(value = "手机号码")
    private String phone;

    /**
     * 找回密码问题
     */
    @ApiModelProperty(value = "密保问题")
    private String question;

    /**
     * 找回密码答案
     */
    @ApiModelProperty(value = "密保答案")
    private String answer;
}
