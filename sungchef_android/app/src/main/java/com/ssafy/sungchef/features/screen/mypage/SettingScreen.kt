package com.ssafy.sungchef.features.screen.mypage

import android.net.Uri
import android.os.Build

import android.util.Log
import android.widget.Toast

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.sungchef.R
import com.ssafy.sungchef.commons.BIRTH

import com.ssafy.sungchef.features.component.DatePickerDialogComponent
import com.ssafy.sungchef.features.component.FilledButtonComponent

import com.ssafy.sungchef.commons.EMPTY_NICKNAME
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.features.component.IconButtonComponent

import com.ssafy.sungchef.features.component.IconComponent
import com.ssafy.sungchef.features.component.ImageComponent
import com.ssafy.sungchef.features.component.MyPageGenderButtonComponent
import com.ssafy.sungchef.features.component.OutlinedButtonComponent
import com.ssafy.sungchef.features.component.SmallTextButtonComponent
import com.ssafy.sungchef.features.component.TextComponent
import com.ssafy.sungchef.features.component.TextFieldComponent
import com.ssafy.sungchef.features.component.TopAppBarComponent
import com.ssafy.sungchef.util.SocialLoginManager
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
    viewModel: SettingViewModel,
    onMoveSurveyPage : (Boolean) -> Unit,
    onMoveLoginPage : () -> Unit
){

    val userProfileImage by viewModel.userProfileImage.collectAsState()
    val userNickname by viewModel.userNickname.collectAsState()
    val userBirthDate by viewModel.userBirthDate.collectAsState()
    val userGender by viewModel.userGender.collectAsState()
    val uiState: BaseModel by viewModel.isDuplicateName.collectAsState()
    val isDuplicateCheckNeeded by viewModel.isDuplicateCheckNeeded.collectAsState()
    val isSaveCompleted by viewModel.isSaveCompleted.collectAsState()
    val context = LocalContext.current

//    val userNickname = "헤헿"
    LaunchedEffect(key1 = true){
        viewModel.getUserSettingInfo()
        viewModel.isSaveCompleted.value = false
    }

    LaunchedEffect(uiState){
        Log.d(TAG, "SettingScreen: uiState LaunchedEffect")
        if(uiState.message!="") {
            Log.d(TAG, "SettingScreen: uiState : $uiState")
            if(uiState.isSuccess) viewModel.setIsDuplicateCheckNeeded(false)
            else viewModel.setIsDuplicateCheckNeeded(true)
            Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
        }
    }

    if(isSaveCompleted) {
        viewModel.setIsSaveCompletedFalse()
        navController.popBackStack()
    }

    Scaffold(
        topBar = { TopAppBarComponent(
            navigationIcon = { IconButtonComponent(
                onClick = { navController.popBackStack() },
                painter = painterResource(id = R.drawable.icon_back)
            )},
            actions = { IconButtonComponent(
                onClick = {
                    if(viewModel.isDuplicateCheckNeeded.value == false){
                        Log.d(TAG, "SettingScreen: 최종 저장 $isDuplicateCheckNeeded")
                        Toast.makeText(context, "프로필이 업데이트 되었습니다.", Toast.LENGTH_SHORT).show()

                        //API 저장 필요//
                        viewModel.updateUserSettingInfo(context)
//                        navController.popBackStack()
                    }else{
                        Toast.makeText(context, "닉네임 중복체크를 먼저 진행해주세요.", Toast.LENGTH_SHORT).show()
                    }

                  },
                painter = painterResource(id = R.drawable.save),
                size = 36
            )}
        ) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
//                    .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center

            ) {

                Log.d(TAG, "SettingScreen: 초기화면 : $userNickname")
                
                SetProfileImage(userProfileImage, viewModel)

                Column(modifier = Modifier.background(Color.White)) {
                    Spacer(modifier = Modifier.height(10.dp))
                    SetNickname(userNickname, viewModel)
                    SetBirthDate(userBirthDate, viewModel)
                    SetGender(userGender, viewModel)
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Inquire(viewModel, onMoveSurveyPage)
                Logout(viewModel, onMoveLoginPage)
            }
        }
    }
}

