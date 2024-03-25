package com.ssafy.userservice.vaild.vaildator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.ssafy.userservice.vaild.annotation.EnumPattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumPatternValidator implements ConstraintValidator<EnumPattern, Enum<?>> {
	private Pattern pattern;

	@Override
	public void initialize(EnumPattern annotation) {
		try {
			pattern = Pattern.compile(annotation.regexp());
		} catch (PatternSyntaxException e) {
			throw new IllegalArgumentException("pattern regex is invalid", e);
		}
	}

	@Override
	public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}

		Matcher m = pattern.matcher(value.name());
		return m.matches();
	}
}