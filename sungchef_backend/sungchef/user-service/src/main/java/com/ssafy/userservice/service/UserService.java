package com.ssafy.userservice.service;

import com.ssafy.userservice.config.security.jwt.JwtToken;
import com.ssafy.userservice.db.entity.User;
import com.ssafy.userservice.db.repository.UserRepository;
import com.ssafy.userservice.util.exception.UserNotCreatedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Component;

import com.ssafy.userservice.dto.request.SignUpReq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

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
	// private final BCryptPasswordEncoder bCryptPasswordEncoder;


	public JwtToken createUser(SignUpReq req) {

		User user = userRepository.save(User.builder()
						.userId(-1)
						// .userSnsId(bCryptPasswordEncoder.encode(req.getUserSnsId()))
						.userSnsId(req.getUserSnsId())
						.userGenderType(req.getUserGender())
						.userSnsType(req.getUserSnsType())
						.userNickname(req.getUserNickName())
						.userBirthDate(req.getUserBirthdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
				.build()
		);

		if (user.getUserId() == -1) throw new UserNotCreatedException(req.toString());

		// UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(req.getUserSnsId(), req.getUserSnsId());
		// Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		JwtToken token = jwtTokenProvider.generateToken(req.getUserSnsId());

		redisService.setValues("user_" + req.getUserSnsId(), token.getRefreshToken(), Duration.ofMillis(1000 * 60 * 60 * 36));
		return jwtTokenProvider.generateToken(req.getUserSnsId());
	}

	// @Override
	// public UserDetails loadUserByUsername(String userSnsId) throws UsernameNotFoundException {
	// 	Optional<User> searchUser =  userRepository.findByUserSnsId(userSnsId);
	//
	// 	if (searchUser.isEmpty()) throw new UsernameNotFoundException(userSnsId);
	// 	User user = searchUser.get();
	// 	return new org.springframework.security.core.userdetails.User(user.getUserSnsId(), user.getUserSnsId(),
	// 			true, true, true, true,
	// 			new ArrayList<>()
	// 	);
	// }
}
