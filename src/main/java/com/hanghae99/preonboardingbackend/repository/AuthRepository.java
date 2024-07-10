package com.hanghae99.preonboardingbackend.repository;

import com.hanghae99.preonboardingbackend.model.entity.Authority;
import com.hanghae99.preonboardingbackend.model.entity.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Authority, UserRoleEnum> {
}
