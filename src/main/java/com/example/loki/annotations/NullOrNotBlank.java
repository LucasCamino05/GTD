package com.example.loki.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

// acepta valores nulos pero no en blanco
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NullOrNotBlank.NullOrNotBlankValidator.class)
public @interface NullOrNotBlank {
  String message() default "";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

  class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {
    public void initialize(NullOrNotBlank parameters) { }
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
      return value == null || value.trim().length() > 0;
    }
  }
}
