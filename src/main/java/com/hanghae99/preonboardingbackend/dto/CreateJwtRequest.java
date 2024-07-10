package com.hanghae99.preonboardingbackend.dto;

import com.hanghae99.preonboardingbackend.model.entity.UserRoleEnum;
import lombok.Data;

@Data
public class CreateJwtRequest {
    private String username;
    private UserRoleEnum role;

    public CreateJwtRequest() { // 기본생성자. 생성 후 set 사용하기 위해
    }

    public CreateJwtRequest(String username, UserRoleEnum role) {
        this.username = username;
        this.role = role;
    }
}