package com.moonyue.sleeve.dto;

import com.moonyue.sleeve.core.bean.LoginType;
import com.moonyue.sleeve.dto.validators.EnumValue;
import com.moonyue.sleeve.dto.validators.TokenPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@TokenPassword(message = "{token.password}")
public class TokenGetDTO {

    @NotBlank(message = "account不能为空")
    private String account;

    private String password;

    private LoginType loginType;
}
