package com.moonyue.sleeve.dto.validators;

import cn.hutool.core.util.StrUtil;
import com.moonyue.sleeve.dto.PersonDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordEqual, PersonDTO> {

    private int min;

    @Override
    public void initialize(PasswordEqual constraintAnnotation) {
        this.min = constraintAnnotation.Min();
    }

    @Override
    public boolean isValid(PersonDTO personDTO, ConstraintValidatorContext constraintValidatorContext) {
        String password = personDTO.getPassword();
        String confirmPassword = personDTO.getConfirmPassword();
        if(StrUtil.isBlank(password) || password.length() < this.min){
            return false;
        }
        return password.equals(confirmPassword);
    }
}
