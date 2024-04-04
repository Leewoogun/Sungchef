package com.sungchef.sungchef.util.result;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListResult<T> extends CommonResult {
	private List<T> data;
}
