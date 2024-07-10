package com.hanghae99.preonboardingbackend.config.jwt;

import com.hanghae99.preonboardingbackend.exception.jwt.JwtAccessDeniedHandler;
import com.hanghae99.preonboardingbackend.exception.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//public class SecurityConfig {
//    private final TokenProvider tokenProvider;
//
//
//}


public class SecurityConfig {

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Bean                     //HttpSecurity 객체를 통해 보안 설정을 정의
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(httpBasic -> httpBasic.disable())    //httpBasic과 formLogin 필터를 비활성화
                .formLogin(formLogin -> formLogin.disable())
                .csrf(csrf -> csrf.disable())                   //H2 콘솔을 사용하기 위해 CSRF 보호를 비활성화
                .headers(headers -> headers                     //iframe 내에서 H2 콘솔이 제대로 작동하도록
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                .authorizeHttpRequests(authorize -> authorize   //요청에 대한 보안 규칙을 설정
                        .requestMatchers(
                                "/logIn",
                                "/signUp",
                                "/swagger-ui/**",
                                "v3/api-docs/**",
                                "/h2-console/**",
                                "/error"
                        ).permitAll()                           //경로에 대한 모든 요청을 허용
                        .anyRequest().authenticated()           //다른 모든 요청은 인증을 요구
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling   // 에러 처리 핸들러 설정
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                );

        return http.build();
    }
}