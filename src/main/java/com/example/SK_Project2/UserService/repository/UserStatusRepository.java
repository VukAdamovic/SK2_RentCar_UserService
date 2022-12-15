package com.example.SK_Project2.UserService.repository;

import com.example.SK_Project2.UserService.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
}
