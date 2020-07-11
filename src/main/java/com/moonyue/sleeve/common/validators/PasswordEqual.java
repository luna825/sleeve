package com.moonyue.sleeve.common.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


/*
* documented 注释加入文档
* retention 保存到什么时候
* target 注解的作用目标
* */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordEqual {

    int Min() default 3;

    String message() default "passwords are not equal.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
