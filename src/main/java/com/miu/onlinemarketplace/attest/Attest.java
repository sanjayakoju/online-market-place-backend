package com.miu.onlinemarketplace.attest;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Constraint(validatedBy = AttestValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Attest {
    String message() default "Invalid input";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
