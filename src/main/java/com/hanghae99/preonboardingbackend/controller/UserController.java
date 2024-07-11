package com.hanghae99.preonboardingbackend.controller;


import com.hanghae99.preonboardingbackend.config.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final TokenProvider tokenProvider;

    @Autowired
    public UserController(TokenProvider tokenProvider)
    {
        this.tokenProvider = tokenProvider;
    }
//    @GetMapping("/login")
//    public String logIn(HttpServletResponse res) {
//
//    }
//
//    @PostMapping("/signIn")
//    public String signIn(HttpServletResponse res) {
//
//    }

}
//
//@RequestMapping("/api")
//public class UserController {
//
//    @GetMapping("/user/login-page")
//    public String loginPage() {
//        return "login";
//    }
//
//    @GetMapping("/user/signup")
//    public String signupPage() {
//        return "signup";
//    }
//}