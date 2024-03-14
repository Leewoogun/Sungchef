package com.sungchef.sungchef.util.result;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult implements Serializable {
	private int code;

	private String message;
}

