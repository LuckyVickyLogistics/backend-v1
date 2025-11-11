package com.luckylogistics.user.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luckylogistics.user.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByUsername(String username);
	Optional<User> findByUsername(String username);
	Optional<User> findByIdentifier(UUID identifier);
}
