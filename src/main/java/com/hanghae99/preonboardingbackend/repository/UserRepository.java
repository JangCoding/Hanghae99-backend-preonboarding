package com.hanghae99.preonboardingbackend.repository;

import com.hanghae99.preonboardingbackend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
