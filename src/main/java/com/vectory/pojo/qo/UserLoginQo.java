package com.vectory.pojo.qo;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginQo implements Serializable {
    private static final long serialVersionUID = -5144153677091779979L;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
