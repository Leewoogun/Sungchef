package com.ssafy.userservice.service;

import com.ssafy.userservice.db.entity.User;
import com.ssafy.userservice.db.repository.UserRepository;
import com.ssafy.userservice.util.exception.UserNotCreatedException;
import com.ssafy.userservice.util.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ssafy.userservice.dto.request.SignUpReq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	// private final FridgeRepository fridgeRepository;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;


	public User createUser(SignUpReq req) {

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
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String userNickName) throws UsernameNotFoundException {
		Optional<User> searchUser =  userRepository.searchUserByUserNickname(userNickName);

		if (!searchUser.isPresent()) throw new UsernameNotFoundException(userNickName);
		User user = searchUser.get();
		return new org.springframework.security.core.userdetails.User(user.getUserNickname(), user.getUserSnsId(),
				true, true, true, true,
				new ArrayList<>()
		);
	}
}
