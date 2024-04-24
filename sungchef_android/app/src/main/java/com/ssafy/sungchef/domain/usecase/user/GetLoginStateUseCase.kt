package com.ssafy.sungchef.domain.usecase.user

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.requestdto.UserSnsIdRequestDTO
import com.ssafy.sungchef.domain.model.user.LoginState
import com.ssafy.sungchef.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoginStateUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun getLoginStateCode(userSnsIdRequestDTO: UserSnsIdRequestDTO) : Flow<DataState<LoginState>> {
        return userRepository.login(userSnsIdRequestDTO)
    }
}