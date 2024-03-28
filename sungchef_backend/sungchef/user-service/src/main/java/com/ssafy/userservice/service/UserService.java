package com.ssafy.userservice.service;

import com.ssafy.userservice.config.security.jwt.JwtToken;
import com.ssafy.userservice.config.security.jwt.JwtTokenProvider;
import com.ssafy.userservice.db.entity.User;
import com.ssafy.userservice.db.repository.UserRepository;
import com.ssafy.userservice.dto.request.LoginReq;
import com.ssafy.userservice.util.exception.JwtExpiredException;
import com.ssafy.userservice.util.exception.NicknameExistException;
import com.ssafy.userservice.util.exception.UserNotCreatedException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ssafy.userservice.dto.request.SignUpReq;
import com.ssafy.userservice.util.exception.UserNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
// public class UserService implements UserDetailsService {
public class UserService {

	// private final FridgeRepository fridgeRepository;
	private final UserRepository userRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisService redisService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public boolean userExist(String userSnsId) {
		Optional<User> user = userRepository.findByUserSnsId(userSnsId);
		return user.isPresent();
	}

	public boolean nicknameExist(String userNickname) {
		Optional<User> user = userRepository.findByUserNickname(userNickname);
		return user.isPresent();
	}

	@Transactional
	public JwtToken createUser(SignUpReq req) {
		if (userExist(req.getUserSnsId())) throw new UserNotFoundException("이미 존재하는 유저");
		if (nicknameExist(req.getUserSnsId())) throw new NicknameExistException("이미 존재하는 닉네임");

		User user = userRepository.save(User.builder()
						.userId(-1)
						.userPassword(bCryptPasswordEncoder.encode(req.getUserSnsId()))
						.userSnsId(req.getUserSnsId())
						.userGenderType(req.getUserGender())
						.userSnsType(req.getUserSnsType())
						.userNickname(req.getUserNickName())
						.userBirthDate(req.getUserBirthdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
				.build()
		);

		if (user.getUserId() == -1) throw new UserNotCreatedException("유저 생성 실패");

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(req.getUserSnsId(), req.getUserSnsId());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		JwtToken token = jwtTokenProvider.generateToken(authentication, req.getUserSnsId());
		return token;
	}

	@Transactional
	public JwtToken loginUser(LoginReq req) {

		Optional<User> selectUser = userRepository.findByUserSnsId(req.getUserSnsId());

		if (selectUser.isEmpty()) throw new UserNotFoundException("유저가 존재하지 않음");
		User user = selectUser.get();

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserSnsId(), user.getUserSnsId());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		return jwtTokenProvider.generateToken(authentication, req.getUserSnsId());
	}

	public JwtToken reissue(String refreshToken) {
		return jwtTokenProvider.generateTokenFromRefreshToken(refreshToken.substring(7));
	}
}
