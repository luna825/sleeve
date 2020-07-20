package com.moonyue.sleeve.dto;

import com.moonyue.sleeve.dto.validators.PasswordEqual;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@Builder
@PasswordEqual(message="两次密码必须相同")
public class PersonDTO {
    @Length(min=2, max=10)
    private String name;
    private Integer age;

    private String password;
    private String confirmPassword;

}
