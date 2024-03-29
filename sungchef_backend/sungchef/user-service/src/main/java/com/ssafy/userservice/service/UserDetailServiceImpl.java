package com.ssafy.userservice.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.userservice.db.entity.User;
import com.ssafy.userservice.db.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// public class UserService implements UserDetailsService {


@Slf4j
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

	// private final RedisService redisService;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String userSnsId) throws UsernameNotFoundException {
		// String searchUser = redisService.getValues("user_" + userSnsId);
		try {
			Optional<User> searchUser =  userRepository.findByUserSnsId(userSnsId);
			if (searchUser.isEmpty()) throw new UsernameNotFoundException(userSnsId);
			return new org.springframework.security.core.userdetails.User(userSnsId, bCryptPasswordEncoder.encode(userSnsId),
				true, true, true, true,
				new ArrayList<>()
			);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
