package com.hanghae99.preonboardingbackend.controller;


import com.hanghae99.preonboardingbackend.Service.AuthService;
import com.hanghae99.preonboardingbackend.config.jwt.TokenProvider;
import com.hanghae99.preonboardingbackend.dto.CreateJwtRequest;
import com.hanghae99.preonboardingbackend.model.entity.Authority;
import com.hanghae99.preonboardingbackend.model.entity.UserRoleEnum;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final TokenProvider jwtUtil;
    private final AuthService authService;

    @Autowired
    public AuthController(TokenProvider jwtUtil, AuthService authService)
    { // 생성자 주입. jwtUtil 생성
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PostMapping("/create-jwt")
    public String createJwt(
            HttpServletResponse res,
            @RequestBody CreateJwtRequest createJwtRequest ) {

        return authService.createJwt(res, createJwtRequest);
    }

    @GetMapping("/get-jwt")
    public String getJwt(@CookieValue(TokenProvider.AUTHORIZATION_HEADER) String tokenValue) {
        // JWT 토큰 substring.
        String token = jwtUtil.substringToken(tokenValue);

        // 토큰 검증
        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }

        // 토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(token);
        // 사용자 username
        String username = info.getSubject();
        System.out.println("username = " + username);
        // 사용자 권한
        String authority = (String) info.get(TokenProvider.AUTHORIZATION_KEY);
        System.out.println("authority = " + authority);

        return "getJwt : " + username + ", " + authority;
    }

    @PostMapping("/create-auth")
    public Authority createAuthority(@RequestParam UserRoleEnum role) {
        return authService.createAuthority(role);
    }

}



