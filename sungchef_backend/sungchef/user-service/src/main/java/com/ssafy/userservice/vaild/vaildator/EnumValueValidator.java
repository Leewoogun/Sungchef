package com.ssafy.userservice.vaild.vaildator;

import com.ssafy.userservice.vaild.annotation.EnumValue;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValue, Enum<?>> {

	private EnumValue enumValue;
	@Override
	public void initialize(EnumValue constraintAnnotation) {
		this.enumValue = constraintAnnotation;
	}

	@Override
	public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
		boolean result = false;
		Enum<?>[] enumValues = this.enumValue.enumClass().getEnumConstants();
		if (enumValues != null) {
			for (Object enumValue : enumValues) {
				if (value.equals(enumValue) || (this.enumValue.ignoreCase() && value.name().equalsIgnoreCase(enumValue.getClass().getName()))) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
}