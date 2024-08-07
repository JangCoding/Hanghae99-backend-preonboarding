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

###### 동작과정
1. 톰캣에 요청 도착 - HTTP요청이 톰캣 서버에 도착하면 처리할 쓰레드를 할당하고, DispatcherServlet 으로 요청 전달
2. DispatcherServlet - 어떤 컨트롤러에 전달할 지 결정. HandlerMapping 을 통해 요청을 처리할 컨트롤러와 HandlerInterceptor를 찾는다.
3. HandlerInterceptor - 'preHandle' 메서드가 순차적으로 호출됨.
   -요청을 가로채어 특정 조건을 검사하거나 필요한 전처리 작업을 수행
   -인터셉터 체인을 통과하며 모든 'preHandle' 메서드가 'true'를 반환하면 요청 처리가 계속됨.
   -하나라도 'false'가 반환되면 남은 'preHandle' 메서드와 컨트롤러 메서드 실행을 건너뜀
4. 컨트롤러 실행
5. HandelrInterceptor 후처리 - 컨트롤러가 실행을 완료하면 DispatcherServlet 은 다시 HandlerInterceptor 체인의 'postHandle' 메서드를 호출
   -'postHandle'은 컨트롤러 실행 후 뷰를 렌더링하기 전에 추가적인 작업을 수행함
6. 뷰 렌더링 및 완료 처리 - 'postHandle' 메서드 실행 후 DispatcherServlet은 뷰를 렌더링하고, 클라이언트에 응답을 전송함.
   -마지막으로 HandlerInterceptor체인의 'afterCompletion' 메서드를 호출. 리소스 정리 및 예외처리 관련 작업 수행.
   

##### AOP

1. AOP는 스프링의 핵심 기능 중 하나로, 핵심 비즈니스 로직과 부가적인 관심사를 분리하여 코드의 모듈화를 향상시킨다.
2. Aspect는 특정 관심사(부가기능) 을 모듈화한 것으로, 선택한 메서드(Pointcut)의 어떤 시점(JoinPoint)에서 어떤 작업(Advice)을 수행할 것인지 정의한다.
3. 코드의 재사용성을 높이고, 중복 코드르 줄일 수 있다.
4. 로깅, 트랜잭션 관리, 보안 검사, 성능 모니터링 시 사용

- [ ]  Spring Security란?

스프링 애플리케이션의 각 계층에 대한 보안 기능을 통합하고 통제할 수 있도록 도와주는 보안 프레임워크.

##### 개념

- 인증 : 사용자가 누구인지 확인하는 과정
- 인가 : 인증된 사용자가 특정 자원에 접근할 수 있는지 확인하는 과정. 역할 기반 접근 제어(RBAC)를 주로 사용.
- CSRF 보호 : Cross-Site Request Forgery. 공격자가 피해자의 브라우저를 통해 피해자가 인증된 상태에서 악의적인 요청을 수행하는 것.
- 세션 관리 : 세션 고정 공격 등을 방지.
- OAuth2/OpenID Connect : 외부 인증 서버와 통합할 수 있는 기능.

##### 구조
- SecurityContext : 현재 인증된 사용자의 정보를 담는다.
- Authentication : 사용자의 인증 정보를 나타내는 인터페이스.
- GrantedAuthority : 사용자가 가지고 있는 권한.
- UserDetailsService : 사용자 정보를 로드하는 서비스.
- SecurityFilterChain : Http 요청을 가로채고, 필터링하여 보안을 적용하는 필터 체인

높은 보안성과 유연한 설정, 확장 가능한 아키텍쳐로 쉽게 보안 수준을 향상시키고 관련 처리를 통합할 수 있다.

하지만 다양한 기능과 설정 방법이 있어 모든 기능을 숙지하는 데 시간이 오래 걸리고, 모든 요청을 필터링하여 처리하므로 성능에 영향을 미칠 수 있다.

**JWT 기본 이해**

- [ ]  JWT란 무엇인가요?
Jason Web Token 은 정보를 안전하게 전송하기 위해 디자인된 표준으로, JSON 객체로 표현되며 claim 이라 불리는 속성과 값을 가지고 있으며, subject 와 유효기간, 서명을 포함하여 정보 위조를 확인할 수 있다.

##### 구조
- Header : 토큰의 유형과 사용하는 암호화 알고리즘 등의 메타데이터를 포함
- Payload : 클레임(사용자 , 권한 등) 이 담긴 부분
- Signature : Header와 Payload를 인코딩한 후, 서버에서 생성한 비밀키로 서명한 부분. 토큰 유효성 검증 가능.

##### 장단점
###### 장점
- 스케일링이 용이 : 서버에서 상태를 저장할 필요가 없어 확장성이 좋음
- 암호화 : 데이터를 안전하게 암호화 가능
- 표준화 : 널리 사용되는 표준으로 다양한 플랫폼에서 지원됨.
- 성능 : 토큰 기반 인증으로 인해 서버 부하가 줄어듦.
- 확장성 : 분산 환경에서 서버를 확장하기 쉬움
- 유연성 : 클라이언트-서버 간의 통신을 간단하게 구현 가능.
###### 단점
- 크기 : 정보를 포함하는 토큰이기 때문에 크기가 상대적으로 큼.
- 보안 : 토큰이 탈취되면 정보에 대한 접근 권한이 전체적으로 노출될 수 있음. 안전한 저장/전송 대책 필요.
  

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
