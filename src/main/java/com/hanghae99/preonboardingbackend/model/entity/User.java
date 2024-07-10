package com.hanghae99.preonboardingbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    // primary key
    // 자동 증가 되는
    @Id
    @Column(name = "user_id")
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "password", length = 100)
    @JsonIgnore
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "activated")
    @JsonIgnore
    private boolean activated;

    @Column(name = "role")
    @JsonIgnore
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @ManyToMany
    @JoinTable(     // user 가 여러 권한을 가질 수 있도록 함. JoinTable 로 새 테이블 만들어 유저와 권한 관계 만듦.
        name = "user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
