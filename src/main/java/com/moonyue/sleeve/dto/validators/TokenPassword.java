package com.moonyue.sleeve.dto.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Constraint(validatedBy = TokenPasswordValidator.class)
public @interface TokenPassword {

    String message() default "password must not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
