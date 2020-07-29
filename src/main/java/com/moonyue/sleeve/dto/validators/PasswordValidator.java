package com.moonyue.sleeve.dto.validators;

import cn.hutool.core.util.StrUtil;
import com.moonyue.sleeve.dto.RegisterDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordEqual, RegisterDTO> {

    @Override
    public boolean isValid(RegisterDTO registerDTO, ConstraintValidatorContext constraintValidatorContext) {
        String password = registerDTO.getPassword();
        String confirmPassword = registerDTO.getConfirmPassword();
        if(StrUtil.isBlank(password)){
            return true;
        }
        return password.equals(confirmPassword);
    }
}
