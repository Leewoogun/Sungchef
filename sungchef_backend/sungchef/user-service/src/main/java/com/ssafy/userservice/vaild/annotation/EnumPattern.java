package com.ssafy.userservice.vaild.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ssafy.userservice.vaild.vaildator.EnumPatternValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = EnumPatternValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumPattern {
	String regexp();
	String message() default "does not match \"{regexp}\"";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}