package com.moonyue.sleeve.dto;

import com.moonyue.sleeve.dto.validators.PasswordEqual;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@PasswordEqual(message = "{password.equal}")
public class RegisterDTO {

    @NotBlank(message = "{nickname.not-blank}")
    @Length(message = "{nickname.length}", max = 10, min = 3)
    private String nickname;

    @Email
    private String email;

    @NotBlank(message = "{password.new.not-blank}")
    private String password;

    @NotBlank(message = "{password.confirm.not-blank}")
    private String confirmPassword;

}
