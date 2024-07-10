package com.hanghae99.preonboardingbackend;


import com.hanghae99.preonboardingbackend.config.jwt.TokenProvider;
import com.hanghae99.preonboardingbackend.model.entity.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TokenProviderTest {
    private TokenProvider tokenProvider;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.token-validity-in-milliseconds}")
    private long tokenValidityInMilliseconds;

    @BeforeEach     // TokenProvider 인스턴스 초기화
    void setUp() {
        tokenProvider = new TokenProvider();
        // 테스트 시 프라이빗 필드나 메서드에 접근하고 수정하기 위해 사용
        // Java 리플렉션 API를 사용하여 접근 제어를 우회하고, 인스턴스의 상태를 직접 설정하거나 수정
        ReflectionTestUtils.setField(tokenProvider, "secretKey", secretKey);
        ReflectionTestUtils.setField(tokenProvider, "TOKEN_TIME", tokenValidityInMilliseconds);
        tokenProvider.init();
    }

    @Test
    void createToken_NullUsername() { // 유저명이 null인 경우
        // Given
        UserRoleEnum role = UserRoleEnum.USER;

        // When                     // 특정 예외가 발생하는지 확인
        Exception exception = assertThrows(IllegalArgumentException.class, () -> { // 예상하는 예외 클래스
            // 예외가 발생할 수 있는 코드 블록
            tokenProvider.createToken(null, role);
        });

        // Then
        assertEquals("Username and role must not be null", exception.getMessage());
    }

    @Test
    void createToken_NullRole() { // 역할이 null인 경우
        // Given
        String userName = "Jang";

        // When                     // 특정 예외가 발생하는지 확인
        Exception exception = assertThrows(IllegalArgumentException.class, () -> { // 예상하는 예외 클래스
            // 예외가 발생할 수 있는 코드 블록
            tokenProvider.createToken(userName, null);
        });

        // Then
        assertEquals("Username and role must not be null", exception.getMessage());
    }
}
