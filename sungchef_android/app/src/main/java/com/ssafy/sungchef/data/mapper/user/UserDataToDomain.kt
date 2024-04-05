package com.ssafy.sungchef.data.mapper.user

import com.ssafy.sungchef.commons.NEED_SURVEY
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.token.TokenResponse
import com.ssafy.sungchef.data.model.responsedto.user.LoginResponse
import com.ssafy.sungchef.domain.model.token.JwtToken
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.model.user.LoginState

fun APIError.toBaseModel() : BaseModel{
    return when (this.code) {
        200L -> BaseModel(
            true,
            "사용 가능한 닉네임 입니다."
        )
        409L -> BaseModel(
            false,
            "이미 존재하는 닉네임 입니다."
        )
        else -> BaseModel(
            false,
            "서버와의 연결이 불안정 합니다.\n잠시 후에 다시 시도해주세요."
        )
    }
}

fun TokenResponse.toJwtToken() : JwtToken {
    return JwtToken(
        this.accessToken,
        this.refreshToken
    )
}


fun LoginResponse.toJwtToken() : JwtToken {
    return JwtToken(
        this.jwtToken.accessToken,
        this.jwtToken.refreshToken
    )
}

fun<T> ResponseDto<T>.toLoginState(needSurvey : Boolean) : LoginState {
    return if (this.code == 200 && needSurvey) {
        LoginState(
            code = this.code,
            message = NEED_SURVEY,
            needSurvey = needSurvey
        )
    } else {
        LoginState(
            code = this.code,
            message = this.message,
            needSurvey = needSurvey
        )
    }
}



