package com.ssafy.userservice.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.userservice.db.entity.User;
import com.ssafy.userservice.dto.request.LoginReq;
import com.ssafy.userservice.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

@Slf4j
@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment environment;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
        UserService userService, Environment environment) {
        super(authenticationManager);
        this.userService = userService;
        this.environment = environment;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
        throws AuthenticationException {
        try {

            LoginReq creds = new ObjectMapper().readValue(req.getInputStream(), LoginReq.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(creds.getUserSnsId(), creds.getUserSnsId(), new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // @Override
    // protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
    //     Authentication auth) throws IOException, ServletException {
    //
    //     String userSnsId = ((User) auth.getPrincipal()).getUserSnsId();
    //     UserDetails userDetails = userService.loadUserByUsername(userSnsId);

        // byte[] secretKeyBytes = Base64.getEncoder().encode(environment.getProperty("token.secret").getBytes());

        // SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        // Instant now = Instant.now();

        // String token = Jwts.builder()
        //     .subject(userDetails.getUserId())
        //     .expiration(Date.from(now.plusMillis(Long.parseLong(environment.getProperty("token.expiration_time")))))
        //     .issuedAt(Date.from(now))
        //     .signWith(secretKey)
        //     .compact();

        // res.addHeader("token", token);
        // res.addHeader("userId", userDetails.getUserId());
    // }
}