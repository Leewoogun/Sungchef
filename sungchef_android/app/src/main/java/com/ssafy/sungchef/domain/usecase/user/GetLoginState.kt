package com.ssafy.sungchef.domain.usecase.user

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.requestdto.UserSnsIdRequestDTO
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.model.user.LoginState
import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoginState @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository
) {
    suspend fun getLoginStateCode(userSnsIdRequestDTO: UserSnsIdRequestDTO) : Flow<DataState<LoginState>> {
        return userDataStoreRepository.login(userSnsIdRequestDTO)
    }
}