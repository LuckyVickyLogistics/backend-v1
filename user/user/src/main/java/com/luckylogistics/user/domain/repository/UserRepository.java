package com.luckylogistics.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luckylogistics.user.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByUsername(String username);
}
