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
@ApiModel(value = "用户注册入参")
public class UserRegisterQO implements Serializable {
    private static final long serialVersionUID = -5144153677091779979L;

    /**
     * 用户名
     */
    @Pattern(regexp = RegexpUtil.REGEX_USERNAME,
            message = "用户名以字母或数字开头，由大小写字母、数字、下划线组成，长度为5-7个")
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 用户密码，MD5加密
     */
    @Pattern(regexp = RegexpUtil.REGEX_PASSWORD,
            message = "密码由大小写字母、数字组成，长度为6-16个")
    @ApiModelProperty(value = "密码")
    private String password;

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
    @ApiModelProperty(value = "密保问题答案")
    private String answer;
}
