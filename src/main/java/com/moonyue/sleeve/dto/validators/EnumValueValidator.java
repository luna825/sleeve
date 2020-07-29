package com.moonyue.sleeve.dto.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {

    /**
     * 枚举类
     */
    private Class<?> cls;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        cls = constraintAnnotation.target();
    }


    /**
     * 校验
     *
     * @param value   传入值
     * @param context 上下文
     * @return 是否成功
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if( value == null){
            // 不能为空
            return false;
        }

        try{
            Object[] objs = cls.getEnumConstants();
            Method method = cls.getMethod("getValue");
            for(Object obj: objs){
                Object val = method.invoke(obj);
                if(val.equals(value)){
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
