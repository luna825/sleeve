package com.moonyue.sleeve.dto.validators;

import cn.hutool.core.util.StrUtil;
import com.moonyue.sleeve.core.bean.LoginType;
import com.moonyue.sleeve.dto.TokenGetDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TokenPasswordValidator implements ConstraintValidator<TokenPassword, TokenGetDTO> {


    @Override
    public boolean isValid(TokenGetDTO tokenGetDTO, ConstraintValidatorContext constraintValidatorContext) {
        String password = tokenGetDTO.getPassword();
        if(StrUtil.isEmpty(password)
                && tokenGetDTO.getLoginType() == LoginType.USER_WX
        ){
            return true;
        }
        return StrUtil.isNotBlank(password);
    }

}
