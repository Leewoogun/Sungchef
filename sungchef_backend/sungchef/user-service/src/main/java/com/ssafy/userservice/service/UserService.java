package com.ssafy.userservice.service;

import com.ssafy.userservice.config.security.SecurityConfig;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ssafy.userservice.db.repository.UserRepository;
import com.ssafy.userservice.db.repository.UserRepositoryImpl;
import com.ssafy.userservice.dto.request.SignUpReq;
import com.ssafy.userservice.dto.request.UserInfoReq;
import com.ssafy.userservice.dto.response.UserInfoRes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService {

	// private final FridgeRepository fridgeRepository;
	// private final UserRepositoryImpl userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;


	public int createUser(SignUpReq req) {
		String s = req.getUserSnsId();
		String encode = bCryptPasswordEncoder.encode(s);
		log.info("encode Id: {}", encode);
		log.info("equals : {}", bCryptPasswordEncoder.matches(s, encode));
		// log.info("userres : {}", passwordEncoder.matches("1234", req.getUserSnsId()));
		return 1;
		// return userRepository.insertUser(req);
	}
}
