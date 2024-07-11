package com.hanghae99.preonboardingbackend.model.entity;

import jakarta.persistence.*;

@Entity
public class Authority {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "authority_name", length = 50)
    private UserRoleEnum authorityName;

    // Getter와 Setter 추가
    public UserRoleEnum getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(UserRoleEnum authorityName) {
        this.authorityName = authorityName;
    }
}
