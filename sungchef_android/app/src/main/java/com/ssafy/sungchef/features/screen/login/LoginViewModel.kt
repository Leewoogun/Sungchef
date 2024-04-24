package com.ssafy.sungchef.features.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.requestdto.UserSnsIdRequestDTO
import com.ssafy.sungchef.domain.model.user.LoginState
import com.ssafy.sungchef.domain.usecase.user.GetLoginStateUseCase
import com.ssafy.sungchef.domain.usecase.user.SetLoginType
import com.ssafy.sungchef.domain.usecase.user.SetUserSnsIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "LoginViewModel_성식당"
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginStateUseCase: GetLoginStateUseCase,
    private val setLoginType : SetLoginType,
    private val setUserSnsIdUseCase: SetUserSnsIdUseCase
) : ViewModel(){

    private val _loginState = MutableStateFlow(LoginState())
    val loginState : StateFlow<LoginState> = _loginState

    private val _needSurvey = MutableStateFlow(false)
    val needSurvey = _needSurvey.asStateFlow()

    private val _movePageState = MutableStateFlow(0)
    val movePageState = _movePageState.asStateFlow()

    private val _dialogState = MutableStateFlow(false)
    val dialogState = _dialogState.asStateFlow()

    private val _isNextPageState = MutableStateFlow(false)
    val isNextPageState = _isNextPageState.asStateFlow()

    fun login(userSnsIdRequestDTO : UserSnsIdRequestDTO, loginType : String) {
        viewModelScope.launch {
            setLoginType.setLoginType(loginType)   // 로그인 타입 저장
            setUserSnsIdUseCase.setUserSnsId(userSnsIdRequestDTO.userSnsId) // uid 저장
            getLoginStateUseCase.getLoginStateCode(userSnsIdRequestDTO).collect {
                when (it) {
                    is DataState.Success -> {
                        _loginState.emit(it.data)
                        _needSurvey.emit(it.data.needSurvey)
                    }

                    is DataState.Error -> {
                        _loginState.emit(
                            LoginState(
                                code = it.apiError.code.toInt(),
                                message = it.apiError.message,
                                isLoading = false
                            )
                        )
                    }

                    is DataState.Loading -> {
                        _loginState.emit(
                            LoginState(
                                isLoading = true
                            )
                        )
                        delay(3000)
                    }
                }
            }
        }
    }

    fun movePage(
        loginState : LoginState,
        isNextPage : Boolean
    ) {
        when (loginState.code) {
            200 -> {
                // 200일 때 needSurvey 값에 따라 Home 화면으로 갈지
                // 설문조사 화면으로 갈지 정함
                if (loginState.needSurvey) {
                    changeDialogState(true)

                    if (isNextPage) {
                        _movePageState.value = MOVE_SURVEY_PAGE_CODE
                        initLoginStateCode()
                        changeDialogState(false)
                    }
                } else {
                    _movePageState.value = MOVE_HOME_PAGE_CODE
                }
            }

            400 -> {
                changeDialogState(true)
            }

            404 -> {
                changeDialogState(true)

                if (isNextPage) {
                    _movePageState.value = MOVE_SIGNUP_PAGE_CODE
                    initLoginStateCode()
                    changeDialogState(false)
                }
            }

            500 -> {
                changeDialogState(true)
            }
        }
    }

    fun initLoginStateCode(){
        _loginState.value = LoginState()
    }

    fun initMovePageState() {
        _movePageState.value = 0
    }

    fun changeDialogState(showDialog : Boolean) {
        _dialogState.value = showDialog
    }

    fun changeIsNextPageState(isNextPage : Boolean) {
        _isNextPageState.value = isNextPage
    }

    companion object {
        const val MOVE_HOME_PAGE_CODE = 100
        const val MOVE_SIGNUP_PAGE_CODE = 200
        const val MOVE_SURVEY_PAGE_CODE = 300
    }
}