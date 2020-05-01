package com.vectory.pojo.qo;

import com.vectory.utils.RegexpUtil;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户登录状态下修改密码入参")
public class UpdatePasswordQO implements Serializable {
    private static final long serialVersionUID = -5144153677091779979L;

    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    @Pattern(regexp = RegexpUtil.REGEX_PASSWORD,
            message = "密码由大小写字母、数字组成，长度为6-16个")
    private String newPassword;
}
