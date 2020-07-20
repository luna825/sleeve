package com.moonyue.sleeve.dto.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = TokenPasswordValidator.class)
public @interface TokenPassword {

    int min() default 3;

    int max() default 32;

    String message() default "字段不符合要求";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
