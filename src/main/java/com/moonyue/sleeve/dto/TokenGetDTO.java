package com.moonyue.sleeve.dto;

import com.moonyue.sleeve.core.bean.LoginType;
import com.moonyue.sleeve.dto.validators.TokenPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class TokenGetDTO {

    @NotBlank(message = "account不能为空")
    private String account;

    @TokenPassword(message = "{token.password}")
    private String password;

    private LoginType loginType;
}
