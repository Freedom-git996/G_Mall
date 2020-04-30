package com.vectory.pojo.qo;

import com.vectory.utils.RegexpUtil;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户重置密码入参")
public class ResetPasswordQO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @Pattern(regexp = RegexpUtil.REGEX_PASSWORD,
            message = "密码由大小写字母、数字组成，长度为6-16个")
    private String newPassword;

    @NotBlank(message = "token不能为空")
    private String resetToke;
}
