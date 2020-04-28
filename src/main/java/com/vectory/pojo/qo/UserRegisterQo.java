package com.vectory.pojo.qo;

import com.vectory.utils.RegexpUtil;
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
public class UserRegisterQo implements Serializable {
    private static final long serialVersionUID = -5144153677091779979L;

    /**
     * 用户名
     */
    @Pattern(regexp = RegexpUtil.REGEX_USERNAME,
            message = "用户名由大小写字母、数字组成，长度为5-7个")
    private String username;

    /**
     * 用户密码，MD5加密
     */
    @Pattern(regexp = RegexpUtil.REGEX_PASSWORD,
            message = "密码由大小写字母、数字组成，长度为6-16个")
    private String password;

    @Pattern(regexp = RegexpUtil.REGEX_EMAIL,
            message = "邮箱格式不正确")
    private String email;

    @Pattern(regexp = RegexpUtil.REGEX_MOBILE,
            message = "手机号码格式不正确")
    private String phone;

    /**
     * 找回密码问题
     */
    private String question;

    /**
     * 找回密码答案
     */
    private String answer;
}