@Composable
fun SetProfileImage(userProfileImage: String?, viewModel: SettingViewModel) {

    Column(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .background(Color.White)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

//        var imageUri by remember { mutableStateOf<Uri?>(null) }
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri : Uri?->
            if(uri!=null) viewModel.setProfileImage(uri.toString())
        }

        var uri : Uri? = userProfileImage?.let {
            Log.d(TAG, "SetProfileImage: $it")
            Uri.parse(it) 
        }

        // 선택된 이미지를 표시하는 Image 컴포넌트
        uri?.let { uri ->
            ImageComponent(modifier = Modifier
                .size(120.dp)
                .clip(CircleShape), imageResource = uri)
        } ?: run {
            // 기본 이미지 또는 초기 이미지를 표시
            ImageComponent(modifier = Modifier
                .size(120.dp)
                .clip(CircleShape), imageResource = R.drawable.account_circle)
        }

        TextComponent(
            text = "사진 수정",
            modifier = Modifier.clickable {
                galleryLauncher.launch("image/*")
            })

        Spacer(modifier = Modifier.height(10.dp))
    }


}

@Composable
fun SetNickname(userNickname: String, viewModel: SettingViewModel){
    val context = LocalContext.current
//    var text by remember { mutableStateOf("기본닉네임") }

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(Color.White)) {
        OutlinedTextField(
            value = userNickname,
            onValueChange = {
                viewModel.setIsDuplicateCheckNeeded(true)
                viewModel.setNickname(it) },
            label = { Text("닉네임") },
            modifier = Modifier
                .padding(start = 20.dp)
                .fillMaxWidth(0.6f)
//                .height(60.dp)
        )

        FilledButtonComponent(
            text = "중복확인",
            modifier = Modifier
                .fillMaxWidth()
//                .height(60.dp)
                .padding(start = 10.dp, end = 20.dp)
            ,
            shape = RoundedCornerShape(15)
        ) {
            if (!viewModel.checkNickname()){
                if (userNickname.isNotEmpty()) {
                    viewModel.isDuplicateNickname(userNickname)
                } else {
                    Toast.makeText(context, EMPTY_NICKNAME, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetBirthDate(userBirthDate: String, viewModel: SettingViewModel) {
    val context = LocalContext.current
    //    var birth by remember { mutableStateOf("1999-02-08") }

    //실제 유저 생일 가져와서 LocalDate로 매핑 필요
    val date = remember { mutableStateOf(LocalDate.parse(userBirthDate))}
    val isOpen = remember { mutableStateOf(false) }

    TextFieldComponent(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 5.dp)
            .clickable {
                isOpen.value = true
            },
        value = userBirthDate,
        onValueChange = {
            viewModel.setBirthDate(it)
        },
        hintText = BIRTH,
        trailingIcon = {
            IconButton(
                onClick = {
                    isOpen.value = true
                }
            ) {
                IconComponent(
                    size = 40,
                    painter = painterResource(id = R.drawable.icon_calendar)
                )
            }
        },
        enabled = false
    )

    if (isOpen.value) {

        DatePickerDialogComponent(
            onAccept = { time ->
                isOpen.value = false

                time?.let {
                    val dt = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                    Log.d(TAG, "SetBirthDate: $dt")
                    date.value = dt
                    viewModel.setBirthDate(date.value.toString())
                }
            },
            onCancel = {
                isOpen.value = false
            },
            initialDateMillis = LocalDate.parse(userBirthDate).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
    }
}

@Composable
fun SetGender(userGender: Boolean, viewModel: SettingViewModel) {
//    var isMale by remember { mutableStateOf(true) }

    Row(modifier = Modifier.padding(top = 10.dp)) {
        TextComponent(
            text = "성별", color = MaterialTheme.colorScheme.primary,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(start = 20.dp)
        )
        MyPageGenderButtonComponent(gender = "남자", genderResource = R.drawable.gender_man,
            modifier = Modifier
                .weight(1f)
                .height(60.dp)
                .padding(start = 20.dp)
                .clickable { viewModel.setGender(true) },
            color = if (userGender) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
        )
        Spacer(modifier = Modifier.width(10.dp))
        MyPageGenderButtonComponent(gender = "여자", genderResource = R.drawable.gender_woman,
            modifier = Modifier
                .weight(1f)
                .height(60.dp)
                .padding(end = 20.dp)
                .clickable { viewModel.setGender(false) },
            color = if (userGender) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.primary
            )
    }
}

@Composable
fun Inquire(
    viewModel: SettingViewModel,
    onMoveSurveyPage: (Boolean) -> Unit
) {
    val context = LocalContext.current
    viewModel.getEmail()
    val userEmail by viewModel.userEmail.collectAsState()

    Column(modifier = Modifier.padding(20.dp)) {

        var showDialog by remember { mutableStateOf(false) }

        OutlinedButtonComponent(text = "문의하기", borderColor = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(15),
            modifier = Modifier.padding(bottom = 10.dp),
            ) {
            showDialog = true;
        }
        OutlinedButtonComponent(text = "다시 설문하기", borderColor = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(15)) {
            onMoveSurveyPage(true)
        }


        // 문의하기 다이얼로그
        if (showDialog) {
            var email by remember { mutableStateOf("") }
            email = userEmail
            var text by remember { mutableStateOf("") } // 텍스트 필드의 현재 값을 저장하는 상태 변수
            var saveEmail by remember { mutableStateOf(false) }

            Dialog(onDismissRequest = { showDialog = false }) {
                // Dialog의 배경색과 모서리를 설정합니다.
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)), // 모서리 둥글게
                    color = MaterialTheme.colorScheme.surfaceVariant // 배경색 지정
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        Text("문의하기", style = MaterialTheme.typography.bodyLarge)

                        TextFieldComponent(value = email, onValueChange = {newtext -> email = newtext}, hintText = "이메일을 입력하세요."
                            , singleLine = true)
                        TextField(
                            value = text,
                            onValueChange = {newtext -> text = newtext},
                            placeholder = {Text("문의내용을 입력하세요")},
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(8.dp)
                                ) // 큰 네모칸의 느낌을 주기 위한 테두리
                                .padding(top = 8.dp, bottom = 8.dp), // 내부 패딩을 추가하여 입력 영역을 넓힘

                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                            maxLines = 5
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Checkbox(
                                checked = saveEmail,
                                onCheckedChange = { saveEmail = it }
                            )
                            Text(text="이메일 자동저장", fontSize = 16.sp)
                            Spacer(modifier = Modifier.weight(1f))
                            Button(onClick = {
                                if(email=="" || text=="" || !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+".toRegex())){
                                    Toast.makeText(context, "올바른 이메일과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                                }
                                else {
                                    //이메일 자동저장일 경우 앱 DB에 저장하고 메일 발송 후 종료
                                    if(saveEmail==true){
                                        viewModel.setEmail(email)
                                    }
                                    Toast.makeText(context, "문의가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                    viewModel.inquire(email, text)
                                    showDialog = false
                                }
                            }) {
                                Text("문의하기")
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Logout(viewModel: SettingViewModel, onMoveLoginPage: () -> Unit) {
    val context = LocalContext.current

    Column(modifier = Modifier
        .padding(end = 20.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.End) {
        SmallTextButtonComponent(text = "로그아웃", color = Color.Gray,
            modifier = Modifier.padding(bottom = 10.dp)){
            SocialLoginManager(context).kakaoLogout()
            viewModel.logout()
            onMoveLoginPage()
        }
//        SmallTextButtonComponent(text = "회원탈퇴", color = Color.Red)

    }
}

@Composable
@Preview
@RequiresApi(Build.VERSION_CODES.O)
fun SettingPreview(){
    val navController = rememberNavController()
    SettingScreen(navController = navController, hiltViewModel(), {true}, {})
}

