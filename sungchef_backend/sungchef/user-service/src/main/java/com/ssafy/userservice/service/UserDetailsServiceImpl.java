package com.ssafy.userservice.service;

import com.ssafy.userservice.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

//@Service
//@RequiredArgsConstructor
public class UserDetailsServiceImpl{

//    @Override
//    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
//        return new User("email", "enctyptpassword"
//                , true, true, true, true,
//                new ArrayList<>()
//        );
//        User user = userRepository.findByLoginId(loginId)
//                .orElseThrow(() -> new UsernameNotFoundException("Failed: No User Info"));
//
//        return user;
//    }
}