package com.sungchef.sungchef.util.result;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends CommonResult implements Serializable {
	private T data;
}
