package com.hanghae99.preonboardingbackend;


import com.hanghae99.preonboardingbackend.config.jwt.TokenProvider;
import com.hanghae99.preonboardingbackend.model.entity.UserRoleEnum;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest

public class TokenProviderTest {
    private static final Logger log = LoggerFactory.getLogger(TokenProviderTest.class);
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
    public void init_InvalidBase64SecretKey() { // 시크릿 키가 Base64 형태가 아닌 경우
        // Given
        ReflectionTestUtils.setField(tokenProvider, "secretKey", "invalid-test-key");

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> { // 예상하는 예외 클래스
            tokenProvider.init();   // 예외가 발생할 수 있는 코드 블록
        });

        // Then
        assertEquals("Invalid secret key. Unable to decode.", exception.getMessage());
    }

    @Test
    void createToken_NullUsername() { // 유저명이 null인 경우
        // Given
        UserRoleEnum role = UserRoleEnum.USER;
        ReflectionTestUtils.setField(tokenProvider, "secretKey", "12345");

        // When                     // 특정 예외가 발생하는지 확인
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tokenProvider.createToken(null, role);
        });

        // Then
        assertEquals("Username and role must not be null", exception.getMessage());
    }

    @Test
    void createToken_NullRole() { // 역할이 null인 경우
        // Given
        String userName = "Jang";

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tokenProvider.createToken(userName, null);
        });

        // Then
        assertEquals("Username and role must not be null", exception.getMessage());
    }


    @Test
    public void validateToken_InvalidSignature(){ // 토큰의 서명이 일치하지 않는 경우
        //Given
        Key invalidKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 새로운 비밀 키 생성

        String token = "Bearer " +
                Jwts.builder()
                        .setSubject("testUserName")
                        .claim("auth", UserRoleEnum.USER)
                        .setExpiration(new Date(new Date().getTime() + 10000))
                        .setIssuedAt(new Date())
                        .signWith(invalidKey, SignatureAlgorithm.HS256) // 새로운 비밀 키로 서명
                        .compact();

        token = tokenProvider.substringToken(token);

        //When
        String finalToken = token;
        Exception exception = assertThrows(SecurityException.class, () -> {
            tokenProvider.validateToken(finalToken);
        });

        // Then
        assertEquals("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", exception.getMessage());    }

    @Test
    public void validateToken_ExpiredToken(){ // 토큰의 유효기간이 지난 경우
        //Given
        byte[] decodedBytes = Base64.getDecoder().decode(secretKey);
        Key key = Keys.hmacShaKeyFor(decodedBytes);

        String token = "Bearer " +
                Jwts.builder()
                        .setSubject("testUserName")
                        .claim("auth", UserRoleEnum.USER)
                        .setExpiration(new Date(new Date().getTime() - 10000)) // 만료 시간
                        .setIssuedAt(new Date())
                        .signWith(key)
                        .compact();

        token = tokenProvider.substringToken(token);

        //When
        String finalToken = token;
        Exception exception = assertThrows(ExpiredJwtException.class, () -> { // 예상하는 예외 클래스
            // 예외가 발생할 수 있는 코드 블록
            tokenProvider.validateToken(finalToken);
        });

        // Then
        assertEquals("Expired JWT token, 만료된 JWT token 입니다.", exception.getMessage());
    }


    //
    @Test
    public void addJwtToCookie_testAddJwtToCookie() throws UnsupportedEncodingException { // 쿠키에 제대로 추가 되는지 확인
        // Given
        String token = tokenProvider.createToken("testUser", UserRoleEnum.USER);
        log.info(token);
        MockHttpServletResponse response = new MockHttpServletResponse(); // HttpServletResponse 객체를 모킹

        // When
        tokenProvider.addJwtToCookie(token, response);

        // Then
        Cookie cookie = response.getCookie("Authorization");
        // 토큰 값과 쿠키안의 토큰 값이 일치하는지 확인
        assertEquals(URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"), cookie.getValue());
        assertEquals("/", cookie.getPath());

        // 추가적으로 URL 인코딩된 토큰과 쿠키 값을 출력하여 확인
        log.info("Encoded token: {}", URLEncoder.encode(token, "utf-8"));
        log.info("Cookie value: {}", cookie.getValue());
    }

//    public String substringToken(String token) { // 토큰 값 가져와서
//        //공백, null 확인                  // "Bearer "로 시작 확인
//        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
//            return token.substring(7); // "Bearer " 총 7자 자르기
//        }
//        logger.error("Not Found Token");
//        throw new NullPointerException("Not Found Token");
//    }

}
