package com.sparta.springthreeproject.user.repository;

import com.sparta.springthreeproject.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserName(String name);
}
