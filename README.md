![spartacodingclub](https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1719643111/noticon/yeqwdeuiybor5m4hh7zj.png)
# Hanghae99 Preonboarding Backend Course

**취업시장에 침투하기 전에, 실전과 같은 훈련으로 코딩의 감(떫음)을 찾아서 세상에 스파르타st를 보여주자.<br />
어렵다고 느끼는 제군들도 있겠지만, 힌트를 보면서 잘 따라 와주기를 바란다.**



### 🎖️ 훈련 메뉴

---
- [ ]  Junit를 이용한 테스트 코드 작성법 이해
- [ ]  Spring Security를 이용한 Filter에 대한 이해
- [ ]  JWT와 구체적인 알고리즘의 이해
- [ ]  PR 날려보기
- [ ]  리뷰 바탕으로 개선하기
- [ ]  EC2에 배포해보기

### Day 1 - 시나리오 설계 및 코딩 시작!

---
**Spring Security 기본 이해**

- [ ]  Filter란 무엇인가?(with Interceptor, AOP)

##### Filter

1. Filter 는 서블릿 스펙에 정의된 기능으로, 클라이언트의 요청이 서블릿이나 JSP 등에 도달하기 전에 요청을 가로채서 처리할 수 있다. 주로 인증, 인가, 로깅 등의 기능을 구현하는 데 사용된다.
 필터 체인은 여러 필터로 구성되며 필터는 순차적으로 실행된다.
2. 각 필터는 'dofilter()' 메서드를 통해 요청과 응답을 처리한다.
3. 서블릿 필터는 서블릿 컨테이너 레벨에서 동작하므로 특정 프레임워크(Spring, Struts 등) 에 종속되지 않고 모든 서블릿 컨테이너( Tomcat, Jetty, WildFly 등) 에서 동일하게 작동한다.
4. 모든 요청을 중앙에서 처리할 수 있어 일관성 있는 처리 로직을 적용할 수 있다.
5. 인증 및 인가, 로깅 및 모니터링, CORS 처리, 데이터 압축 & 변환에 사용됨.
   ###### 서블릿 : Java 클래스로, 요청/응답 처리하는 서버측 컴포넌트. HTML페이지를 동적으로 생성, 다른 페이지로 리다이렉트 하는데 사용됨. 

##### Interceptor

1. Interceptor 는 스프링 프레임워크에서 제공하는 기능으로, 스프링 MVC에서 컨트롤러의 요청 처리 과정 전후에 특정 로직을 실행할 수 있다.
2. HandlerInterceptor 인터페이스를 구현해야 하고 preHandle(), postHandle(), afterCompletion() 메서드를 통해 각각 특정 로직을 실행.
3. 인터셉터는 스프링의 설정 파일이나 자바 설정을 통해 등록.
4. 요청 전/후 데이터 가공, 사용자 권한 체크, 특정 컨트롤러에 대한 공통 로직 처리 시 사용

##### AOP

1. AOP는 스프링의 핵심 기능 중 하나로, 핵심 비즈니스 로직과 부가적인 관심사를 분리하여 코드의 모듈화를 향상시킨다.
2. Aspect는 특정 관심사(부가기능) 을 모듈화한 것으로, 선택한 메서드(Pointcut)의 어떤 시점(JoinPoint)에서 어떤 작업(Advice)을 수행할 것인지 정의한다.
3. 코드의 재사용성을 높이고, 중복 코드르 줄일 수 있다.
4. 로깅, 트랜잭션 관리, 보안 검사, 성능 모니터링 시 사용

- [ ]  Spring Security란?

스프링 애플리케이션의 각 계층에 대한 보안 기능을 통합하고 통제할 수 있도록 도와주는 보안 프레임워크.

- 인증 : 사용자가 누구인지 확인하는 과정
- 인가 : 인증된 사용자가 특정 자원에 접근할 수 있는지 확인하는 과정. 역할 기반 접근 제어(RBAC)를 주로 사용.
- CSRF 보호 : Cross-Site Request Forgery. 공격자가 피해자의 브라우저를 통해 피해자가 인증된 상태에서 악의적인 요청을 수행하는 것.
- 세션 관리 : 세션 고정 공격 등을 방지.
- OAuth2/OpenID Connect : 외부 인증 서버와 통합할 수 있는 기능.

**JWT 기본 이해**

- [ ]  JWT란 무엇인가요?

**토큰 발행과 유효성 확인**

- [ ]  Access / Refresh Token 발행과 검증에 관한 **테스트 시나리오** 작성하기

**유닛 테스트 작성**

- [ ]  JUnit를 이용한 JWT Unit 테스트 코드 작성해보기

  - https://preasim.github.io/39

  - [https://velog.io/@da_na/Spring-Security-JWT-Spring-Security-Controller-Unit-Test하기](https://velog.io/@da_na/Spring-Security-JWT-Spring-Security-Controller-Unit-Test%ED%95%98%EA%B8%B0)


### Day 2 - 백엔드 배포하기

---
**테스트 완성**

- [ ]  백엔드 유닛 테스트 완성하기

**로직 작성**

- [ ]  백엔드 로직을 Spring Boot로
    - [ ]  회원가입 - /signup
        - [ ]  Request Message

           ```json
           {
               "username": "JIN HO",
               "password": "12341234",
               "nickname": "Mentos"
           }
           ```

        - [ ]  Response Message

           ```json
           {
               "username": "JIN HO",
               "nickname": "Mentos",
               "authorities": [
                       {
                               "authorityName": "ROLE_USER"
                       }
               ]		
           }
           ```

    - [ ]  로그인 - /sign
        - [ ]  Request Message

           ```json
           {
               "username": "JIN HO",
               "password": "12341234"
           }
           ```

        - [ ]  Response Message

           ```json
           {
               "token": "eKDIkdfjoakIdkfjpekdkcjdkoIOdjOKJDFOlLDKFJKL",
           }
           ```


**배포해보기**

- [ ]  AWS EC2 에 배포하기

**API 접근과 검증**

- [ ]  Swagger UI 로 접속 가능하게 하기

### Day 3 - 백엔드 개선하기

---
[Git 커밋 메시지 잘 쓰는 법 | GeekNews](https://news.hada.io/topic?id=9178&utm_source=slack&utm_medium=bot&utm_campaign=TQ595477U)

**AI-assisted programming**

- [ ]  AI 에게 코드리뷰 받아보기

**Refactoring**

- [ ]  피드백 받아서 코드 개선하기

**마무리**

- [ ]  AWS EC2 재배포하기
