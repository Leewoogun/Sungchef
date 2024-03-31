package com.ssafy.userservice.db.entity;


import com.ssafy.userservice.util.sungchefEnum.UserGenderType;
import com.ssafy.userservice.util.sungchefEnum.UserSnsType;
import com.ssafy.userservice.vaild.annotation.EnumValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="suser")
@Getter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "suser_id")
	private int userId;

	@Column(name = "suser_nickname", nullable = false, length = 20)
	private String userNickname;

	@Column(name =  "suser_sns_id", nullable = false, length = 20)
	private String userSnsId;

	@Column(name =  "suser_sns_type")
	@Enumerated(EnumType.STRING)
	private UserSnsType userSnsType;

	@Column(name =  "suser_gender", nullable = false, length = 5)
	@Enumerated(EnumType.STRING)
	private UserGenderType userGenderType;

	@Column(name = "suser_birthdate", nullable = false, length = 20)
	private String userBirthDate;

	@Column(name =  "suser_image", nullable = true, length = 100)
	private String userImage;

	@Column(name = "suser_isactive", nullable = true)
	private boolean userIsActive;

	@Column(name = "suser_issurvey", nullable = true)
	private boolean userIsSurvey;

	@Column(name = "suser_password", nullable = false)
	private String userPassword;

	public void updateUserInfo(String userNickName, String userBirthDate, UserGenderType userGenderType) {
		this.userNickname = userNickName;
		this.userBirthDate = userBirthDate;
		this.userGenderType = userGenderType;
	}
	public void updateUserImage(String userImage) {
		this.userImage = userImage;
	}

	public void userSurveySuccess() {
		this.userIsSurvey = true;
	}
}
