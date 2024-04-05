package com.ssafy.sungchef.features.screen.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.KAKAO
import com.ssafy.sungchef.commons.NAVER
import com.ssafy.sungchef.data.model.requestdto.UserSnsIdRequestDTO
import com.ssafy.sungchef.data.repository.UserDataStoreRepositoryImpl
import com.ssafy.sungchef.domain.model.user.LoginState
import com.ssafy.sungchef.features.component.AlertDialogComponent
import com.ssafy.sungchef.features.component.FilledButtonComponent
import com.ssafy.sungchef.features.component.ImageComponent
import com.ssafy.sungchef.features.component.LoginImageComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.screen.survey.SurveyScreen
import com.ssafy.sungchef.features.ui.theme.primaryContainer80
import com.ssafy.sungchef.util.SocialLoginManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toCollection


private const val TAG = "LoginScreen_성식당"
@Composable
fun LoginScreen(
    viewModel : LoginViewModel,
    onMoveSignupPage : () -> Unit,
    onMoveSurveyPage : () -> Unit,
    onMoveHomePage : () -> Unit
) {

    val context = LocalContext.current
    val socialLoginManager = SocialLoginManager(context) // 소셜 로그인의 이벤트를 관리하는 객체

    val loginState : LoginState by viewModel.loginState.collectAsState()

    val movePageState : Int by viewModel.movePageState.collectAsState()
    val dialogState : Boolean by viewModel.dialogState.collectAsState()
    val isNextPageState : Boolean by viewModel.isNextPageState.collectAsState()

    Log.d(TAG, "LoginScreen: $loginState")
    Log.d(TAG, "isNextPageState: $isNextPageState")


    ShowLoadingDialog(isLoading = loginState.isLoading)

    viewModel.movePage(
        loginState = loginState,
        isNextPage = isNextPageState
    )

    movePage(
        movePageState = movePageState,
        viewModel = viewModel,
        onMoveSignupPage = onMoveSignupPage,
        onMoveHomePage = onMoveHomePage,
        onMoveSurveyPage = onMoveSurveyPage,
    )

    ShowMovePageDialog(
        viewModel = viewModel,
        dialogState = dialogState,
        loginState = loginState,
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .padding(top = 100.dp)
        )
        ImageComponent(
            modifier = Modifier
                .size(300.dp),
            imageResource = R.drawable.sungchef
        )

        Spacer(
            modifier = Modifier
                .padding(top = 40.dp)
        )

        LoginImageComponent(
            modifier = Modifier
                .width(300.dp)
                .height(60.dp)
                .clickable {
                    socialLoginManager.kakaoLogin(viewModel = viewModel)
                },
            imageResource = R.drawable.kakao_login,
        )

        Spacer(
            modifier = Modifier
                .padding(top = 20.dp)
        )

        LoginImageComponent(
            modifier = Modifier
                .width(300.dp)
                .height(60.dp)
                .clickable {
                    socialLoginManager.naverLogin(viewModel = viewModel)
                },
            imageResource = R.drawable.naver_login
        )
    }
}

@Composable
fun ShowLoadingDialog(
    isLoading : Boolean
) {
    if (isLoading) {
        Dialog(
            onDismissRequest = {
                
            },
            // 다이얼로그 시 뒤로 가기와 바깥 클릭 시 종료 안되게
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.loading_animation)
            )

            LottieAnimation(
                modifier = Modifier
                    .size(300.dp),
                composition = composition,
                iterations = 50 // 애니메이션을 50번 반복
            )
        }
    }
}

fun movePage(
    movePageState : Int,
    viewModel : LoginViewModel,
    onMoveSignupPage: () -> Unit,
    onMoveHomePage: () -> Unit,
    onMoveSurveyPage: () -> Unit
) {
    when (movePageState) {
        LoginViewModel.MOVE_SIGNUP_PAGE_CODE -> {
            onMoveSignupPage()
            viewModel.initLoginStateCode()
        }

        LoginViewModel.MOVE_HOME_PAGE_CODE -> {
            onMoveHomePage()
            viewModel.initLoginStateCode()
        }

        LoginViewModel.MOVE_SURVEY_PAGE_CODE -> {
            onMoveSurveyPage()
            viewModel.initLoginStateCode()
        }
    }

}

@Composable
fun ShowMovePageDialog(
    viewModel : LoginViewModel,
    dialogState : Boolean,
    loginState : LoginState,
){
    if (dialogState) {
        AlertDialog(
            text = {
                TextComponent(
                    text = loginState.message,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            onDismissRequest = {
                viewModel.changeDialogState(false)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.changeIsNextPageState(true)
                        viewModel.changeDialogState(false)
                    }
                ) {
                    TextComponent(text = "확인")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.changeDialogState(false)
                    }
                ) {
                    TextComponent(text = "취소")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginBodyPreview() {
    LoginScreen(
        viewModel = hiltViewModel(),
        {},
        {},
        {}
    )
}