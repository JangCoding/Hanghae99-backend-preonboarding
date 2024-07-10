package com.hanghae99.preonboardingbackend.Service;

import com.hanghae99.preonboardingbackend.config.jwt.TokenProvider;
import com.hanghae99.preonboardingbackend.dto.CreateJwtRequest;
import com.hanghae99.preonboardingbackend.model.entity.Authority;
import com.hanghae99.preonboardingbackend.model.entity.UserRoleEnum;
import com.hanghae99.preonboardingbackend.repository.AuthRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final TokenProvider tokenProvider;

    @Autowired
    public AuthService(AuthRepository authRepository, TokenProvider tokenProvider){
        this.authRepository = authRepository;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public String createJwt(HttpServletResponse res, CreateJwtRequest request){
        // Jwt 생성
        String token = tokenProvider.createToken(request.getUsername(), UserRoleEnum.USER);

        // Jwt 쿠키 저장
        tokenProvider.addJwtToCookie(token, res);

        return token;
    }


    @Transactional
    public Authority createAuthority(UserRoleEnum role){
        Authority auth = new Authority();
        auth.setAuthorityName(role);
        return authRepository.save(auth);
    }
}
