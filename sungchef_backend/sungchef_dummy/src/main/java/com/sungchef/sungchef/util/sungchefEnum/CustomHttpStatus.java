package com.sungchef.sungchef.util.sungchefEnum;

import lombok.Getter;

@Getter
public enum CustomHttpStatus {


    NEED_SIGNUP(210),
    NEED_SURVEY(211);


    private final int code;
    CustomHttpStatus(int _code) {
        code = _code;
    }
}
