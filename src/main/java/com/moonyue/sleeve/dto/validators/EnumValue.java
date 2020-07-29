package com.moonyue.sleeve.dto.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 枚举校验
 * 校验的值必须为 target (枚举类)中的一项
 *
 * @author pedro@TaleLin
 * @author jUZI@TaleLin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@Constraint(validatedBy = EnumValueValidator.class)
public @interface EnumValue {

    /**
     * 目标值，必须是一个枚举类
     */
    Class<? extends Enum<?>> target();

    String message() default "value must in enum";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
