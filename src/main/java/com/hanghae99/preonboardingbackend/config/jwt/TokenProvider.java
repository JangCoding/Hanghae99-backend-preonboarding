package com.hanghae99.preonboardingbackend.config.jwt;

import com.hanghae99.preonboardingbackend.model.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Date;
import java.util.Base64;

@Component // 필요한 곳에서 사용할 수있도록 Bean 으로 등록
@Configuration
@Slf4j
public class TokenProvider implements InitializingBean {

    // JWT 데이터
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";       //    private static final String AUTHORITIES_KEY = "authorities";

    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer "; // 토큰 앞에 붙일 용어. 토큰임을 알려주는 규칙 같은 것.

    // 토큰 만료시간
    @Value("${jwt.token-validity-in-milliseconds}")
    private long TOKEN_TIME; // tokenValidityInMilliseconds;

    @Value("${jwt.secret}") // Base64 Encode 한 SecretKey . application.yml 에서 가져옴.
    private String secretKey;
    private Key key; // SecretKey 를 담을 객체
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 암호화 알고리즘 HS256

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    @PostConstruct // JwtUtil 이 생성된 후 key 에 SecretKey를 Base64 로 디코드 해서 담을 것.
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey); // 바이트 배열 타입
        key = Keys.hmacShaKeyFor(bytes);
    }


    // JWT 생성
    public String createToken(String username, UserRoleEnum role) {
        if (username == null || role == null) {
            throw new IllegalArgumentException("Username and role must not be null");
        }

        Date date = new Date();

                // "Bearer " 값을 앞에 붙임
        return BEARER_PREFIX +
                // Jwts Class의 builder() 메서드를 통해 jwt 생성
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 시크릿키, 암호화 알고리즘을 넣어 암호화.
                        .compact();
    }

    // JWT Cookie 에 저장
    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
            cookie.setPath("/");

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }

    // Cookie에 들어있던 JWT 을 Substring
    public String substringToken(String tokenValue) { // 토큰 값 가져와서
                        //공백, null 확인                  // "Bearer "로 시작 확인
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7); // "Bearer " 총 7자 자르기
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
                                //암호화에 사용한 키           // 토큰의 위,변조 검증
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // JWT에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
                                                         // 검증단계 + .getBody() 로 본문 가져오기
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
