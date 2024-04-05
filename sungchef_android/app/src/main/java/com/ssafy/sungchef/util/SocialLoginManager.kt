package com.ssafy.sungchef.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.ssafy.sungchef.commons.KAKAO
import com.ssafy.sungchef.commons.NAVER
import com.ssafy.sungchef.data.model.requestdto.UserSnsIdRequestDTO
import com.ssafy.sungchef.features.screen.login.LoginViewModel

private const val TAG = "SocialLoginManager_성식당"
class SocialLoginManager(
    private val context : Context
) {

    /**
     * 카카오 로그인을 관리
     */
    fun kakaoLogin(
        viewModel : LoginViewModel
    ){
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                getKakaoUserInfo(viewModel)
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    getKakaoUserInfo(viewModel)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun getKakaoUserInfo(viewModel : LoginViewModel) {
        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(
                    TAG, "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                val userSnsIdRequestDto =  UserSnsIdRequestDTO(user.id.toString())
                Log.d(TAG, "getKakaoUserInfo: 정보를 받습니다.")
                viewModel.login(userSnsIdRequestDto, KAKAO)
            }
        }
    }

    fun kakaoLogout() {
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(TAG, "연결 끊기 실패", error)
            }
            else {
                Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }


    /**
     * 네이버 로그인을 관리
     * authenticate() 를 이용한 로그인
     */

    fun naverLogin(
        viewModel : LoginViewModel
    ){
        /**
         * OAuthLoginCallback을 authenticate() 메서드 호출 시 파라미터로 전달하거나 NidOAuthLoginButton 객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다.
         */
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                getNaverUserInfo(context, viewModel)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()

                Toast.makeText(context, errorDescription, Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
        NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
    }

    private fun getNaverUserInfo(context : Context, viewModel : LoginViewModel) {
        // 네이버 로그인 API 호출 성공 시 유저 정보를 가져온다
        NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(result: NidProfileResponse) {
                val naverId = result.profile?.id.toString()
                val userSnsIdRequestDto =  UserSnsIdRequestDTO(naverId)
                viewModel.login(userSnsIdRequestDto, NAVER)
                Log.i(
                    TAG, "사용자 정보 요청 성공" +
                        "\n회원번호: ${result.profile?.id}" +
                        "\n이메일: ${result.profile?.email}" +
                        "\n닉네임: ${result.profile?.nickname}")
            }

            override fun onError(errorCode: Int, message: String) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(httpStatus: Int, message: String) {

            }
        })
    }
}